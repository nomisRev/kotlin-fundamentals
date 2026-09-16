---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 2</div>

# Kotlin Syntax

## Properties and functions

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

```kotlin no-compile
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

# Return values are meant to be used

> Experimental, enabled with `-Xreturn-value-checker=full`

<Warning :line="3" text="sum(1, 2)" message="Unused return value of 'sum'.">

```kotlin
fun sum(a: Int, b: Int): Int = a + b

sum(1, 2)
```

</Warning>

---
magic-move
---

# Return values are meant to be used

> Experimental, enabled with `-Xreturn-value-checker=full`

<DrawnAnnotation text="val _ =" label="Discard a result on purpose with `_`" :geometry="{ label: { x: 0.44, y: 0.40 } }"/>

```kotlin
fun sum(a: Int, b: Int): Int = a + b

val _ = sum(1, 2)
```

---
magic-move
---

# Return values are meant to be used

> Experimental, enabled with `-Xreturn-value-checker=full`

<DrawnAnnotation text="greet(&quot;Ada&quot;)" label="`Unit` can be safely ignored" :geometry="{ label: { x: 0.36, y: 0.50 } }"/>

```kotlin
fun sum(a: Int, b: Int): Int = a + b
fun greet(name: String) = println("Hello!")

val _ = sum(1, 2)
greet("Ada")
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
<DrawnAnnotation text="toIntArray()" label="Double copy (double expensive)"/>

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

# Collection literals

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

infix operator fun Int.plus(b: Int): Int = this + b

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
| `+a` | `a.unaryPlus()` |
| `-a` | `a.unaryMinus()` |
| `a < b` | `a.compareTo(b) < 0` |
| `a == b` | `a?.equals(b) ?: (b === null)` |

<style>
.slidev-layout table { font-size: 1.6rem; }
</style>

---

# Declarations are public by default

<DrawnAnnotation text="fun String.shout()" label="No modifier is `public`" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.48, y: 0.243 } }"/>
<DrawnAnnotation text="private fun" label="`private` at top level means this file" :geometry="{ label: { x: 0.36, y: 0.337 } }"/>

```kotlin
fun String.shout(): String = normalized().uppercase() + "!"

private fun String.normalized(): String = trim().replace("  ", " ")

println("  hello   world ".shout())
```
```console
HELLO WORLD!
```

---
magic-move
---

# Declarations are public by default

<DrawnAnnotation text="// Main.kt" label="Another file in the same module" :geometry="{ label: { x: 0.36, y: 0.478 } }"/>
<InlineCompilerError text="normalized()" :line="9" message="Cannot access 'normalized': it is private in file">

```kotlin
// Strings.kt
fun String.shout(): String = normalized().uppercase() + "!"

private fun String.normalized(): String = trim().replace("  ", " ")


// Main.kt
println("  hello   world ".shout())
println("  hello   world ".normalized())
```

</InlineCompilerError>

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 7</div>

# Drop the block

> Rewrite `average` with `=`, no braces and no `return`

```kotlin
fun average(a: Int, b: Int): Int {
  val total = a + b
  return total / 2
}

check(average(4, 8) == 6)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 7</div>

# Add a default

> `by` should be optional and fall back to `1`

```kotlin no-compile
fun increment(x: Int, by: Int): Int = x + by

check(increment(1) == 2)
check(increment(1, by = 5) == 6)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 7</div>

# Name the argument

> `twoTerms(a, c)` calls `sum` without a `b`, so `c` needs its name

```kotlin no-compile
fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

check(twoTerms(1, 1) == 2)
check(twoTerms(1, 2) == 3)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 4 / 7</div>

# Take any number of arguments

> `largest` takes any number of `Int`s, `IntArray` already has `max()`

```kotlin no-compile
check(largest(3, 9, 4) == 9)
check(largest(7) == 7)

val scores = intArrayOf(12, 40, 8)
check(largest(*scores) == 40)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 5 / 7</div>

# Extend `Int`

> `isEven` is an extension on `Int`, `this` is the receiver

```kotlin no-compile
check(4.isEven())
check(!7.isEven())
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 6 / 7</div>

# Make it infix

> `10 percentOf 200` should read like a sentence

```kotlin no-compile
check(10 percentOf 200 == 20)
check(50 percentOf 8 == 4)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 7 / 7</div>

# Implement `*`

> `*` desugars to `times`, the number is the receiver this time

```kotlin no-compile
check(3 * "Ho" == "HoHoHo")
check(0 * "Hi" == "")
```
