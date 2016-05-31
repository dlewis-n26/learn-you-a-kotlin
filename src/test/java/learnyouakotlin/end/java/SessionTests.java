package learnyouakotlin.end.java;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SessionTests {
    SessionCode code = SessionCode.of(1);

    Session original = new Session(code, "The Title", null, new Presenter("Alice"));

    @Test
    public void can_change_presenters() {
        assertThat(original.withPresenters(asList(new Presenter("Bob"), new Presenter("Carol"))), equalTo(
                new Session(code, "The Title", null, new Presenter("Bob"), new Presenter("Carol"))));
    }

    @Test
    public void can_change_title() {
        assertThat(original.withTitle("Another Title"), equalTo(
                new Session(code, "Another Title", null, new Presenter("Alice"))));
    }

    @Test
    public void can_change_subtitle() {
        assertThat(original.withSubitle("The Subtitle"), equalTo(
                new Session(code, "The Title", "The Subtitle", new Presenter("Alice"))));
    }

    @Test
    public void can_remove_subtitle() {
        assertThat(original.withSubitle("The Subtitle").withSubitle(null), equalTo(
                new Session(code, "The Title", null, new Presenter("Alice"))));
    }
}
