package closedtypes

/**
 * Exercise 1 / 3: parse in the companion.
 *
 * Make the constructor of `Version` private and give it a companion object
 * with a `parse(text: String): Version?` that turns `"2.4"` into a `Version`
 * with `major = 2` and `minor = 4`, and anything else into `null`, so that
 * `Version.parse("2.4")` reads like a static factory and is the only way to
 * get a `Version`. Keep `data` for `==`, and add `@ConsistentCopyVisibility`
 * so `copy()` does not reopen the door the private constructor closed.
 */
data class Version(val major: Int, val minor: Int)

/**
 * Exercise 2 / 3: carry data in an entry.
 *
 * Write an enum class `Priority` with the entries `LOW`, `MEDIUM` and `HIGH`,
 * each carrying a `level: Int` of `1`, `2` and `3`.
 */

/**
 * Exercise 3 / 3: close the hierarchy.
 *
 * Write a sealed class `Shape` with three nested subtypes: a
 * `data object Empty`, a `data class Circle(val radius: Double)` and a
 * `data class Rectangle(val width: Double, val height: Double)`. Then write
 * `area(shape: Shape): Double` as one exhaustive `when` without `else`.
 */
