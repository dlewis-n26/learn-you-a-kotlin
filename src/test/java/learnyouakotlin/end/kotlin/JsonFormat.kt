package learnyouakotlin.end.kotlin

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import learnyouakotlin.end.kotlin.Result.Failure
import learnyouakotlin.end.kotlin.Result.Success

fun Session.asJson() = obj(
    "code" of code.toString(),
    "title" of title,
    "presenters" of array(presenters, Presenter::asJson))

fun JsonNode.toSession() =
    path("code").toSessionCode().flatMap { sessionCode ->
        path("title").asText().let { title ->
            path("presenters").flatMap(JsonNode::toPresenter).flatMap { presenters ->
                Success(Session(sessionCode, title, presenters))
            }
        }
    }

fun Presenter.asJson() = obj("name" of name)
fun JsonNode.toPresenter() = Success(Presenter(path("name").asText()))

fun JsonNode.toSessionCode() = this.asText()
    .let { SessionCode.parse(it) }
    ?.let { Success(it) }
    ?: Failure(JsonMappingException(null, "could not parse ${this.asText()} as SessionCode"))


private fun <T> JsonNode.map(transform: (JsonNode) -> T) = elements().asSequence().map(transform).toList()
private fun <T> JsonNode.flatMap(transform: (JsonNode) -> Result<T>) = map(transform).flatten()
