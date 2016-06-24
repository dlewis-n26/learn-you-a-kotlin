package learnyouakotlin.solution

import java.security.SecureRandom

private val no_subtitle = null

val mondaySessions = setOf(
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

fun main(args: Array<String>) {
    val rng = SecureRandom()
    val schedule = rng.sample(allSchedules(mondaySessions))

    if (schedule == null) {
        println("could not choose a schedule")
    }
    else {
        schedule.forEach { session ->
            println("Slot ${session.slots.start}: ${session.title} ${if (session.presenters.isEmpty()) "" else "by ${session.presenters.joinToString(", ")}"}")
        }
    }
}

