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

fun <A, T> apply(f: (A) -> T, ra: Result<A>) = ra.map(f)

fun <A, B, C, T> apply(f: (A, B, C) -> T, ra: Result<A>, rb: Result<B>, rc: Result<C>) =
    ra.flatMap { a -> rb.flatMap { b -> rc.flatMap { c -> Result.Success(f(a, b, c)) } } }

fun <A, B, C, D, T> apply(f: (A, B, C, D) -> T, ra: Result<A>, rb: Result<B>, rc: Result<C>, rd: Result<D>) =
    ra.flatMap { a -> rb.flatMap { b -> rc.flatMap { c -> rd.flatMap { d -> Result.Success(f(a, b, c, d)) } } } }

fun <T> Iterable<Result<T>>.flatten(): Result<List<T>> = Result.Success(fold(ArrayList<T>(), { list, result ->
    when (result) {
        is Result.Success<T> -> list.apply { add(result.value) }
        is Result.Failure -> return@flatten result
    }
}))

