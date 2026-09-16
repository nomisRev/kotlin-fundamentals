package nullability

fun shout(text: String?): String? = text?.uppercase()

fun lengthOrZero(text: String?): Int = text?.length ?: 0

fun firstInitial(name: String?): Char {
  if (name == null || name.isEmpty()) return '?'
  return name.uppercase().first()
}
