package functions

fun average(a: Int, b: Int): Int = (a + b) / 2

fun increment(x: Int, by: Int = 1): Int = x + by

fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

fun twoTerms(a: Int, c: Int): Int = sum(a, c = c)

fun largest(vararg numbers: Int): Int = numbers.max()

fun Int.isEven(): Boolean = this % 2 == 0

infix fun Int.percentOf(total: Int): Int = total * this / 100

operator fun Int.times(word: String): String = word.repeat(this)
