package learnyouakotlin.solution.part1

typealias Slots = IntRange

data class Session(val title: String, val subtitle: String? = null, val slots: Slots, val presenters: List<Presenter>) {
    constructor(title: String, subtitle: String? = null, slots: Slots, vararg presenters: Presenter)
    : this(title, subtitle, slots, listOf(*presenters))
}

fun Session.withPresenters(newLineUp: List<Presenter>) = copy(presenters = newLineUp)
fun Session.withTitle(newTitle: String) = copy(title = newTitle)
fun Session.withSubtitle(newSubtitle: String?) = copy(subtitle = newSubtitle)

