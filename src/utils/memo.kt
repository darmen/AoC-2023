package utils

interface Memo<A, R> {
    fun recurse(a: A): R
}

fun <A, R> (Memo<A, R>.(A) -> R).memoize(): (A) -> R {
    val memoized = object : Memoized<A, R>() {
        override fun Memo<A, R>.function(a: A): R = this@memoize(a)
    }
    return { a ->
        memoized.execute(a)
    }
}

abstract class Memoized<A, R> {
    private val cache = mutableMapOf<A, R>()
    private val memo = object : Memo<A, R> {
        override fun recurse(a: A): R  {
            return cache.getOrPut(a) { function(a) }
        }
    }

    protected abstract fun Memo<A, R>.function(a: A): R

    fun execute(a: A): R = memo.recurse(a)
}