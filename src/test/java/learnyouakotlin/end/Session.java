package learnyouakotlin.end;

import java.util.List;
import java.util.Objects;

import static com.oneeyedmen.okeydoke.util.Tabulator.asList;

public class Session {
    public final SessionCode code;
    public final String title;
    public final List<Presenter> presenters;

    public Session(SessionCode code, String title, List<Presenter> presenters) {
        this.code = code;
        this.title = title;
        this.presenters = presenters;
    }

    public Session(SessionCode code, String title, Presenter... presenters) {
        this(code, title, asList(presenters));
    }

    @Override
    public String toString() {
        return "Session{" +
                "code=" + code +
                ", title='" + title + '\'' +
                ", authors=" + presenters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(code, session.code) &&
                Objects.equals(title, session.title) &&
                Objects.equals(presenters, session.presenters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, title, presenters);
    }
}