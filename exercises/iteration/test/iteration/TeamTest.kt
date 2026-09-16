package iteration

import kotlin.test.Test
import kotlin.test.assertEquals

class TeamTest {
  @Test
  fun `a Team can be iterated`() {
    val team = Team(listOf(Member("Ada", 36), Member("Linus", 17)))
    assertEquals(listOf("Ada", "Linus"), team.map { it.name })
  }

  @Test
  fun `a Team is a literal target`() {
    val team: Team = [Member("Ada", 36), Member("Linus", 17)]
    assertEquals(2, team.count())
  }
}
