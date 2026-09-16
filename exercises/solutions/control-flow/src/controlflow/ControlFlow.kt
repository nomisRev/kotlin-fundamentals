package controlflow

fun abs(x: Int): Int = if (x < 0) -x else x

fun grade(score: Int): String = when {
  score >= 90 -> "A"
  score >= 80 -> "B"
  else -> "F"
}

fun ticketPrice(age: Int, student: Boolean): Int = when (age) {
  in 0..11 -> 5
  in 12..64 if student -> 8
  in 12..64 -> 12
  else -> 6
}

enum class Light { RED, AMBER, GREEN }

fun next(light: Light): Light = when (light) {
  Light.RED -> Light.GREEN
  Light.GREEN -> Light.AMBER
  Light.AMBER -> Light.RED
}

fun digitCount(n: Int): Int {
  var remaining = n
  var digits = 0
  do {
    remaining /= 10
    digits++
  } while (remaining > 0)
  return digits
}

fun sumUntil(numbers: List<Int>, stopAt: Int): Int {
  var total = 0
  for (number in numbers) {
    if (number == stopAt) break
    total += number
  }
  return total
}
