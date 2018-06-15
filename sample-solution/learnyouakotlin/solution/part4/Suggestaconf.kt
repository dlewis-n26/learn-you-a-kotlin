package learnyouakotlin.solution.part4

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import learnyouakotlin.solution.part1.Presenter
import learnyouakotlin.solution.part1.Session
import learnyouakotlin.solution.part3.array
import learnyouakotlin.solution.part3.asJson
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.server.SunHttp
import java.security.SecureRandom
import java.util.Random

private val no_subtitle = null

val sessions = setOf(
    Session("Opening Plenary", no_subtitle, 1..1),

    Session("Play the GetKanban Game", no_subtitle, 2..6,
        Presenter("Mattia Battiston")),

    Session("Learn You A Kotlin", "For all the good it will do you", 2..3,
        Presenter("Nat Pryce"),
        Presenter("Duncan McGregor")),

    Session("Empathy Dojo", no_subtitle, 2..2,
        Presenter("Marina Haase")),

    Session("Agile: it's not just about the development team", no_subtitle, 2..2,
        Presenter("Andy Longshaw")),

    Session("Working Fearlessly with your Data", no_subtitle, 3..3,
        Presenter("Stephen Lewis"),
        Presenter("Ian Bell")),

    Session("10 Releases a Day and No Anguish", "Continuous Delivery for Humans", 3..3,
        Presenter("Douglas Squirrel")),

    Session("Neurodiversity", no_subtitle, 4..4,
        Presenter("Sallyann Freudenberg")),

    Session("Real World Big Data in Action", no_subtitle, 5..6,
        Presenter("Nick Rozanski"),
        Presenter("Eoin Woods"),
        Presenter("Chris Cooper-Bland")),

    Session("Education for Engineers", no_subtitle, 5..5,
        Presenter("Roberty Chatley")),

    Session("Agile in a Business-to-Business Relationship", no_subtitle, 6..6,
        Presenter("Camilla Brown")),

    Session("Pathfinding Peril", no_subtitle, 5..6,
        Presenter("Chris Parsons")),

    Session("Birds of a Feather Sessions", no_subtitle, 7..7)
)

fun Suggestaconf(rng: Random): (Request) -> Response {
    val objectMapper = ObjectMapper().enable(INDENT_OUTPUT)
    return { _ ->
        val schedule = rng.sample(allSchedules(sessions))
        
        if (schedule != null) {
            Response(OK)
                .header("Content-Type", "application/json")
                .header("Access-Control-Allow-Origin", "*")
                .body(objectMapper.writeValueAsString(array(schedule, Session::asJson)))
        }
        else {
            Response(NOT_FOUND)
        }
    }
}

fun main(args: Array<String>) {
    val serverConfig = SunHttp(8910)
    serverConfig.toServer(Suggestaconf(SecureRandom()))
        .start()
    println("http://localhost:${serverConfig.port}/")
}
