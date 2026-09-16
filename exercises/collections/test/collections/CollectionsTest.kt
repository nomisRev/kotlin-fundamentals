package collections

import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionsTest {
  private val ada = Member("Ada", 36)
  private val linus = Member("Linus", 17)
  private val grace = Member("Grace", 25)

  @Test
  fun `names maps every member`() {
    assertEquals(listOf("Ada", "Linus"), names(listOf(ada, linus)))
  }

  @Test
  fun `adults keeps the members of 18 and older`() {
    assertEquals(listOf(ada), adults(listOf(ada, linus)))
  }

  @Test
  fun `letters flattens the words`() {
    assertEquals(listOf('a', 'b', 'c'), letters(listOf("ab", "c")))
  }

  @Test
  fun `numbers drops what does not parse`() {
    assertEquals(listOf(1, 3), numbers(listOf("1", "two", "3")))
  }

  @Test
  fun `byLength groups on the length`() {
    assertEquals(mapOf(1 to listOf("a"), 2 to listOf("bb", "cc")), byLength(listOf("a", "bb", "cc")))
  }

  @Test
  fun `adultNamesByAge chains filter, sortedBy and map`() {
    assertEquals(listOf("Grace", "Ada"), adultNamesByAge(listOf(ada, linus, grace)))
  }
}
