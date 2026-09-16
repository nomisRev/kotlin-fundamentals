package strings

/**
 * Exercise 1 / 3: interpolate.
 *
 * Write `birthday`: it takes a `name` and an `age` and returns
 * `"Ada turns 37 next year"` for Ada aged 36. `$name` for the variable,
 * `${...}` for the expression.
 */

/**
 * Exercise 2 / 3: frame it with a raw string.
 *
 * Write `receipt`: it takes an `item` and a `price` (a `Double`) and returns
 * three lines: `Coffee costs 3.5`, ten dashes, `Thank you!`. One raw string
 * with `$item` and `$price`; `trimIndent()` removes the indentation.
 */

/**
 * Exercise 3 / 3: stop escaping `$`.
 *
 * Rewrite `payload` with a `$$` prefix so that `$id` needs no `${'$'}`.
 */
fun payload(name: String): String = "{ \"${'$'}id\": \"$name\" }"
