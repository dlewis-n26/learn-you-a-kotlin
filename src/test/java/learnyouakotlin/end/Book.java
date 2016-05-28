package learnyouakotlin.end;

import java.util.List;

public class Book {
    public final CatalogNumber catalogNumber;
    public final String title;
    public final List<Person> authors;

    public Book(CatalogNumber catalogNumber, String title, List<Person> authors) {
        this.catalogNumber = catalogNumber;
        this.title = title;
        this.authors = authors;
    }
}
