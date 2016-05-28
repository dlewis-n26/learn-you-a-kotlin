package learnyouakotlin.end;

import java.util.Objects;
import java.util.Optional;

public class CatalogNumber {
    private final int repr;

    private CatalogNumber(int repr) {
        this.repr = repr;
    }

    public static CatalogNumber of(int repr) {
        return new CatalogNumber(repr);
    }

    public static Optional<CatalogNumber> parse(String s) {
        try {
            int repr = Integer.valueOf(s);
            return repr > 0 ? Optional.of(CatalogNumber.of(repr)) : Optional.empty();
        } catch (NumberFormatException e) {
            return Optional.empty();
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
        CatalogNumber catalogNumber = (CatalogNumber) o;
        return Objects.equals(repr, catalogNumber.repr);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(repr);
    }
}
