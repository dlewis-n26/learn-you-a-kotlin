package learnyouakotlin.end.kotlin

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import learnyouakotlin.end.kotlin.Result.Failure
import learnyouakotlin.end.kotlin.Result.Success

fun Session.asJson() = obj(
    "code" of code.toString(),
    "title" of title,
    "presenters" of array(presenters, Presenter::asJson))

fun JsonNode.toSession() = apply(::Session,
    path("code").toSessionCode(),
    path("title").asNonblankText(),
    path("presenters").flatMap(JsonNode::toPresenter))


fun Presenter.asJson() = obj("name" of name)
fun JsonNode.toPresenter() = apply(::Presenter, path("name").asNonblankText())

fun JsonNode.toSessionCode() = this.asText()
    .let { SessionCode.parse(it) }
    ?.let { Success(it) }
    ?: jsonFailure("could not parse ${asText()} as SessionCode")


fun JsonNode.asNonblankText() = asText().let {
    when {
        it.isBlank() -> jsonFailure("blank text is invalid")
        else -> Result.Success(it)
    }
}

private fun <T> JsonNode.map(transform: (JsonNode) -> T) = elements().asSequence().map(transform).toList()
private fun <T> JsonNode.flatMap(transform: (JsonNode) -> Result<T>) = map(transform).flatten()

private fun jsonFailure(message: String) = Failure(JsonMappingException(null, message))

