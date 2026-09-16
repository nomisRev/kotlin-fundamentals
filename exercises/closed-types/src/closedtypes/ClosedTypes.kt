package closedtypes

/**
 * Exercise 1 / 3: parse in the companion.
 *
 * Give `Version` a companion object with a `parse(text: String): Version`
 * that turns `"2.4"` into `Version(2, 4)`, so that `Version.parse("2.4")`
 * reads like a static factory.
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
