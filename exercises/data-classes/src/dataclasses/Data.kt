package dataclasses

/**
 * Exercise 1 / 3: declare `Member`.
 *
 * Write a data class `Member` with a `name: String` and an `age: Int`, so that
 * two members with the same values are equal, a member prints as
 * `Member(name=Ada, age=36)`, and `copy(age = 37)` changes one property.
 */

/**
 * Exercise 2 / 3: give `age` a domain type.
 *
 * Write a value class `Age` (`@JvmInline`) around `val value: Int` whose
 * `init` block `require`s the value to be `0` or more.
 *
 * Exercise 3 / 3: make `Age` sortable.
 *
 * Make `Age` implement `Comparable<Age>` by delegating `compareTo` to the
 * wrapped `Int`, so that `Age(17) < Age(36)` holds and a list of ages can be
 * `sorted()`.
 */
