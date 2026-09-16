package dataclasses

data class Member(val name: String, val age: Int)

@JvmInline
value class Age(val value: Int) : Comparable<Age> {
  init {
    require(value >= 0) { "An age cannot be negative" }
  }

  override fun compareTo(other: Age): Int = value.compareTo(other.value)
}
