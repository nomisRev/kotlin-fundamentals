package closedtypes

import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class ClosedTypesTest {
  @Test
  fun `parse is a static factory`() {
    assertEquals(Version(2, 4), Version.parse("2.4"))
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
