package learnyouakotlin.end.java;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;


public class Session {
    public final SessionCode code;
    public final String title;
    @Nullable public final String subtitle;
    public final List<Presenter> presenters;

    public Session(SessionCode code, String title, @Nullable String subtitle, List<Presenter> presenters) {
        this.code = code;
        this.title = title;
        this.subtitle = subtitle;
        this.presenters = Collections.unmodifiableList(presenters);
    }

    public Session(SessionCode code, String title, @Nullable String subtitle, Presenter... presenters) {
        this(code, title, subtitle, asList(presenters));
    }

    @Override
    public String toString() {
        return "Session{" +
                "code=" + code +
                ", title='" + title + '\'' +
                (subtitle == null ? "" : ", subtitle='" + subtitle + '\'') +
                ", presenters=" + presenters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(code, session.code) &&
                Objects.equals(title, session.title) &&
                Objects.equals(subtitle, session.subtitle) &&
                Objects.equals(presenters, session.presenters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, title, subtitle, presenters);
    }

    public Session withPresenters(List<Presenter> newLineUp) {
        return new Session(code, title, subtitle, newLineUp);
    }

    public Session withTitle(String newTitle) {
        return new Session(code, newTitle, subtitle, presenters);
    }

    public Session withSubitle(String newSubtitle) {
        return new Session(code, newSubtitle, subtitle, presenters);
    }
}
