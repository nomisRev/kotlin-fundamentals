package properties

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PropertiesTest {
  @Test
  fun `increment is the only write`() {
    val counter = Counter()
    counter.increment()
    counter.increment()
    assertEquals(2, counter.count)
  }

  @Test
  fun `count and isEmpty follow the items`() {
    val inventory = Inventory()
    assertTrue(inventory.isEmpty)
    inventory.add("Kodee")
    assertEquals(1, inventory.count)
    assertFalse(inventory.isEmpty)
  }

  @Test
  fun `target rejects values outside 5 to 30`() {
    val thermostat = Thermostat()
    thermostat.target = 25
    assertEquals(25, thermostat.target)
    assertFailsWith<IllegalArgumentException> { thermostat.target = 40 }
    assertEquals(25, thermostat.target)
  }
}
