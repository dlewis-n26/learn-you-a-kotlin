package learnyouakotlin.end;

import java.text.ParseException;
import java.util.Objects;

public class SessionCode {
    private final int repr;

    private SessionCode(int repr) {
        this.repr = repr;
    }

    public static SessionCode of(int repr) {
        return new SessionCode(repr);
    }

    public static SessionCode parse(String s) throws ParseException {
        try {
            int repr = Integer.valueOf(s);

            if (repr > 0) {
                return SessionCode.of(repr);
            } else {
                throw new ParseException("catalog number must be greater than zero, was: " + s, 0);
            }
        } catch (NumberFormatException e) {
            throw new ParseException("invalid catalog number: " + s, 0);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(repr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionCode code = (SessionCode) o;
        return Objects.equals(repr, code.repr);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(repr);
    }
}
