package learnyouakotlin.solution.part4

import learnyouakotlin.solution.part1.Session
import java.util.*

// TODO - make it lazy
fun allSchedules(sessions: Iterable<Session>): Sequence<List<Session>> {
    val sessionsByStartTime = TreeMap(sessions.groupBy { it.slots.start })

    fun allSchedulesFrom(startSlot: Int): List<List<Session>> =
        sessionsByStartTime.ceilingEntry(startSlot)?.value.orEmpty().flatMap { session ->
            val laterSchedules = allSchedulesFrom(session.slots.endInclusive + 1)
            if (laterSchedules.isEmpty()) {
                listOf(listOf(session))
            }
            else {
                laterSchedules.map { listOf(session) + it }
            }
        }

    return allSchedulesFrom(1).asSequence()
}

// TODO - implemented weighted reservoir sampling to make selection fair for multi-slot sessions
fun <T> Random.sample(seq : Sequence<T>) : T? =
    seq.foldIndexed(null as T?) { i: Int, selected: T?, newItem: T ->
        if (nextDouble() < 1.0/(i+1).toDouble()) newItem else selected
    }

