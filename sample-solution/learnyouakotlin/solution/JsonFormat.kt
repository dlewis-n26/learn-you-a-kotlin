package learnyouakotlin.solution

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import learnyouakotlin.solution.Result.Failure

fun Session.asJson() = obj(
    "title" of title,
    subtitle?.let { "subtitle" of it },
    "slots" of obj("first" of slots.start, "last" of slots.endInclusive),
    "presenters" of array(presenters, learnyouakotlin.solution.Presenter::asJson))

fun JsonNode.toSession() = apply(::Session,
    path("title").asNonblankText(),
    path("subtitle").asOptionalNonblankText(),
    path("slots").toIntRange(),
    path("presenters").all(JsonNode::toPresenter))


fun Presenter.asJson() = obj("name" of name)
fun JsonNode.toPresenter() = apply(::Presenter, path("name").asNonblankText())

fun JsonNode.asOptionalNonblankText() = if (isNull || isMissingNode) Result.Success(null) else asNonblankText()

fun JsonNode.asNonblankText() = asText().let {
    when {
        it.isBlank() -> jsonFailure("blank text is invalid")
        else -> Result.Success(it)
    }
}

fun JsonNode.toIntRange(): Result<IntRange> {
    return apply(::IntRange,
        path("first").toInt(),
        path("last").toInt())
}

fun JsonNode.toInt() : Result<Int> {
    return if (isInt) Result.Success(asInt()) else Result.Failure(NumberFormatException("not an int"))
}

private fun <T> JsonNode.map(transform: (JsonNode) -> T) = elements().asSequence().map(transform).toList()
private fun <T> JsonNode.all(transform: (JsonNode) -> Result<T>) = map(transform).flatten()

private fun jsonFailure(message: String) = Failure(JsonMappingException(null, message))

