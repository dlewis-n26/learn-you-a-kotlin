package learnyouakotlin.solution

import org.junit.Test

import java.util.Arrays.asList
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat

class SessionTests {
    val code = SessionCode(1)
    
    val original = Session(code, "The Title", null, Presenter("Alice"))
    
    @Test
    fun can_change_presenters() {
        assertThat(original.withPresenters(asList(Presenter("Bob"), Presenter("Carol"))), equalTo(
            Session(code, "The Title", null, Presenter("Bob"), Presenter("Carol"))))
    }
    
    @Test
    fun can_change_title() {
        assertThat(original.withTitle("Another Title"), equalTo(
            Session(code, "Another Title", null, Presenter("Alice"))))
    }
    
    @Test
    fun can_change_subtitle() {
        assertThat(original.withSubtitle("The Subtitle"), equalTo(
            Session(code, "The Title", "The Subtitle", Presenter("Alice"))))
    }
    
    @Test
    fun can_remove_subtitle() {
        assertThat(original.withSubtitle("The Subtitle").withSubtitle(null), equalTo(
            Session(code, "The Title", null, Presenter("Alice"))))
    }
}
