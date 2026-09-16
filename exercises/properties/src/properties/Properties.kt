package properties

/**
 * Exercise 1 / 3: expose the read, keep the write.
 *
 * `count` can be read by anyone but only written by `Counter` itself: a
 * `private set` on the property, and `increment()` adds one.
 */
class Counter {
  var count: Int = 0

  fun increment(): Unit = TODO()
}

/**
 * Exercise 2 / 3: compute the getter.
 *
 * Add two read-only properties: `count`, the number of items, and `isEmpty`,
 * whether there are none. Both are computed by a `get()` from `items`, so
 * neither needs a backing field.
 */
class Inventory {
  private val items = mutableListOf<String>()

  fun add(item: String) {
    items.add(item)
  }
}

/**
 * Exercise 3 / 3: validate in the setter.
 *
 * `target` accepts values from `5` to `30` only: a custom `set(value)` that
 * `require`s the range before writing `field`.
 */
class Thermostat {
  var target: Int = 20
}
