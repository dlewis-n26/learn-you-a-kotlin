package learnyouakotlin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;


public class Session {
    public final String title;
    @Nullable
    public final String subtitle;
    public final List<Presenter> presenters;

    public Session(String title, @Nullable String subtitle, List<Presenter> presenters) {
        this.title = title;
        this.subtitle = subtitle;
        this.presenters = Collections.unmodifiableList(new ArrayList<>(presenters));
    }

    public Session(String title, @Nullable String subtitle, Presenter... presenters) {
        this(title, subtitle, asList(presenters));
    }

    @Override
    public String toString() {
        return "Session{" +
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
        return Objects.equals(title, session.title) &&
                Objects.equals(subtitle, session.subtitle) &&
                Objects.equals(presenters, session.presenters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, subtitle, presenters);
    }

    public Session withPresenters(List<Presenter> newLineUp) {
        return new Session(title, subtitle, newLineUp);
    }

    public Session withTitle(String newTitle) {
        return new Session(newTitle, subtitle, presenters);
    }

    public Session withSubtitle(@Nullable String newSubtitle) {
        return new Session(title, newSubtitle, presenters);
    }
}
