package iteration

class Member(val name: String, val age: Int)

class Team(private val members: List<Member>) : Iterable<Member> {
  override operator fun iterator(): Iterator<Member> = members.iterator()

  companion object {
    operator fun of(vararg members: Member): Team = Team(members.toList())
  }
}
