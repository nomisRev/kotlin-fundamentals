package properties

class Counter {
  var count: Int = 0
    private set

  fun increment() {
    count++
  }
}

class Inventory {
  private val items = mutableListOf<String>()

  fun add(item: String) {
    items.add(item)
  }

  val count: Int
    get() = items.size

  val isEmpty: Boolean
    get() = items.isEmpty()
}

class Thermostat {
  var target: Int = 20
    set(value) {
      require(value in 5..30) { "Target must be between 5 and 30" }
      field = value
    }
}
