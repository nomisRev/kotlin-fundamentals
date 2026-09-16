package presentation.support

// Lesson 5 - DSLs and builders
//
// The "Building a DSL" slides grow `HtmlBuilder` step by step and hand the
// buffer to a `BodyBuilder` they never show; this is that builder.

class BodyBuilder(private val buffer: StringBuilder) {
  fun div(block: BodyBuilder.() -> Unit) {
    buffer.append("<div>")
    block()
    buffer.append("</div>")
  }

  operator fun String.unaryPlus() {
    buffer.append(this)
  }
}
