package learnyouakotlin.solution

import java.util.*

// TODO - make it lazy
fun allSchedules(iterable: Iterable<Session>): Sequence<List<Session>> {
    fun allSchedulesFrom(sessions: NavigableMap<Int, List<Session>>, startSlot: Int): List<List<Session>> =
        sessions.ceilingEntry(startSlot)?.value.orEmpty().flatMap { session ->
            val laterSchedules = allSchedulesFrom(sessions, session.slots.endInclusive + 1)
            if (laterSchedules.isEmpty()) {
                listOf(listOf(session))
            }
            else {
                laterSchedules.map { listOf(session) + it }
            }
        }

    return allSchedulesFrom(TreeMap(iterable.groupBy { it.slots.start }), 1).asSequence()
}

// TODO - implemented weighted reservoir sampling to make selection fair for multi-slot sessions
fun <T> Random.sample(seq : Sequence<T>) : T? =
    seq.foldIndexed(null as T?) { i: Int, selected: T?, newItem: T ->
        if (nextDouble() < 1.0/(i+1).toDouble()) newItem else selected
    }

