package learnyouakotlin.solution


data class Session(val code: SessionCode, val title: String, val subtitle: String? = null, val presenters: List<Presenter>) {
    constructor(code: SessionCode, title: String, subtitle: String? = null, vararg presenters: Presenter)
    : this(code, title, subtitle, listOf(*presenters)) {
    }
}

fun Session.withPresenters(newLineUp: List<Presenter>) = copy(presenters = newLineUp)
fun Session.withTitle(newTitle: String) = copy(title = newTitle)
fun Session.withSubtitle(newSubtitle: String?) = copy(subtitle = newSubtitle)

data class Presenter(val name: String) {
    override fun toString() = name
}

fun <T> T.given(f: (T) -> Boolean) = if (f(this)) this else null

data class SessionCode(private val repr: Int) {
    override fun toString() = repr.toString()

    companion object {
        fun parse(s: String) =
            try { Integer.valueOf(s) } catch (e: NumberFormatException) { null }
                ?.given { it > 0 }
                ?.let { SessionCode(it) }
    }
}

