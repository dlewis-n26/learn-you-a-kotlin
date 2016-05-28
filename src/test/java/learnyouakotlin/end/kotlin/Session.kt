package learnyouakotlin.end.kotlin


data class Session(val code: SessionCode, val title: String, val presenters: List<Presenter>) {
    constructor(code: SessionCode, title: String, vararg presenters: Presenter) : this(code, title, listOf(*presenters)) {
    }
}

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
