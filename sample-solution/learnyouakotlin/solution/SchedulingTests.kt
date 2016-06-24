package learnyouakotlin.solution

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

private val openingPlenary = Session("Opening Plenary", null, 1..1)
private val learnYouAKotlin = Session("Learn You A Kotlin", "For all the good it will do you", 2..3,
    Presenter("Nat Pryce"),
    Presenter("Duncan McGregor"))
private val keynote = Session("Neurodiversity", null, 4..4,
    Presenter("Sallyann Freudenberg"))
private val realWorldBigData = Session("Real World Big Data in Action", null, 5..5,
    Presenter("Nick Rozanski"),
    Presenter("Eoin Woods"),
    Presenter("Chris Cooper-Bland"))
private val bofs = Session("Birds of a Feather Sessions", null, 6..6)

class ScheduleTests {
    @Test
    fun single_track() {
        val sessions = listOf(
            openingPlenary,
            learnYouAKotlin,
            keynote,
            realWorldBigData,
            bofs
        )

        assertThat(sessions.allSchedules().toList(), equalTo(listOf(sessions)))
    }
}