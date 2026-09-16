---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Appendix</div>

# Experimental compiler flags

## Every `-X` flag this deck turns on, Kotlin 2.4.20

---

# Square brackets build a collection

> `-Xcollection-literals`

<DrawnAnnotation text="[1, 2, 3, 4, 5, 6]" label="A collection literal, no `listOf` call needed"/>

```kotlin
fun sum(numbers: Iterable<Int>): Int = numbers.sum()

val six = sum([1, 2, 3])
val twentyOne = sum([1, 2, 3, 4, 5, 6])
```

---

# Ignoring a return value is a warning

> `-Xreturn-value-checker=full`

<DrawnAnnotation text="@MustUseReturnValues"/>
<DrawnAnnotation text="calculator.calculate()" label="Ignoring the result is a compiler warning" color="red"/>

```kotlin
@MustUseReturnValues
class Calculator {
  fun calculate(): Int = 42
}

fun example(calculator: Calculator) {
  calculator.calculate()
  val result = calculator.calculate()
}
```

---

# A contract can name the real must-use call

> `-Xallow-returns-result-of` sharpens `-Xreturn-value-checker=full`

<DrawnAnnotation text="Scoreboard().myLet" label="`myLet` is generic, `full` mode still infers it forwards a must-use result — but the warning names `myLet`, not `computeScore`" color="red"/>

```kotlin
@MustUseReturnValues
class Scoreboard {
  fun computeScore(): Int = 42
}

fun <T, R> T.myLet(block: (T) -> R): R {
  println("running…")
  return block(this)
}

Scoreboard().myLet { it.computeScore() }
```

---
magic-move
---

# A contract can name the real must-use call

> `-Xallow-returns-result-of` sharpens `-Xreturn-value-checker=full`

<DrawnAnnotation text="returnsResultOf(block)"/>
<DrawnAnnotation text="Scoreboard().myLet" label="The warning now names `computeScore`" color="red"/>

```kotlin
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@MustUseReturnValues
class Scoreboard {
  fun computeScore(): Int = 42
}

@OptIn(ExperimentalContracts::class)
fun <T, R> T.myLet(block: (T) -> R): R {
  contract { returnsResultOf(block) }
  println("running…")
  return block(this)
}

Scoreboard().myLet { it.computeScore() }
```

<!--
Without the contract, `-Xreturn-value-checker=full` already infers that myLet's
result must be used, but the warning it reports points at `myLet` itself.

The `returnsResultOf` contract tells the checker that myLet's must-use-ness is
exactly whatever `block` returns, so the warning follows the reference all the
way back to `computeScore`, the function that actually made the value must-use.
-->

---

# Context-sensitive resolution drops the qualifier

> `-Xcontext-sensitive-resolution` · see Lesson 4, "Context-sensitive resolution drops the qualifier"

<DrawnAnnotation text="CONNECTION ->" label="`Problem.` is implied by the subject"/>

```kotlin
enum class Problem { CONNECTION, AUTHENTICATION, DATABASE }

fun message(problem: Problem): String = when (problem) {
  CONNECTION -> "Connection failed"
  AUTHENTICATION -> "Authentication failed"
  DATABASE -> "Database failed"
}
```

---

# More constants evaluate at compile time

> `-Xintrinsic-const-evaluation`

<DrawnAnnotation text=".uppercase()" label="A stdlib call marked intrinsic, not just arithmetic"/>

```kotlin
const val greeting = "Hello, Kotlin!"
const val length = greeting.length
const val upper = "kotlin".uppercase()
```

---

# A companion block adds members in place

> `-Xcompanion-blocks-and-extensions`

<DrawnAnnotation text="companion {" label="Shorthand for `companion object { }`" :geometry="{ label: { x: 0.5575, y: 0.4891 } }"/>

```kotlin
class Money(val cents: Int) {
  companion {
    fun zero(): Money = Money(0)
  }
}

println(Money.zero().cents)
```

---

# A companion extension reaches types you don't own

> `-Xcompanion-blocks-and-extensions`

<DrawnAnnotation text="companion fun LocalDate.epoch()" label="`LocalDate` is a Java class, it has no Kotlin companion to extend"/>

```kotlin
import java.time.LocalDate

companion fun LocalDate.epoch(): LocalDate = LocalDate.ofEpochDay(0)

println(LocalDate.epoch())
```

---

# Context parameters can be passed by name

> `-Xexplicit-context-arguments` (context parameters are stable in 2.4)

<DrawnAnnotation text="logger = Logger(&quot;APP&quot;)" label="Named like any other argument, no `with` block needed"/>

```kotlin
class Logger(val tag: String) {
  fun log(message: String) = println("[$tag] $message")
}

context(logger: Logger)
fun greet(name: String) {
  logger.log("hello, $name")
}

greet(logger = Logger("APP"), name = "Grace")
```

---

# A `typealias` can live inside a function

> `-Xlocal-type-aliases`

<DrawnAnnotation text="typealias Score = Int" label="Scoped to the function, invisible outside it"/>

```kotlin
fun demo() {
  typealias Score = Int
  val score: Score = 42
  println(score)
}
```

---

# Destructuring matches by name, not position

> `-Xname-based-destructuring=complete` · see Lesson 4, "Name-based destructuring is safe"

<DrawnAnnotation text="(y, x)" label="Declared out of order, still binds by property name"/>

```kotlin
data class Point(val x: Int, val y: Int)

fun demo() {
  val point = Point(x = 1, y = 2)
  val (y, x) = point

  println("y=$y x=$x") // y=2 x=1
}
```

---
magic-move
---

# Destructuring matches by name, not position

> `-Xname-based-destructuring=complete` · see Lesson 4, "Name-based destructuring is safe"

<DrawnAnnotation text="[a, b]" label="Square brackets stay positional, via `componentN()`"/>

```kotlin
data class Point(val x: Int, val y: Int)

fun demo() {
  val point = Point(x = 1, y = 2)
  val [a, b] = point

  println("a=$a b=$b") // a=1 b=2
}
```

---

# A Java class can actualize an `expect` directly

> `-Xdirect-java-actualization` · multiplatform only, not compiled in this JVM-only deck

<DrawnAnnotation text="expect class Logger()"/>

```kotlin no-compile
// commonMain
expect class Logger() {
  fun log(message: String)
}
```

<InlineCompilerError text="Logger" message="the corresponding Java declaration 'example.Logger' must be marked with '@kotlin.jvm.KotlinActual'.">

```java
// jvmMain
public final class Logger {
  public Logger() {}
  public void log(String message) {
    System.out.println(message);
  }
}
```

</InlineCompilerError>

<!--
This is the real diagnostic kotlinc 2.4.20 reports for a plain, unannotated
Java class standing in for an expect declaration. Direct actualization needs
every actualizing member marked `@kotlin.jvm.KotlinActual`; once it is, no
`actual typealias` wrapper is needed at all. This deck has no multiplatform
target, so this slide documents the mechanism rather than compiling it.
-->

---
zoom: 0.8
---

# Every flag this build turns on

| Flag | Enables |
| --- | --- |
| `-Xcollection-literals` | `[1, 2, 3]` collection literals |
| `-Xreturn-value-checker=full` | `@MustUseReturnValues` plus automatic annotation insertion |
| `-Xallow-returns-result-of` | `returnsResultOf` contracts for the checker above |
| `-Xcontext-sensitive-resolution` | Bare enum entries in a `when` on that enum |
| `-Xintrinsic-const-evaluation` | More stdlib calls allowed in `const val` initializers |
| `-Xcompanion-blocks-and-extensions` | `companion { }` blocks and extensions on companion-less types |
| `-Xexplicit-context-arguments` | Passing context parameters by name at the call site |
| `-Xlocal-type-aliases` | `typealias` declared inside a function body |
| `-Xname-based-destructuring=complete` | `val (a, b)` by property name, `val [a, b]` stays positional |
| `-Xdirect-java-actualization` | A Java class actualizes an `expect` without an `actual typealias` |

> **`kotlinc -X` still lists ~15 other flags for language version 2.4 as redundant — their features already shipped.**
