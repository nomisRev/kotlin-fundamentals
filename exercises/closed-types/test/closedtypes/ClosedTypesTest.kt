package closedtypes

import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ClosedTypesTest {
  @Test
  fun `parse is a static factory`() {
    val version = Version.parse("2.4")
    assertEquals(2, version?.major)
    assertEquals(4, version?.minor)
    assertEquals(Version.parse("2.4"), version)
  }

  @Test
  fun `parse rejects what is not a version`() {
    assertNull(Version.parse("banana"))
    assertNull(Version.parse("2"))
  }

  @Test
  fun `entries carry a level`() {
    assertEquals(3, Priority.HIGH.level)
    assertEquals(listOf(1, 2, 3), Priority.entries.map { it.level })
    assertEquals(Priority.LOW, Priority.valueOf("LOW"))
  }

  @Test
  fun `area covers every subtype`() {
    assertEquals(0.0, area(Shape.Empty))
    assertEquals(PI, area(Shape.Circle(1.0)), 1e-9)
    assertEquals(6.0, area(Shape.Rectangle(2.0, 3.0)))
  }

  @Test
  fun `a data object prints its name`() {
    assertEquals("Empty", Shape.Empty.toString())
  }
}
