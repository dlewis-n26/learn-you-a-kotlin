package learnyouakotlin.solution.part1


data class Session(val title: String, val subtitle: String? = null, val slots: IntRange, val presenters: List<Presenter>) {
    constructor(title: String, subtitle: String? = null, slots: IntRange, vararg presenters: Presenter)
    : this(title, subtitle, slots, listOf(*presenters))
}

fun Session.withPresenters(newLineUp: List<Presenter>) = copy(presenters = newLineUp)
fun Session.withTitle(newTitle: String) = copy(title = newTitle)
fun Session.withSubtitle(newSubtitle: String?) = copy(subtitle = newSubtitle)

