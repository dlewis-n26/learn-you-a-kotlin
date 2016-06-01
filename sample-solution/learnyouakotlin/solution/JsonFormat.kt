package learnyouakotlin.solution

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import learnyouakotlin.solution.Result.Failure
import learnyouakotlin.solution.Result.Success

fun learnyouakotlin.solution.Session.asJson() = learnyouakotlin.solution.obj(
    "code" of code.toString(),
    "title" of title,
    subtitle?.let { "subtitle" of it },
    "presenters" of learnyouakotlin.solution.array(presenters, learnyouakotlin.solution.Presenter::asJson))

fun JsonNode.toSession() = apply(::Session,
    path("code").toSessionCode(),
    path("title").asNonblankText(),
    path("subtitle").asOptionalNonblankText(),
    path("presenters").all(JsonNode::toPresenter))


fun Presenter.asJson() = obj("name" of name)
fun JsonNode.toPresenter() = apply(::Presenter, path("name").asNonblankText())

fun JsonNode.toSessionCode() = this.asText()
    .let { it.toSessionCode() }
    ?.let { Success(it) }
    ?: jsonFailure("could not parse ${asText()} as SessionCode")

fun JsonNode.asOptionalNonblankText() = if (isNull || isMissingNode) Result.Success(null) else asNonblankText()

fun JsonNode.asNonblankText() = asText().let {
    when {
        it.isBlank() -> jsonFailure("blank text is invalid")
        else -> Result.Success(it)
    }
}

private fun <T> JsonNode.map(transform: (JsonNode) -> T) = elements().asSequence().map(transform).toList()
private fun <T> JsonNode.all(transform: (JsonNode) -> Result<T>) = map(transform).flatten()

private fun jsonFailure(message: String) = Failure(JsonMappingException(null, message))

