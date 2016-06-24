package learnyouakotlin.solution

import java.util.*

fun List<Session>.allSchedules(): List<List<Session>> {
    fun allSchedulesFrom(sessions: NavigableMap<Int, List<Session>>, startSlot: Int): List<List<Session>> {
        val sessionsInSlot = sessions.ceilingEntry(startSlot)?.value.orEmpty()

        return sessionsInSlot.flatMap { session ->
            val laterSchedules = allSchedulesFrom(sessions, session.slots.endInclusive + 1)
            if (laterSchedules.isEmpty()) {
                listOf(listOf(session))
            }
            else {
                laterSchedules.map { listOf(session) + it }
            }
        }
    }

    return allSchedulesFrom(TreeMap(groupBy { it.slots.start }), 1)
}
