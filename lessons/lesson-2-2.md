---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

# `if` is an expression

<InlineCompilerError text="if" message="'if' must have both main and 'else' branches when used as an expression.">

```kotlin
fun max(a: Int, b: Int) =
  if (a > b) a
```

</InlineCompilerError>

---
magic-move
---

# `if` is an expression

<InlineCompilerError text="if" message="'if' must have both main and 'else' branches when used as an expression.">
<DrawnAnnotation text="): Int =" label="A value is expected, so `if` is used as an expression" on="1" :geometry="{ label: { x: 0.5569, y: 0.3243 } }"/>

```kotlin
fun max(a: Int, b: Int): Int =
  if (a > b) a
```

</InlineCompilerError>

---
magic-move
---

# `if` is an expression

<DrawnAnnotation text="else b" label="Both branches supply the value" :geometry="{ label: { x: 0.4450, y: 0.3079 } }"/>

```kotlin
fun max(a: Int, b: Int): Int =
  if (a > b) a else b
```

---
magic-move
---

# `if` is an expression

<DrawnAnnotation text=") {"/>
<DrawnAnnotation text="second" :occurrence="3" label="The last expression is the value of the block" :geometry="{ label: { x: 0.3082, y: 0.6127 } }"/>

```kotlin
fun max(first: Int, second: Int): Int =
  if (first > second) {
    println("First")
    first
  } else {
    println("Second")
    second
  }
```

---

# `if` vs ternary operator

```kotlin
val value = if (predicate()) "truthy" else "falsey"
```
```java no-compile
String value = predicate() ? "truthy" : "falsey";
```

---

# `when` statements

<InlineCompilerError text="when" message="when expression must be exhaustive. Add an else branch">

```kotlin
fun max(a: Int, b: Int): Int = 
  when {
    a > b -> a
  }
```

</InlineCompilerError>

---
magic-move
---

# `when` statements

<DrawnAnnotation text="else ->" label="`else` covers  all other cases"  :geometry="{ label: { x: 0.2275, y: 0.4331 } }"/>

```kotlin
fun max(a: Int, b: Int): Int = 
  when {
    a > b -> a
    else -> b
  }
```

---

# `when` matches on a subject

```kotlin
fun describe(x: Int) = when (x) {
  1 -> "x == 1"
  2 -> "x == 2"
  else -> "x is neither 1 nor 2"
}
```

---
magic-move
---

# `when` matches on a subject

<DrawnAnnotation text="when (x)" label="A `when` statement does not need to be exhaustive\nStatement vs expression, same as `if` statement/expression"/>

```kotlin
fun describe(x: Int) {
  when (x) {
    1 -> println("x == 1")
    2 -> println("x == 2")
  }
}
```

---
magic-move
---

# `when` matches on a subject

<DrawnAnnotation text="3, 4" label="Branches can be combined" :geometry="{ label: { x: 0.2317, y: 0.5563 } }"/>

```kotlin
fun describe(x: Int) {
  when (x) {
    1 -> println("x == 1")
    2 -> println("x == 2")
    3, 4 -> println("3 or 4")
  }
}
```

---
magic-move
---

# `when` matches on a subject

<DrawnAnnotation text="-> {" label="A branch can be a block" :geometry="{ label: { x: 0.3320, y: 0.5713 } }"/>

```kotlin
fun describe(x: Int) {
  when (x) {
    1 -> println("x == 1")
    2 -> println("x == 2")
    3, 4 -> {
      println("Multiline code block!")
      println("3 or 4")
    }
  }
}
```

---
magic-move
---

# `when` matches on a subject

<DrawnAnnotation text="in 3..10" label="Ranges match with `in`\nWe'll learn about these in collections section" :geometry="{ label: { x: 0.3435, y: 0.5788 } }"/>

```kotlin
fun describe(x: Int) {
  when (x) {
    1 -> println("x == 1")
    2 -> println("x == 2")
    in 3..10 -> println("in 3..10")
  }
}
```

---
magic-move
---

# `when` matches on a subject

<DrawnAnnotation text="specialValue()" label="Arbitrary expressions can be matched" :geometry="{ label: { x: 0.2914, y: 0.4804 } }"/>

```kotlin
fun describe(x: Int) {
  when (x) {
    1 -> println("x == 1")
    2 -> println("x == 2")
    specialValue() -> println("x matches the special value")
  }
}
```

---
magic-move
---

# `when` matches on a subject

<DrawnAnnotation text="val x = calculate()" />
<DrawnAnnotation text="$x" label="Bind the subject to a value while matching" :geometry="{ label: { x: 0.2786, y: 0.4657 } }"/>

```kotlin
when (val x = calculate()) {
  1 -> println("x == 1")
  2 -> println("x == 2")
  specialValue() -> println("$x matches the special value")
}
```

---

# Guards add a condition to a match

> Stable since Kotlin 2.2.0

<DrawnAnnotation text="if x % 2 == 0" on="0" label="The guard runs only when the condition match succeeds" />
<DrawnAnnotation text="in 1..10" occurrence="3" label="We _should_ typically always specify counter condition" on="1" />

```kotlin
fun describe(x: Int) = when (x) {
    in 1..10 if x % 2 == 0 -> "even, in 1..10"
    in 1..10 -> "odd, in 1..10"
    else -> "outside 1..10"
}
```

---

# Exhaustiveness follows the data flow

> Stable since Kotlin 2.3.0

<DrawnAnnotation text="if (role == UserRole.ADMIN) return 99"/>
<DrawnAnnotation text="UserRole.GUEST -> 1" label="No `else` needed, the early return already handled `ADMIN`" :geometry="{ label: { x: 0.5861, y: 0.6086, width: 0.3150 }, connector: { type: 'quadratic', start: { x: 0.0941, y: 0.6858 }, control: { x: 0.3763, y: 0.7027 }, end: { x: 0.4286, y: 0.6317 } } }"/>

```kotlin
enum class UserRole { ADMIN, MEMBER, GUEST }

fun permission(role: UserRole): Int {
  if (role == UserRole.ADMIN) return 99

  return when (role) {
    UserRole.MEMBER -> 10
    UserRole.GUEST -> 1
  }
}
```

---

# `for` walks collections and ranges

```kotlin
for (item in ["One", "Two", "Three"]) {
    print("$item, ")
} // One, Two, Three,
```

---

# `while` checks before every iteration

```kotlin
var x = 0
while (x < 10) {
  print("$x, ")
  x++
} // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
```

---

# `do` runs the body once before checking

<DrawnAnnotation text="while (y != null)" label="The condition is checked after the first run" :geometry="{ label: { x: 0.3193, y: 0.5059 } }"/>

```kotlin no-compile
fun retrieveData(): Int? = null

do {
  println("Runs!")
  val y = retrieveData()
} while (y != null) // Runs!
```

---

# `continue`, `break` and `return` steer a loop

<DrawnAnnotation text="continue" label="Skip to the next iteration" :geometry="{ label: { x: 0.4477, y: 0.3704 } }"/>

```kotlin
fun printOdd() {
  for (d in 1..10) {
    if (d % 2 == 0) continue
    else print("$d, ")
  }
  println("done")
} // 1, 3, 5, 7, 9, done
```

---
magic-move
---

# `continue`, `break` and `return` steer a loop

<DrawnAnnotation text="break" label="Stop the loop" :geometry="{ label: { x: 0.4064, y: 0.3448 } }"/>

```kotlin
fun printOdd() {
  for (d in 1..10) {
    if (d % 2 == 0) break
    else print("$d, ")
  }
  println("done")
} // 1, done
```

---
magic-move
---

# `continue`, `break` and `return` steer a loop

<DrawnAnnotation text="return" label="Leave the closest enclosing function" :geometry="{ label: { x: 0.5132, y: 0.3561 } }"/>

```kotlin
fun printOdd() {
  for (d in 1..10) {
    if (d % 2 == 0) return
    else print("$d, ")
  }
  println("done")
} // 1,
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 6</div>

# Make `if` the value

> Rewrite `abs` as one `if` expression, no `var` and no `return`

```kotlin
fun abs(x: Int): Int {
  var result = x
  if (x < 0) result = -x
  return result
}

check(abs(-3) == 3)
check(abs(4) == 4)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 6</div>

# Replace the `if` chain

> Rewrite `grade` with a subject-less `when`

```kotlin
fun grade(score: Int): String =
  if (score >= 90) "A"
  else if (score >= 80) "B"
  else "F"

check(grade(95) == "A")
check(grade(85) == "B")
check(grade(40) == "F")
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 6</div>

# Add a guard

> Students aged 12 to 64 pay `8`, one guarded branch does it

```kotlin
fun ticketPrice(age: Int, student: Boolean): Int = when (age) {
  in 0..11 -> 5
  in 12..64 -> 12
  else -> 6
}

check(ticketPrice(8, student = false) == 5)
check(ticketPrice(20, student = true) == 8)
check(ticketPrice(20, student = false) == 12)
check(ticketPrice(70, student = true) == 6)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 4 / 6</div>

# Cover every entry

> `next(light)` returns the one after it, `when (light)` without `else`

```kotlin no-compile
enum class Light { RED, AMBER, GREEN }

check(next(Light.RED) == Light.GREEN)
check(next(Light.GREEN) == Light.AMBER)
check(next(Light.AMBER) == Light.RED)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 5 / 6</div>

# Run the body first

> `digitCount(n)`, `do` divides by `10` at least once, so `0` still has one digit

```kotlin no-compile
check(digitCount(0) == 1)
check(digitCount(7) == 1)
check(digitCount(2024) == 4)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 6 / 6</div>

# Stop early

> `sumUntil` adds up a `List<Int>`, a `for` loop that `break`s at `stopAt`

```kotlin no-compile
check(sumUntil([1, 2, 3, 4, 5], stopAt = 4) == 6)
check(sumUntil([4, 6, 8], stopAt = 4) == 0)
```

---

# Nullability is part of the type

<InlineCompilerError text="null" message="Null cannot be a value of a non-null type 'String'.">

```kotlin
val valueOrNull: String = null
```

</InlineCompilerError>

---
magic-move
---

# Nullability is part of the type

<DrawnAnnotation text="?" label="The nullability modifier makes it explicit in the type" :geometry="{ label: { x: 0.4786, y: 0.2713 }, connector: { type: 'quadratic', start: { x: 0.3135, y: 0.2279 }, control: { x: 0.3112, y: 0.2453 }, end: { x: 0.3135, y: 0.2627 } } }"/>

```kotlin
val valueOrNull: String? = null
```

<!--
In Kotlin, the type system distinguishes between references that can hold null (nullable references),
and those that cannot (non-null references).

To allow nulls, we can declare a variable as nullable string, written String?
-->

---

# Safe calls short-circuit on `null`

<DrawnAnnotation text="?."/>
<DrawnAnnotation text="?." :occurrence="2" label="A safe call yields `null` instead of throwing"/>

```kotlin
var nullable: String? = "Hello World!"
println(nullable?.length)
// 12

nullable = null
println(nullable?.length)
// null
```

---

# `!!` trades safety for an exception

<DrawnAnnotation text="!!." label="Forcefully unwrap the nullable type" on="0" :geometry="{ label: { x: 0.2895, y: 0.3722 } }"/>

```kotlin
val nullable: String? = null

println(nullable!!.length)
```

<v-clicks>

<div class="stacktrace" aria-label="Application stack trace">
  <div>Exception in thread "main" <span class="stacktrace-link">java.lang.NullPointerException</span></div>
  <div>&nbsp;&nbsp;&nbsp;&nbsp;at <span class="stacktrace-link">NullableKt.main(Nullable.kt:3)</span></div>
</div>

</v-clicks>

---

# A null check smart-casts the type

<DrawnAnnotation text="nullable.length" label="_smart casting_ allows for non-null access after the check" :geometry="{ label: { x: 0.5238, y: 0.3650 } }"/>

<SmartCast text="nullable.length">

```kotlin
val nullable: String? = null
val length =
  if (nullable != null) nullable.length
  else -1

println(length) // -1
```

</SmartCast>

---
magic-move
---

# A null check smart-casts the type

<DrawnAnnotation text="?:" label="Elvis: when the left side is `null`, fall back to the right side" :geometry="{ label: { x: 0.6562, y: 0.3086 } }"/>

```kotlin
val nullable: String? = null
val length = nullable?.length ?: -1

println(length) // -1
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 3</div>

# Call it safely

> `shout` takes a `String?` and returns a `String?`, `uppercase()` behind a `?.`

```kotlin no-compile
check(shout("hi") == "HI")
check(shout(null) == null)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 3</div>

# Fall back with Elvis

> `lengthOrZero` takes a `String?` and returns an `Int`, `?.` then `?:`

```kotlin no-compile
check(lengthOrZero("Kotlin") == 6)
check(lengthOrZero(null) == 0)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 3</div>

# Drop the `!!`

> Remove both `!!`, the null check already smart-casts `name`

```kotlin
fun firstInitial(name: String?): Char {
  if (name == null || name!!.isEmpty()) return '?'
  return name!!.uppercase().first()
}

check(firstInitial("simon") == 'S')
check(firstInitial(null) == '?')
check(firstInitial("") == '?')
```

---

# Strings interpolate with `$`

<DrawnAnnotation text="$name" label="`$` substitutes a variable" :geometry="{ label: { x: 0.3382, y: 0.3699 }, connector: { type: 'quadratic', start: { x: 0.2464, y: 0.3226 }, control: { x: 0.2441, y: 0.3400 }, end: { x: 0.2464, y: 0.3574 } } }"/>

```kotlin
val name = "John"

println("Hello $name!") // Hello John!
```

---
magic-move
---

# Strings interpolate with `$`

<DrawnAnnotation text="${name.length}" label="`${...}` substitutes any expression" :geometry="{ label: { x: 0.5062, y: 0.3678 } }"/>

```kotlin
val name = "John"

println("$name.length is ${name.length}") // John.length is 4
```

---
magicMove: true
class: multi-dollar-template
---

# Strings interpolate with `$`

<DrawnAnnotation text="$$" label="A multi-dollar prefix changes the interpolation symbol" :geometry="{ label: { x: 0.3142, y: 0.3718 } }"/>

```kotlin
val name = "John"

println($$"$$name owes you 100$") // John owes you 100$
```

---

# Raw strings need no escaping

<DrawnAnnotation text="&quot;&quot;&quot;"/>
<DrawnAnnotation text="&quot;&quot;&quot;" :occurrence="2" label="A raw string is delimited by triple quotes" :geometry="{ label: { x: 0.2454, y: 0.5129 }, connector: { type: 'quadratic', start: { x: 0.0913, y: 0.4501 }, control: { x: 0.0925, y: 0.4720 }, end: { x: 0.0913, y: 0.4994 } } }"/>

```kotlin
val name = "John"

val text = """
    for (c in "$name")
        print(c)
"""
```

---
magic-move
---

# Raw strings need no escaping

<DrawnAnnotation text="trimIndent()" label="Trim the common leading whitespace" :geometry="{ label: { x: 0.2319, y: 0.5123 } }"/>

```kotlin
val name = "John"

val text = """
    for (c in "$name")
        print(c)
""".trimIndent()
```

---
magic-move
---

# Raw strings need no escaping

<DrawnAnnotation text="trimMargin()" label="Trim up to the `|` margin" :geometry="{ label: { x: 0.1974, y: 0.5137 } }"/>

```kotlin
val name = "John"

val text = """
    |for (c in "$name")
    |    print(c)
""".trimMargin()
```

---
magic-move
---

# Const values

> `const val` is a compile-time constant
>
> inlined where it is used

<DrawnAnnotation text="const"/>

```kotlin
const val name = "John"

val text = """
    |for (c in "$name")
    |    print(c)
""".trimMargin()
```

---

# Const functions

> `const val` is a compile-time constant
>
> inlined where it is used

<DrawnAnnotation text="const" occurrence="2"/>
<DrawnAnnotation text="trimMargin" label="-Xintrinsic-const-evaluation since 2.4.0" :geometry="{ label: { x: 0.2516, y: 0.6857 } }"/>

```kotlin
const val name = "John"

const val text = """
    |for (c in "$name")
    |    print(c)
""".trimMargin()
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 3</div>

# Interpolate

> `birthday(name, age)`, `$name` for the variable, `${...}` for the expression

```kotlin no-compile
check(birthday("Ada", 36) == "Ada turns 37 next year")
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 3</div>

# Frame it with a raw string

> `receipt(item, price)`, one raw string with `$item` and `$price`, then `trimIndent()`

```kotlin no-compile
check(receipt("Coffee", 3.5) == "Coffee costs 3.5\n----------\nThank you!")
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 3</div>

# Stop escaping `$`

> Rewrite `payload` with a `$$` prefix so `$id` needs no `${'$'}`

```kotlin
fun payload(name: String): String = "{ \"${'$'}id\": \"$name\" }"

check(payload("kodee") == "{ \"${'$'}id\": \"kodee\" }")
```
