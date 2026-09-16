package functions

/**
 * Exercise 1 / 7: drop the block.
 *
 * Rewrite `average` with `=`: no braces and no `return`.
 */
fun average(a: Int, b: Int): Int {
  val total = a + b
  return total / 2
}

/**
 * Exercise 2 / 7: add a default.
 *
 * `by` should be optional and fall back to `1`, so that `increment(1)` compiles.
 */
fun increment(x: Int, by: Int): Int = x + by

/**
 * Exercise 3 / 7: name the argument.
 *
 * `twoTerms` adds `a` and `c` by calling `sum` without a `b`. `sum` stays as it is.
 */
fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

fun twoTerms(a: Int, c: Int): Int = TODO()

/**
 * Exercise 4 / 7: take any number of arguments.
 *
 * Write `largest`: it takes any number of `Int`s and returns the biggest one,
 * so that `largest(3, 9, 4)`, `largest(7)` and `largest(*scores)` all compile.
 * `IntArray` already has `max()`.
 */

/**
 * Exercise 5 / 7: extend `Int`.
 *
 * Write `isEven` as an extension on `Int` that returns a `Boolean`, so that
 * `4.isEven()` compiles. `this` is the receiver.
 */

/**
 * Exercise 6 / 7: make it infix.
 *
 * Write `percentOf` so that `10 percentOf 200` reads like a sentence and is `20`.
 */

/**
 * Exercise 7 / 7: implement `*`.
 *
 * Make `3 * "Ho"` produce `"HoHoHo"`. `*` desugars to `times`, the number is
 * the receiver this time, and `String.repeat` does the work.
 */
