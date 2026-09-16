package iteration

class Member(val name: String, val age: Int)

/**
 * Exercise 1 / 2: make `Team` iterable.
 *
 * Implement `Iterable<Member>` and `override` its `iterator()`, so that a
 * `for` loop and the whole collections API work on a `Team`.
 *
 * Exercise 2 / 2: make `Team` a literal target.
 *
 * Add a companion `operator fun of(vararg members: Member): Team`, so that
 * `val team: Team = [ada, linus]` compiles.
 */
class Team(private val members: List<Member>)
