package learnyouakotlin.end.java;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.oneeyedmen.okeydoke.junit.ApprovalsRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static com.oneeyedmen.okeydoke.ApproverFactories.fileSystemApproverFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class JsonFormatTests {
    @Rule
    public final ApprovalsRule approval = new ApprovalsRule(fileSystemApproverFactory(new File("src/test/java")));

    @Test
    public void session_to_json() {
        Session session = new Session(SessionCode.of(1), "Learn You a Kotlin For All The Good It Will Do You",
                new Presenter("Duncan McGregor"),
                new Presenter("Nat Pryce"));

        JsonNode json = JsonFormat.sessionAsJson(session);

        approval.assertApproved(json, Json::asStableJsonString);
    }

    @Test
    public void session_from_json() throws JsonMappingException {
        Session original = new Session(SessionCode.of(2), "Working Effectively with Legacy Tests",
                new Presenter("Nat Pryce"),
                new Presenter("Duncan McGregor"));

        Session parsed = JsonFormat.sessionFromJson(JsonFormat.sessionAsJson(original));

        assertThat(parsed, equalTo(original));
    }
}
