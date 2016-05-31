package learnyouakotlin.end.java;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.oneeyedmen.okeydoke.junit.ApprovalsRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static com.oneeyedmen.okeydoke.ApproverFactories.fileSystemApproverFactory;
import static learnyouakotlin.end.java.JsonFormat.sessionAsJson;
import static learnyouakotlin.end.java.JsonFormat.sessionFromJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class JsonFormatTests {
    @Rule
    public final ApprovalsRule approval = new ApprovalsRule(fileSystemApproverFactory(new File("src/test/java")));

    @Test
    public void session_to_json() {
        Session session = new Session(SessionCode.of(1),
                "Learn You a Kotlin For All The Good It Will Do You",
                null,
                new Presenter("Duncan McGregor"),
                new Presenter("Nat Pryce"));

        JsonNode json = sessionAsJson(session);

        approval.assertApproved(json, Json::asStableJsonString);
    }

    @Test
    public void session_with_subtitle_to_json() {
        Session session = new Session(SessionCode.of(2),
                "Scrapheap Challenge",
                "A Workshop in Postmodern Programming",
                new Presenter("Ivan Moore"));

        JsonNode json = sessionAsJson(session);

        approval.assertApproved(json, Json::asStableJsonString);
    }

    @Test
    public void session_to_and_from_json() throws JsonMappingException {
        Session original = new Session(SessionCode.of(3),
                "Working Effectively with Legacy Tests",
                null,
                new Presenter("Nat Pryce"),
                new Presenter("Duncan McGregor"));

        Session parsed = sessionFromJson(sessionAsJson(original));

        assertThat(parsed, equalTo(original));
    }
}
