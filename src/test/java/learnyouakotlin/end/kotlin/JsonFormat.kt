package learnyouakotlin.end.kotlin

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import java.text.ParseException

fun Session.asJson() = obj(
    "code" of code.toString(),
    "title" of title,
    "presenters" of array(presenters, Presenter::asJson))

fun JsonNode.toSession() = Session(
    SessionCode.parse(path("code").asText()) ?: throw JsonMappingException(null, "failed to parse SessionCode from JSON"),
    path("title").asText(),
    path("presenters").elements().asSequence().map { it.toPresenter() }.toList())

fun Presenter.asJson() = obj("name" of name)

fun JsonNode.toPresenter() = Presenter(path("name").asText())
