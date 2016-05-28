package learnyouakotlin.end.kotlin

import java.text.ParseException


data class Session(val code: SessionCode, val title: String, val presenters: List<Presenter>) {
    constructor(code: SessionCode, title: String, vararg presenters: Presenter) : this(code, title, listOf(*presenters)) {
    }
}

data class Presenter(val name: String) {
    override fun toString() = name
}

data class SessionCode(private val repr: Int) {
    override fun toString() = repr.toString()

    companion object {
        @Throws(ParseException::class)
        fun parse(s: String): SessionCode {
            try {
                val repr = Integer.valueOf(s)!!

                if (repr > 0) {
                    return SessionCode(repr)
                }
                else {
                    throw ParseException("catalog number must be greater than zero, was: " + s, 0)
                }
            }
            catch (e: NumberFormatException) {
                throw ParseException("invalid catalog number: " + s, 0)
            }
        }
    }
}
