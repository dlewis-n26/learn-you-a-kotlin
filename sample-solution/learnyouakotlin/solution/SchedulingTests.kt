package learnyouakotlin.solution

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ScheduleTests {
    @Test
    fun single_track() {
        val s1 = Session(title = "1", slots = 1..1)
        val s2 = Session(title = "2", slots = 2..3)
        val s3 = Session(title = "3", slots = 4..4)

        val sessions = setOf(s1, s2, s3)

        assertThat(allSchedules(sessions).toList(), equalTo(listOf(listOf(s1, s2, s3))))
    }

    @Test
    fun two_tracks_and_plenaries() {
        val p1 = Session(title = "P1", slots = 1..1)
        val a2 = Session(title = "A2", slots = 2..3)
        val b2 = Session(title = "B2", slots = 2..2)
        val b3 = Session(title = "B3", slots = 3..3)
        val p4 = Session(title = "P4", slots = 4..4)

        val sessions = setOf(p1, a2, b2, b3, p4)

        assertThat(allSchedules(sessions).toList(), equalTo(listOf(
            listOf(p1, a2, p4),
            listOf(p1, b2, b3, p4))))
    }

    @Test
    fun empty_slot_in_one_track() {
        val a1 = Session(title = "A1", slots = 1..3)
        val b1 = Session(title = "B1", slots = 1..2)
        val p4 = Session(title = "P4", slots = 4..4)

        val sessions = setOf(a1, b1, p4)

        assertThat(allSchedules(sessions).toList(), equalTo(listOf(
            listOf(a1, p4),
            listOf(b1, p4))))
    }
}
