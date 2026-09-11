package presentation.support

/** Shared dataset shown at the start of lesson 4. */
data class Member(val name: String, val age: Int)

val members = listOf(Member("Ada", 36), Member("Linus", 17), Member("Grace", 28))

fun olderThan(age: Int): (Member) -> Boolean = { member -> member.age > age }

// Teaching implementations shown in lesson 4, reused by later independent snippets.
fun List<Member>.select(predicate: (Member) -> Boolean): List<Member> {
  val result = mutableListOf<Member>()
  for (member in this) {
    if (predicate(member)) result.add(member)
  }
  return result
}

fun List<Member>.firstMatching(predicate: (Member) -> Boolean): Member? {
  for (member in this) {
    if (predicate(member)) return member
  }
  return null
}
