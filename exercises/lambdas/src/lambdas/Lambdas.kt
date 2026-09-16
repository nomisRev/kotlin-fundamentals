package lambdas

/**
 * Exercise 1 / 4: write a lambda.
 *
 * Declare `val isEven: (Int) -> Boolean` and assign it a lambda. One
 * parameter, so `it` will do.
 */

/**
 * Exercise 2 / 4: name the parameters.
 *
 * Declare `val add: (Int, Int) -> Int`. Two parameters, so `it` is off the
 * table.
 */

/**
 * Exercise 3 / 4: give the lambda a receiver.
 *
 * Declare `val shout: String.() -> String` that returns the receiver in
 * uppercase followed by `!`. `this` is the `String`, so `uppercase()` needs
 * no qualifier.
 */

/**
 * Exercise 4 / 4: reference instead of wrap.
 *
 * Replace the lambda with a `::` reference.
 */
val len: (String) -> Int = { it.length }
