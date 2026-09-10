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

// ---------------------------------------------------------------------------
// Lesson 3 - Classes and types

/** An annotation shown on a primary constructor, standing in for any user-defined annotation. */
annotation class MyAnnotation
