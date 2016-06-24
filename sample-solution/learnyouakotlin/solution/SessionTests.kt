package learnyouakotlin.solution

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.util.Arrays.asList

class SessionTests {
    val original = Session("The Title", null, 2..3, Presenter("Alice"))
    
    @Test
    fun can_change_presenters() {
        assertThat(original.withPresenters(asList(Presenter("Bob"), Presenter("Carol"))), equalTo(
            Session("The Title", null, 2..3, Presenter("Bob"), Presenter("Carol"))))
    }
    
    @Test
    fun can_change_title() {
        assertThat(original.withTitle("Another Title"), equalTo(
            Session("Another Title", null, 2..3, Presenter("Alice"))))
    }
    
    @Test
    fun can_change_subtitle() {
        assertThat(original.withSubtitle("The Subtitle"), equalTo(
            Session("The Title", "The Subtitle", 2..3, Presenter("Alice"))))
    }
    
    @Test
    fun can_remove_subtitle() {
        assertThat(original.withSubtitle("The Subtitle").withSubtitle(null), equalTo(
            Session("The Title", null, 2..3, Presenter("Alice"))))
    }
}

