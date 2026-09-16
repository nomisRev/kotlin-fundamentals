package controlflow

/**
 * Exercise 1 / 6: make `if` the value.
 *
 * Rewrite `abs` as one `if` expression: no `var` and no `return`.
 */
fun abs(x: Int): Int {
  var result = x
  if (x < 0) result = -x
  return result
}

/**
 * Exercise 2 / 6: replace the `if` chain.
 *
 * Rewrite `grade` with a subject-less `when`.
 */
fun grade(score: Int): String =
  if (score >= 90) "A"
  else if (score >= 80) "B"
  else "F"

/**
 * Exercise 3 / 6: add a guard.
 *
 * Students aged 12 to 64 pay `8`. One guarded branch does it.
 */
fun ticketPrice(age: Int, student: Boolean): Int = when (age) {
  in 0..11 -> 5
  in 12..64 -> 12
  else -> 6
}

enum class Light { RED, AMBER, GREEN }

/**
 * Exercise 4 / 6: cover every entry.
 *
 * Write `next`: it takes a [Light] and returns the one that follows it,
 * `RED` to `GREEN`, `GREEN` to `AMBER`, `AMBER` to `RED`. A `when (light)`
 * without `else` does it: the compiler knows all three entries.
 */

/**
 * Exercise 5 / 6: run the body first.
 *
 * Write `digitCount`: it takes a non-negative `Int` and returns how many
 * digits it has. A `do` loop divides by `10` at least once, so `0` still has
 * one digit.
 */

/**
 * Exercise 6 / 6: stop early.
 *
 * Write `sumUntil`: it takes a `List<Int>` and a `stopAt` value and adds up
 * the numbers in order, `break`ing out of the `for` loop when it reaches
 * `stopAt`.
 */
