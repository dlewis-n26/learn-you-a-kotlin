package learnyouakotlin.end.kotlin

import java.util.*

sealed class Result<out T> {
    abstract fun <U> map(f: (T) -> U): Result<U>
    abstract fun <U> flatMap(f: (T) -> Result<U>): Result<U>
    abstract val value: T?
    abstract val error: Throwable?

    class Success<T>(override val value: T) : Result<T>() {
        override val error: Throwable? get() = null
        override fun <U> map(f: (T) -> U) = Success(f(value))
        override fun <U> flatMap(f: (T) -> Result<U>) = f(value)
    }

    class Failure(override val error: Throwable) : Result<Nothing>() {
        override val value: Nothing? get() = null
        override fun <U> map(f: (Nothing) -> U) = this
        override fun <U> flatMap(f: (Nothing) -> Result<U>) = this
    }
}

fun <T> Iterable<Result<T>>.flatten(): Result<List<T>> = Result.Success(fold(ArrayList<T>(), { list, result ->
    when (result) {
        is Result.Success<T> -> list.add(result.value)
        is Result.Failure -> return@flatten result
    }
    list
}))

