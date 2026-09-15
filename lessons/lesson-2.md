---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 2</div>

# Kotlin Syntax

## Values, functions and control flow

---

# Variables hold mutable state

<DrawnAnnotation text="var" label="Mutable variables are declared using `var`" :geometry="{ label: { x: 0.2560, y: 0.3299 } }"/>

```kotlin
var number: Int = 42
var message: String = "Hello World!"
```

---
magic-move
---

# Variables hold mutable state

<DrawnAnnotation text=" ="/>
<DrawnAnnotation text=" =" :occurrence="2" label="The type can be inferred by the compiler" :geometry="{ label: { x: 0.2865, y: 0.3295 }, connector: { type: 'quadratic', start: { x: 0.1934, y: 0.2791 }, control: { x: 0.1914, y: 0.3083 }, end: { x: 0.1934, y: 0.3300 } } }"/>

```kotlin
var number = 42
var message = "Hello World!"
```

---
magic-move
---

# Variables hold mutable state

<DrawnAnnotation text="number += 1" label="Reassign as often as you like" :geometry="{ label: { x: 0.2332, y: 0.4704 }, connector: { type: 'quadratic', start: { x: 0.1354, y: 0.4274 }, control: { x: 0.1228, y: 0.4661 }, end: { x: 0.1355, y: 0.4993 } } }"/>
<DrawnAnnotation text="message = &quot;Hello from Kotlin!&quot;"/>

```kotlin
var number = 42
var message = "Hello World!"

number += 1
message = "Hello from Kotlin!"
```

---

# A value is assigned once

<DrawnAnnotation text="val" label="Read-only values are declared using `val`" :geometry="{ label: { x: 0.2525, y: 0.3347 } }"/>

```kotlin
val number = 42
val message = "Hello World!"
```

---
magic-move
---

# A value is assigned once

<InlineCompilerError :line="4" text="number" message="'val' cannot be reassigned.">
<InlineCompilerError :line="5" text="message" message="'val' cannot be reassigned.">

```kotlin
val number = 42
val message = "Hello World!"

number += 1
message = "Hello from Kotlin!"
```

</InlineCompilerError>
</InlineCompilerError>

---

# Kotlin Property

<DrawnAnnotation text="field" label="Refers to the actual backing field. Return mandatory." on="0"  :geometry="{ label: { x: 0.5062, y: 0.4676 } }"/>
<DrawnAnnotation text="field = " label="In the setter the backing field can be assigned" on="1"  :geometry="{ label: { x: 0.3832, y: 0.6672 } }"/>

> `var` property has `get` and `set` functions

```kotlin
var number: Int = 42
  get() {
      return field
  }
  set(value) {
      field = value
  }
```

---

# Kotlin Property

<DrawnAnnotation text="println" label="Allows customisation without changing API" :geometry="{ label: { x: 0.5062, y: 0.4676 } }"/>

> `val` property has `get` 

```kotlin
val number: Int = 42
  get() {
    println("get number")
    return field
  }
```

---

# Kotlin Property

<DrawnAnnotation text="return 42" label="No backing field"  :geometry="{ label: { x: 0.3009, y: 0.4880 } }"/>

> `val` property has `get`

```kotlin
val number: Int
  get() {
    println("get number")
    return 42
  }
```

---

# Read-only is not immutable

> `val` is an immutable reference

<InlineCompilerError :line="2" text="mutableList" message="'val' cannot be reassigned.">

```kotlin
val mutableList: MutableList<String> = mutableListOf("Hello World!")
mutableList = mutableListOf("Hello Student!")
```

</InlineCompilerError>

---
magic-move
---

# Read-only is not immutable

> `val` is an immutable reference

<DrawnAnnotation text="remove"/>
<DrawnAnnotation text="add" label="A mutable value can still be mutated"/>

```kotlin
val mutableList: MutableList<String> = mutableListOf("Hello World!")
mutableList.remove("Hello World!")
mutableList.add("Hello Student!")
```

---
magic-move
---

# Read-only is not immutable

> `val` is an immutable reference

<InlineCompilerError text="remove" message="Unresolved reference 'remove'.">
<InlineCompilerError text="add" message="Unresolved reference 'add'.">

```kotlin
val list: List<String> = listOf("Hello World!")
list.remove("Hello World!")
list.add("Hello Student!")
```

</InlineCompilerError>
</InlineCompilerError>

<!--
It is important to note that `val` implies read-only.
If a mutable structure is assigned, the variable cannot be mutated (reassigned), but the actual structure can.

In the example, we have a mutable list which contains Hello World.
If we want to replace the message to greet the student instead, the variable cannot be reassigned.
However, as it is a mutable list, we can modify the messages.
-->

---

# Parameters are values, not variables

> Object references are passed by value

<InlineCompilerError :line="2" text="mutableList" message="'val' cannot be reassigned.">

```kotlin
fun changeGreeting(mutableList: MutableList<String>) {
  mutableList = mutableListOf("Hello Student!")
}
```

</InlineCompilerError>

---
magic-move
---

# Parameters are values, not variables

> Object references are passed by value

<DrawnAnnotation text="remove"/>
<DrawnAnnotation text="add" label="A mutable value can still be mutated"/>

```kotlin
fun changeGreeting(mutableList: MutableList<String>) {
  mutableList.remove("Hello World!")
  mutableList.add("Hello Student!")
}
```

---
magic-move
---

# Parameters are values, not variables

> Object references are passed by value

<InlineCompilerError text="remove" message="Unresolved reference 'remove'.">
<InlineCompilerError text="add" message="Unresolved reference 'add'.">

```kotlin
fun changeGreeting(list: List<String>) {
  list.remove("Hello World!")
  list.add("Hello Student!")
}
```

</InlineCompilerError>
</InlineCompilerError>

---

# Every function returns a value

<DrawnAnnotation text="Unit" :occurrence="2" label="Return `Unit` when there is no output" :geometry="{ label: { x: 0.3841, y: 0.3322 }, connector: { type: 'quadratic', start: { x: 0.1861, y: 0.3296 }, control: { x: 0.2134, y: 0.4381 }, end: { x: 0.1993, y: 0.5481 } } }"/>

```kotlin
fun helloWorld(): Unit {
  println("Hello World!")
  return Unit
}

helloWorld()
```

---
magic-move
---

# Every function returns a value

<DrawnAnnotation text=") {" label="Writing `Unit` explicitly is optional"/>

```kotlin
fun helloWorld() {
  println("Hello World!")
}

helloWorld()
```

---
magic-move
---

# Every function returns a value

<DrawnAnnotation text=") {" label="Writing `Unit` explicitly is optional"/>

```kotlin
fun helloWorld() {
}

helloWorld()
```

---
magic-move
---

# Every function returns a value

<DrawnAnnotation text="val unit: Unit = " label="Unlike Java’s void, Unit has a single value" :geometry="{ label: { x: 0.3061, y: 0.4654 } }"/>

```kotlin
fun helloWorld() {
  println("Hello World!")
}

val unit: Unit = helloWorld()
```

---

# Expression bodies drop the block

<DrawnAnnotation text=" {" />
<DrawnAnnotation text="}" label="Function with _block body_"  :geometry="{ label: { x: 0.2760, y: 0.3319 } }"/>

```kotlin
fun sum(a: Int, b: Int): Int {
}
```

---
magic-move
---

# Expression bodies drop the block

<DrawnAnnotation text="return a + b" label="A block body needs an explicit `return`" :geometry="{ label: { x: 0.3677, y: 0.3769 } }"/>

```kotlin
fun sum(a: Int, b: Int): Int {
  return a + b
}
```

---
magic-move
---

# Expression bodies drop the block

<DrawnAnnotation text=" = ..." label="Function with an _expression body_"/>

```kotlin no-compile
fun sum(a: Int, b: Int): Int = ...
```

---
magic-move
---

# Expression bodies drop the block

<DrawnAnnotation text="= a + b" label="_assign_ a single expression to the function\nno braces and no `return`"/>

```kotlin
fun sum(a: Int, b: Int): Int = a + b
```

---
magic-move
---

# Default arguments replace overloads

<DrawnAnnotation text="= sum(a, b, 0)" label="Explicit overload for an optional `c`" :geometry="{ label: { x: 0.4690, y: 0.3677 } }"/>

```kotlin
fun sum(a: Int, b: Int): Int = sum(a, b, 0)

fun sum(a: Int, b: Int, c: Int): Int = a + b + c
```

---
magic-move
---

# Default arguments replace overloads

<DrawnAnnotation text="c: Int =" label="Assign an expression after the parameter type"/>

```kotlin
fun sum(a: Int, b: Int, c: Int = 0): Int = a + b + c
```

---
magic-move
---

# Default arguments replace overloads

<DrawnAnnotation text="a + b" label="Defaults can reference the earlier parameters `a` and `b`"/>

```kotlin
fun sum(a: Int, b: Int, c: Int = a + b): Int = a + b + c
```

---
magic-move
---

# Default arguments replace overloads

<DrawnAnnotation text="sum(1, 1)" label="`c` falls back to its default"/>

```kotlin
fun sum(a: Int, b: Int, c: Int = 0): Int = a + b + c

val two = sum(1, 1)
```

---

# Defaults in the middle need names

<DrawnAnnotation text="b: Int = 0"/>
<InlineCompilerError text="sum(1, 1)" message="No value passed for parameter 'c'.">

```kotlin
fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

val two = sum(1, 1)
```

</InlineCompilerError>

---
magic-move
---

# Defaults in the middle need names

<DrawnAnnotation text="c = " label="Name the argument to help the compiler"/>

```kotlin
fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

val two = sum(1, c = 1)
```

---

# Varargs take any number of arguments

<DrawnAnnotation text="vararg" label="Any number of arguments of the same type" :geometry="{ label: { x: 0.2891, y: 0.4462 } }"/>

```kotlin
fun sum(vararg numbers: Int): Int = numbers.sum()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)
```

---
magic-move
---

# Spread operator

<DrawnAnnotation text="*array" label="The spread operator copies the elements into a new array (expensive)" :geometry="{ label: { x: 0.5147, y: 0.5676 } }"/>

```kotlin
fun sum(vararg numbers: Int): Int = numbers.sum()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)

val array = intArrayOf(1, 2, 3, 4, 5, 6)
val spread = sum(*array)
```

---
magic-move
---

# Spread operator

<DrawnAnnotation text="*" occurrence="2"/>
<DrawnAnnotation text="toIntArray()" label="Double copy"/>

```kotlin
fun sum(vararg numbers: Int): Int = numbers.sum()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)

val array = intArrayOf(1, 2, 3, 4, 5, 6)
val spread = sum(*array)

val list = listOf(1, 2, 3, 4, 5, 6)
sum(*list.toIntArray())
```

---
magic-move
---

# Iterable overload

<DrawnAnnotation text="Iterable<Int>"/>
<DrawnAnnotation text="sum(list)" label="The explicit overload takes any `Iterable`" :geometry="{ label: { x: 0.2603, y: 0.7454 } }"/>

```kotlin
fun sum(vararg numbers: Int): Int = numbers.sum()
fun sum(numbers: Iterable<Int>): Int = numbers.sum()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)

val array = intArrayOf(1, 2, 3, 4, 5, 6)
val spread = sum(*array)

val list = listOf(1, 2, 3, 4, 5, 6)
sum(list)
```

---
magic-move
---

# Colletion literals

<DrawnAnnotation text="[1, 2, 3]"/>
<DrawnAnnotation text="[1, 2, 3, 4, 5, 6]"/>
<DrawnAnnotation text="[1, 2, 3, 4, 5, 6]" occurrence="2" label="Collection literals, experimental in Kotlin 2.4 (-Xcollection-literals)" :geometry="{ label: { x: 0.4081, y: 0.7548 } }"/>

```kotlin
fun sum(vararg numbers: Int): Int = numbers.sum()
fun sum(numbers: Iterable<Int>): Int = numbers.sum()

val six = sum([1, 2, 3])
val twentyOne = sum([1, 2, 3, 4, 5, 6])

val array = intArrayOf(1, 2, 3, 4, 5, 6)
val spread = sum(*array)

val list = [1, 2, 3, 4, 5, 6]
sum(list)
```

---
magic-move
---

# Prefer collection (literals)

<DrawnAnnotation text="asIterable()" label="Copy-free, single `Iterable` allocation wrapping `Iterator`" />

```kotlin
fun sum(numbers: Iterable<Int>): Int = numbers.sum()

val six = sum([1, 2, 3])
val twentyOne = sum([1, 2, 3, 4, 5, 6])

val array = intArrayOf(1, 2, 3, 4, 5, 6)
val spread = sum(array.asIterable())

val list = [1, 2, 3, 4, 5, 6]
sum(list)
```

---

# Extension functions add a receiver

<DrawnAnnotation text="Int." label="The receiver type" />
<DrawnAnnotation text="this" label="The receiver value" at="1" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.4527, y: 0.3820 } }"/>

```kotlin
fun sum(a: Int, b: Int): Int = a + b

fun Int.plus(b: Int): Int = this + b
```

---
magic-move
---

# Extension functions add a receiver

```kotlin
fun sum(a: Int, b: Int): Int = a + b

fun Int.plus(b: Int): Int = this + b

sum(1, 2)
1.plus(2)
```

---
magic-move
---

# Infix operator

<DrawnAnnotation text="infix" />
<DrawnAnnotation text="1 plus 2" label="Allows function to be used in _infix position_" />

```kotlin
fun sum(a: Int, b: Int): Int = a + b

infix fun Int.plus(b: Int): Int = this + b

sum(1, 2)
1 plus 2
```

---
magic-move
---

# `+` is a function call

<DrawnAnnotation text="operator" />
<DrawnAnnotation text="1 + 2" label="Plus operator allows for `+` syntax" :geometry="{ label: { x: 0.45, y: 0.48 } }" />

```kotlin
fun sum(a: Int, b: Int): Int = a + b

operator fun Int.plus(b: Int): Int = this + b

sum(1, 2)
1 plus 2
1 + 2
```

---

# `*` needs the `operator` modifier

<InlineCompilerError :line="3" text="*" message="'operator' modifier is required on 'fun String.times(n: Int): String'" style="--inline-compiler-error-message-size: 1.35rem">

```kotlin
fun String.times(n: Int): String = repeat(n)

"Hi" * 3
```

</InlineCompilerError>

---
magic-move
---

# `*` needs the `operator` modifier

<DrawnAnnotation text="operator" label="Marks `times` as the implementation of `*`" :geometry="{ label: { x: 0.72, y: 0.47 } }" />
<DrawnAnnotation text="times" label="The function name picks the symbol" :geometry="{ label: { x: 0.30, y: 0.47 } }" />

```kotlin
operator fun String.times(n: Int): String = repeat(n)

"Hi" * 3
```
```console
HiHiHi
```

---

# `<` is a function call

<DrawnAnnotation text="compareTo" label="One `compareTo` gives `<`, `<=`, `>` and `>=`" :geometry="{ label: { x: 0.62, y: 0.245 } }" />

```kotlin
"Ada" < "Linus"
"Ada".compareTo("Linus") < 0
```
```console
true
true
```

---

# Operators are named functions

| Expression | Call |
| --- | --- |
| `a + b` | `a.plus(b)` |
| `a - b` | `a.minus(b)` |
| `a * b` | `a.times(b)` |
| `a / b` | `a.div(b)` |
| `a % b` | `a.rem(b)` |
| `-a` | `a.unaryMinus()` |
| `a < b` | `a.compareTo(b) < 0` |
| `a == b` | `a?.equals(b) ?: (b === null)` |

<style>
.slidev-layout table { font-size: 1.6rem; }
</style>

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise</div>

# Functions

> Build the result with a local `var` inside a loop, no `String.repeat`

```kotlin
fun repeatWord(
  word: String,
  times: Int = 3,
  separator: String = ", ",
): String = TODO()

fun main() {
  check(repeatWord("Hi") == "Hi, Hi, Hi")
  check(repeatWord("Hi", separator = " - ") == "Hi - Hi - Hi")
  check(repeatWord(times = 2, separator = "", word = "Go") == "GoGo")
}
```

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
```java
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

<div class="lesson-number">Exercise</div>

# Control flow

> `grade`: 90+ "A", 80..89 "B", else "F" — with `when`
>
> `sumUntil`: sum up to (not including) `stopAt` — loop + `break`

```kotlin
fun grade(score: Int): String = TODO()

fun sumUntil(numbers: List<Int>, stopAt: Int): Int = TODO()

fun main() {
  check(grade(95) == "A")
  check(grade(40) == "F")

  check(sumUntil(listOf(1, 2, 3, 4, 5), stopAt = 4) == 6)
  check(sumUntil(listOf(4, 6, 8), stopAt = 4) == 0)
}
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

<div class="lesson-number">Exercise</div>

# Null safety

> `null` or empty → `'?'`, otherwise the uppercase first letter — no `!!`

```kotlin
fun firstInitial(name: String?): Char = TODO()

fun main() {
  check(firstInitial("simon") == 'S')
  check(firstInitial(null) == '?')
  check(firstInitial("") == '?')
}
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

<div class="lesson-number">Exercise</div>

# Strings

> `$item` interpolation for the header, `trimIndent()` for the frame

```kotlin
fun receipt(item: String, price: Double): String = TODO()

fun main() {
  check(receipt("Coffee", 3.5) == "Coffee costs 3.5\n----------\nThank you!")
}
```
