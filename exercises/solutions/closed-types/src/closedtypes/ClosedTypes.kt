package closedtypes

import kotlin.math.PI

@ConsistentCopyVisibility
data class Version private constructor(val major: Int, val minor: Int) {
  companion object {
    fun parse(text: String): Version? {
      val parts = text.split(".")
      if (parts.size != 2) return null
      val major = parts[0].toIntOrNull() ?: return null
      val minor = parts[1].toIntOrNull() ?: return null
      return Version(major, minor)
    }
  }
}

enum class Priority(val level: Int) {
  LOW(1),
  MEDIUM(2),
  HIGH(3),
}

sealed class Shape {
  data object Empty : Shape()
  data class Circle(val radius: Double) : Shape()
  data class Rectangle(val width: Double, val height: Double) : Shape()
}

fun area(shape: Shape): Double = when (shape) {
  Shape.Empty -> 0.0
  is Shape.Circle -> PI * shape.radius * shape.radius
  is Shape.Rectangle -> shape.width * shape.height
}
