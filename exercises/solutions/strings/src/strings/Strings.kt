package strings

fun birthday(name: String, age: Int): String = "$name turns ${age + 1} next year"

fun receipt(item: String, price: Double): String =
  """
    $item costs $price
    ----------
    Thank you!
  """.trimIndent()

fun payload(name: String): String = $$"{ \"$id\": \"$$name\" }"
