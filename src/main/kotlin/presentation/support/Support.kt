// Hand-written context for the generated snippets in ../snippets/.
//
// The slides use these symbols without defining them on every slide; each
// generated file star-imports this package. A snippet that defines its own
// symbol of the same name shadows the one here, because a declaration in the
// file's own package wins over a star import.
package presentation.support

// ---------------------------------------------------------------------------
// Lesson 2 - Kotlin Syntax

/** The arbitrary expression a `when` branch matches against on the "when matches on a subject" slides. */
fun specialValue(): Int = 42

/** The subject bound with `val x = calculate()` on the same slides. */
fun calculate(): Int = 42

/** The condition of the `if` on the "`if` vs ternary operator" slide. */
fun predicate(): Boolean = true

// ---------------------------------------------------------------------------
// Lesson 3 - Classes and types

/** An annotation shown on a primary constructor, standing in for any user-defined annotation. */
annotation class MyAnnotation

// ---------------------------------------------------------------------------
// Lesson 4 - Classes and types

/** The domain types of the "Give a value a domain type" slides, defined on the first of them. */
@JvmInline
value class EmployeeId(val value: Long)

@JvmInline
value class DepartmentId(val value: Long)

@JvmInline
value class Email(val value: String)

@JvmInline
value class Name(val value: String)

data class Employee(
  val id: EmployeeId,
  val departmentId: DepartmentId,
  val name: Name,
  val email: Email,
)

/** The callback API the "Object expressions" slide registers an anonymous handler with. */
class Event

abstract class EventHandler {
  abstract fun onEvent(event: Event)
}

class Client {
  fun addEventHandler(handler: EventHandler) {}
}

val client = Client()
