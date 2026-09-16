package nullability

/**
 * Exercise 1 / 3: call it safely.
 *
 * Write `shout`: it takes a nullable `String` and returns it in uppercase, or
 * `null` when there was nothing to shout. `uppercase()` behind a `?.`, no `if`.
 */

/**
 * Exercise 2 / 3: fall back with Elvis.
 *
 * Write `lengthOrZero`: it takes a nullable `String` and returns its length,
 * or `0` for `null`. `?.` gives an `Int?`, `?:` turns it into an `Int`.
 */

/**
 * Exercise 3 / 3: drop the `!!`.
 *
 * Remove both `!!`: the null check already smart-casts `name`.
 */
fun firstInitial(name: String?): Char {
  if (name == null || name!!.isEmpty()) return '?'
  return name!!.uppercase().first()
}
