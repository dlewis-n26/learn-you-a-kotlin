package learnyouakotlin.part2

import learnyouakotlin.part1.Session
import learnyouakotlin.part1.Slots
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

private val learnYouAKotlin = Session("Learn you a kotlin", "for all the good it will do you", Slots(1, 1))
private val refactoringToStreams = Session("Refactoring to Streams", null, Slots(2, 2))

fun List<Session>.sessionWithTitle(s: String): Session? =
    find { it.title.equals(s, ignoreCase = true) }

fun subtitleOf(session: Session?): String? {
    if (session == null) return null
    return session.subtitle
}

fun subtitleOrPrompt(session: Session?): String {
    if (session == null || session.subtitle == null) return "click to enter subtitle"
    return session.subtitle
}

class NullsTests {
    val sessions = listOf(learnYouAKotlin, refactoringToStreams)
    
    @Test
    fun nulls() {
        val session: Session? = sessions.sessionWithTitle("learn you a kotlin")
        
        val notNullSession = session as Session
        assertEquals("for all the good it will do you", notNullSession.subtitle)
        assertEquals("for all the good it will do you", session.subtitle)
    }
    
    
    @Test
    fun null_safe_access() {
        assertEquals("for all the good it will do you", subtitleOf(learnYouAKotlin))
        assertNull(subtitleOf(null))
    }
    
    @Test
    fun elvis() {
        assertEquals("for all the good it will do you", subtitleOrPrompt(learnYouAKotlin))
        assertEquals("click to enter subtitle", subtitleOrPrompt(refactoringToStreams))
        assertEquals("click to enter subtitle", subtitleOrPrompt(null))
    }
    
}

