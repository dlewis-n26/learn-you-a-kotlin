package learnyouakotlin.solution

import com.fasterxml.jackson.databind.JsonNode
import com.oneeyedmen.okeydoke.ApproverFactories.fileSystemApproverFactory
import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import learnyouakotlin.Json
import learnyouakotlin.solution.Result.Success
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Rule
import org.junit.Test
import java.io.File

class JsonFormatTests {
    @Rule
    @JvmField
    val approval = ApprovalsRule(fileSystemApproverFactory(File("sample-solution")))
    
    @Test
    fun session_to_json() {
        val session = Session(SessionCode(1),
            "Learn You a Kotlin For All The Good It Will Do You",
            null,
            Presenter("Duncan McGregor"),
            Presenter("Nat Pryce"))

        approval.assertApproved(session.asJson(), JsonNode::asStableJsonString)
    }

    @Test
    fun session_with_subtitle_to_json() {
        val session = Session(SessionCode(2),
            "Scrapheap Challenge",
            "A Workshop in Postmodern Programming",
            Presenter("Ivan Moore"))

        approval.assertApproved<JsonNode>(session.asJson(), JsonNode::asStableJsonString)
    }

    @Test
    fun session_to_and_from_json() {
        val original = Session(SessionCode(2), "Working Effectively with Legacy Tests", null,
            Presenter("Nat Pryce"),
            Presenter("Duncan McGregor"))

        assertThat(original.asJson().toSession().value, equalTo(original))
    }
}
