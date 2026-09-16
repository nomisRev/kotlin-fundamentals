package collections

class Member(val name: String, val age: Int)

/**
 * Exercise 1 / 6: transform.
 *
 * Write `names`: it takes a `List<Member>` and returns their names. `map`
 * each `Member` to its `name`.
 */

/**
 * Exercise 2 / 6: select.
 *
 * Write `adults`: it takes a `List<Member>` and returns the members whose
 * `age` is at least `18`. `filter` on `age >= 18`.
 */

/**
 * Exercise 3 / 6: flatten.
 *
 * Write `letters`: it takes a `List<String>` and returns every character of
 * every word, in order. `flatMap`; `toList()` turns a `String` into its
 * `Char`s.
 */

/**
 * Exercise 4 / 6: keep the non-null type.
 *
 * Write `numbers`: it takes a `List<String>` and returns the ones that parse
 * as an `Int`, as a `List<Int>`. `toIntOrNull()` returns `Int?`, `mapNotNull`
 * drops the `null`s.
 */

/**
 * Exercise 5 / 6: group.
 *
 * Write `byLength`: it takes a `List<String>` and groups the words by their
 * length into a `Map<Int, List<String>>`. `groupBy` on `length`.
 */

/**
 * Exercise 6 / 6: chain.
 *
 * Write `adultNamesByAge`: it takes a `List<Member>` and returns the names of
 * the adults, youngest first. `filter`, then `sortedBy`, then `map`.
 */
