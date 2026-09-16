---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 2</div>

# Kotlin Syntax

## Control flow

---

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
