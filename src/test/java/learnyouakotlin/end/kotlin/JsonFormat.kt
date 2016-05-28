package learnyouakotlin.end.kotlin

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import java.text.ParseException

fun Session.asJson() = obj(
    "code" of code.toString(),
    "title" of title,
    "presenters" of array(presenters, Presenter::asJson))

@Throws(JsonMappingException::class)
fun JsonNode.toSession(): Session {
    try {
        val code = SessionCode.parse(path("code").asText())
        val title = path("title").asText()
        val authors = path("presenters").elements().asSequence().map { it.toPresenter() }.toList()

        return Session(code, title, authors)

    }
    catch (e: ParseException) {
        throw JsonMappingException(null, "failed to parse Book from JSON", e)
    }
}

private fun Presenter.asJson() = obj("name" of name)

fun JsonNode.toPresenter() = Presenter(path("name").asText())
