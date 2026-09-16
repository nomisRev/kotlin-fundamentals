package closedtypes

import kotlin.math.PI

data class Version(val major: Int, val minor: Int) {
  companion object {
    fun parse(text: String): Version {
      val parts = text.split(".")
      return Version(parts[0].toInt(), parts[1].toInt())
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
