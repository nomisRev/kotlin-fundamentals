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

<DrawnAnnotation text="var" label="Mutable variables are declared using `var`" :connect="false" :geometry="{ label: { x: 0.2560, y: 0.3299 } }"/>

```kotlin
var number: Int = 42
var message: String = "Hello World!"
```

---
magic-move
---

# Variables hold mutable state

<DrawnAnnotation text=" =" :connect="false"/>
<DrawnAnnotation text=" =" :occurrence="2" label="The type can be inferred by the compiler" :geometry="{ label: { x: 0.2865, y: 0.3295 }, connector: { type: 'quadratic', start: { x: 0.1934, y: 0.2791 }, control: { x: 0.1914, y: 0.3083 }, end: { x: 0.1934, y: 0.3300 } } }" :connect="false"/>

```kotlin
var number = 42
var message = "Hello World!"
```

---
magic-move
---

# Variables hold mutable state

<DrawnAnnotation text="number += 1" label="Reassign as often as you like" :geometry="{ label: { x: 0.2332, y: 0.4704 }, connector: { type: 'quadratic', start: { x: 0.1354, y: 0.4274 }, control: { x: 0.1228, y: 0.4661 }, end: { x: 0.1355, y: 0.4993 } } }" :connect="false"/>
<DrawnAnnotation text="message = &quot;Hello from Kotlin!&quot;" :connect="false"/>

```kotlin
var number = 42
var message = "Hello World!"

number += 1
message = "Hello from Kotlin!"
```

---

# A value is assigned once

<DrawnAnnotation text="val" label="Read-only values are declared using `val`" :connect="false" :geometry="{ label: { x: 0.2525, y: 0.3347 } }"/>

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

<!--
Variables can be defined as read-only using the val keyword instead of var.
Apart from the keyword change, the syntax remains the same.

However, this simple change has a big impact. The variable cannot be reassigned after the
initialization, and trying to do so will result in a compilation error.

Try to use read-only variables when possible as mutability adds a layer of complexity to reason with code.
-->

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

<DrawnAnnotation text="remove" :connect="false"/>
<DrawnAnnotation text="add" label="A mutable value can still be mutated" :connect="false"/>

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

<DrawnAnnotation text="remove" :connect="false"/>
<DrawnAnnotation text="add" label="A mutable value can still be mutated" :connect="false"/>

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

<DrawnAnnotation text="Unit" :occurrence="2" label="Return `Unit` when there is no output" :geometry="{ label: { x: 0.2702, y: 0.5817 }, connector: { type: 'quadratic', start: { x: 0.1861, y: 0.3296 }, control: { x: 0.2134, y: 0.4381 }, end: { x: 0.1993, y: 0.5481 } } }" :connect="false"/>

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

<DrawnAnnotation text=") {" label="Writing `Unit` explicitly is optional" :connect="false"/>

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

<DrawnAnnotation text="val unit: Unit = " label="There are no procedures, only functions" :connect="false"/>

```kotlin
fun helloWorld() {
  println("Hello World!")
}

val unit: Unit = helloWorld()
```

<!--
Unit expresses that a function does not return any useful value.
It is a type declared in the language, and it has only one value - Unit.

There are no procedures in Kotlin. Every function returns a value.
-->

---

# Return values are meant to be used

> Experimental in Kotlin 2.3.0 with `-Xreturn-value-checker=full`

<DrawnAnnotation text="@MustUseReturnValues" :connect="false"/>
<DrawnAnnotation text="calculator.calculate()" label="Ignoring the result is a compiler warning" color="red" :connect="false"/>

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
magic-move
---

# Return values are meant to be used

> Experimental in Kotlin 2.3.0 with `-Xreturn-value-checker=full`

<DrawnAnnotation text="@IgnorableReturnValue" :connect="false"/>
<DrawnAnnotation text="calculator.add(1)" label="Opting out is explicit, ignoring this result is intentional" :connect="false"/>

```kotlin
@MustUseReturnValues
class Calculator {
  private var total = 0
  fun calculate(): Int = total

  @IgnorableReturnValue
  fun add(value: Int): Calculator = apply { total += value }
}

fun example(calculator: Calculator) {
  calculator.add(1)
  val result = calculator.calculate()
}
```

---

# Expression bodies drop the block

<DrawnAnnotation text="return a + b" label="A block body needs an explicit `return`" :connect="false"/>

```kotlin
fun sum(a: Int, b: Int): Int {
  return a + b
}
```

---
magic-move
---

# Expression bodies drop the block

<DrawnAnnotation text="= a + b" label="A single expression, no braces and no `return`" :connect="false"/>

```kotlin
fun sum(a: Int, b: Int): Int = a + b
```

<!--
Function block body contains multiple statements.
Function expression body contains a single expression.
-->

---

# Default arguments replace overloads

<DrawnAnnotation text="fun sum(a: Int, b: Int): Int = sum(a, b, 0)" label="Explicit overload for an optional `c`" :connect="false"/>

```kotlin
fun sum(a: Int, b: Int, c: Int): Int = a + b + c

fun sum(a: Int, b: Int): Int = sum(a, b, 0)
```

---
magic-move
---

# Default arguments replace overloads

<DrawnAnnotation text="c: Int =" label="Assign an expression after the parameter type" :connect="false"/>

```kotlin
fun sum(a: Int, b: Int, c: Int = 0): Int = a + b + c
```

---
magic-move
---

# Default arguments replace overloads

<DrawnAnnotation text="a + b" label="Defaults can reference the earlier parameters `a` and `b`" :connect="false"/>

```kotlin
fun sum(a: Int, b: Int, c: Int = a + b): Int = a + b + c
```

---
magic-move
---

# Default arguments replace overloads

<DrawnAnnotation text="sum(1, 1)" label="`c` falls back to its default" :connect="false"/>
<DrawnAnnotation text="sum(1, 1, 1)" :connect="false"/>

```kotlin
fun sum(a: Int, b: Int, c: Int = 0): Int = a + b + c

val two = sum(1, 1)
val three = sum(1, 1, 1)
```

<!--
In Kotlin you can avoid overloads assigning default arguments to any number of parameters.
A default argument is defined assigning an expression after the parameter type.
The function will use the default argument when the caller does not provide the parameter with one.

Default arguments can be used to add functionality to APIs keeping retro-compatibility as it does not break client code.
-->

---

# Defaults in the middle need names

<DrawnAnnotation text="b: Int = 0" :connect="false"/>
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

<DrawnAnnotation text="c = " label="Name the argument to help the compiler" :connect="false"/>

```kotlin
fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

val two = sum(1, c = 1)
```

---

# Named arguments clarify the call

<DrawnAnnotation text="length: Int = message.length" label="Defaults can depend on earlier parameters" :connect="false"/>

```kotlin
fun print(
  message: String,
  offset: Int = 0,
  length: Int = message.length,
) { /*...*/ }
```

---
magic-move
---

# Named arguments clarify the call

<DrawnAnnotation text="offset = 6, length = 6" label="Name the arguments to say what each value means" :connect="false"/>

```kotlin
fun print(
  message: String,
  offset: Int = 0,
  length: Int = message.length,
) { /*...*/ }

print("Hello World!", offset = 6, length = 6) // World!
```

---
magic-move
---

# Named arguments clarify the call

<DrawnAnnotation text="length = 5" label="Skip a defaulted parameter entirely" :connect="false"/>

```kotlin
fun print(
  message: String,
  offset: Int = 0,
  length: Int = message.length,
) { /*...*/ }

print("Hello World!", offset = 6, length = 6) // World!
print("Hello World!", length = 5)             // Hello
```

---
magic-move
---

# Named arguments clarify the call

<DrawnAnnotation text="length = 6, message" label="Named arguments can come in any order" :connect="false"/>

```kotlin
fun print(
  message: String,
  offset: Int = 0,
  length: Int = message.length,
) { /*...*/ }

print("Hello World!", offset = 6, length = 6) // World!
print("Hello World!", length = 5)             // Hello
print(length = 6, message = "Hello World!", offset = 6)
```

<!--
When calling a function, you can name one or more of its arguments.
This may be helpful when a function has many arguments, and it's difficult to associate a value with an argument,
especially if it's a boolean or null value.

When you use named arguments in a function call, you can freely change the order they are listed in, and if you want
to use their default values you can just leave them out altogether.
i.e: read("Hello World!", offset = 6, 6) - Mixing named and positioned arguments is not allowed
-->

---

# Varargs take any number of arguments

<DrawnAnnotation text="vararg" label="Any number of arguments of the same type" :geometry="{ label: { x: 0.2891, y: 0.4462 } }" :connect="false"/>

```kotlin
fun sum(vararg numbers: Int): Int = numbers.sum()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)
```

---
magic-move
---

# Varargs take any number of arguments

<DrawnAnnotation text="*array" label="The spread operator copies the elements into a new array (expensive)" :geometry="{ label: { x: 0.5147, y: 0.5676 } }" :connect="false"/>

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

# Varargs take any number of arguments

<DrawnAnnotation text="Iterable<Int>" :connect="false"/>
<DrawnAnnotation text="sum(list)" label="The explicit overload takes any `Iterable`" :geometry="{ label: { x: 0.3722, y: 0.6131 } }" :connect="false"/>

```kotlin
fun sum(vararg numbers: Int): Int = numbers.sum()
fun sum(numbers: Iterable<Int>): Int = numbers.sum()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)

val list = listOf(1, 2, 3, 4, 5, 6)
val spread = sum(list)
```

---
magic-move
---

# Varargs take any number of arguments

<DrawnAnnotation text="[1, 2, 3]" :connect="false"/>
<DrawnAnnotation text="[1, 2, 3, 4, 5, 6]" label="Collection literals, experimental in Kotlin 2.4" :connect="false"/>

```kotlin
fun sum(numbers: Iterable<Int>): Int = numbers.sum()

val six = sum([1, 2, 3])
val twentyOne = sum([1, 2, 3, 4, 5, 6])

val list = listOf(1, 2, 3, 4, 5, 6)
val spread = sum(list)
```

<!--
Simplifies the caller side by avoiding collections at all

Visible type for caller is Int. Inside the function body it is Array<Int>
Only one varargs is allowed.
Must be last parameter. Compiler can't disambiguate when the varargs end, and the other parameters start.
-->

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
<DrawnAnnotation text="): Int =" label="A value is expected, so `if` is used as an expression" :geometry="{ label: { x: 0.5659, y: 0.3489 } }" :connect="false"/>

```kotlin
fun max(a: Int, b: Int): Int =
  if (a > b) a
```

</InlineCompilerError>

---
magic-move
---

# `if` is an expression

<DrawnAnnotation text="else b" label="Both branches supply the value" :geometry="{ label: { x: 0.4450, y: 0.3079 } }" :connect="false"/>

```kotlin
fun max(a: Int, b: Int): Int =
  if (a > b) a else b
```

---
magic-move
---

# `if` is an expression

<DrawnAnnotation text=") {" :connect="false"/>
<DrawnAnnotation text="second" :occurrence="3" label="The last expression is the value of the block" :geometry="{ label: { x: 0.3082, y: 0.6127 } }" :connect="false"/>

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

# `when` replaces `if` chains

```kotlin
fun max(a: Int, b: Int): Int =
  if (a > b) a else b
```

---
magic-move
---

# `when` replaces `if` chains

<DrawnAnnotation text="when" label="A more powerful switch-case" :geometry="{ label: { x: 0.5378, y: 0.2719 } }" :connect="false"/>

```kotlin
fun max(a: Int, b: Int): Int = when {
  a > b -> a
  else -> b
}
```

---

# `when` matches on a subject

<DrawnAnnotation text="condition" :occurrence="2" :connect="false"/>

```kotlin
fun print(condition: Boolean) = when (condition) {
  true -> println("Encountered $condition")
  false -> println("Encountered $condition")
}
```

---
magic-move
---

# `when` matches on a subject

<InlineCompilerError
    text="when (x)"
    message="'when' expression must be exhaustive. Add an 'else' branch"
    style="--inline-compiler-error-message-size: 1.57rem"
>

```kotlin
fun describe(x: Int) = when (x) {
  1 -> "x == 1"
  2 -> "x == 2"
}
```

</InlineCompilerError>

---
magic-move
---

# `when` matches on a subject

<DrawnAnnotation text="else ->" label="`else` covers every other value" :connect="false" :geometry="{ label: { x: 0.2288, y: 0.4725 } }"/>

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

<DrawnAnnotation text="when (x)" label="A `when` statement does not need to be exhaustive" :connect="false"/>

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

<DrawnAnnotation text="3, 4" label="Branches can be combined" :connect="false" :geometry="{ label: { x: 0.2317, y: 0.5563 } }"/>

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

<DrawnAnnotation text="-> {" label="A branch can be a block" :connect="false" :geometry="{ label: { x: 0.3320, y: 0.5713 } }"/>

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

<DrawnAnnotation text="in 3..10" label="Ranges match with `in`" :connect="false" :geometry="{ label: { x: 0.2224, y: 0.4690 } }"/>

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

<DrawnAnnotation text="specialValue()" label="Arbitrary expressions can be matched" :connect="false" :geometry="{ label: { x: 0.2914, y: 0.4804 } }"/>

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

<DrawnAnnotation text="val x = calculate()" label="Bind the subject to a value while matching" :connect="false" :geometry="{ label: { x: 0.2786, y: 0.4657 } }"/>

```kotlin
when (val x = calculate()) {
  1 -> println("x == 1")
  2 -> println("x == 2")
  specialValue() -> println("x matches the special value")
}
```

---

# Guards add a condition to a match

> Stable since Kotlin 2.2.0

<DrawnAnnotation text="if x % 2 == 0" on="0" label="The guard runs only when the condition match succeeds" :geometry="{ label: { x: 0.4884, y: 0.6008, width: 0.3308 }, connector: { type: 'quadratic', start: { x: 0.4826, y: 0.6602 }, control: { x: 0.5354, y: 0.6690 }, end: { x: 0.5780, y: 0.6907 } } }" :connect="false"/>
<DrawnAnnotation text="in 1..10" occurrence="3" label="We _should_ to specify counter condition" on="1" :connect="false" :geometry="{ label: { x: 0.2490, y: 0.5914 } }"/>

```kotlin
fun describe(x: Int) = when (x) {
    in 1..10 if x % 2 == 0 -> "even, in 1..10"
    in 1..10 -> "odd, in 1..10"
    else -> "outside 1..10"
}
```

---

# Guards add a condition to a match

> Stable since Kotlin 2.2.0

```kotlin
fun describe(x: Int) = when (x) {
    in 0..10 step 2 -> "even 0..10 step 2"
    in 1..9 step 2 -> "odd, 1..9 step 2"
    else -> "outside 1..10"
}
```

---

# Exhaustiveness follows the data flow

> Stable since Kotlin 2.3.0

<DrawnAnnotation text="if (role == UserRole.ADMIN) return 99" :connect="false"/>
<DrawnAnnotation text="UserRole.GUEST -> 1" label="No `else` needed, the early return already handled `ADMIN`" :geometry="{ label: { x: 0.5861, y: 0.6086, width: 0.3150 }, connector: { type: 'quadratic', start: { x: 0.0941, y: 0.6858 }, control: { x: 0.3763, y: 0.7027 }, end: { x: 0.4286, y: 0.6317 } } }" :connect="false"/>

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

<DrawnAnnotation text="1..<10" label="Open-ended range" :geometry="{ label: { x: 0.2289, y: 0.4305 } }" :connect="false"/>
<DrawnAnnotation text="6 downTo 0 step 2" label="Ranges step in either direction with steps" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.4706, y: 0.4985 } }" :connect="false"/>

```kotlin
for (item in ["One", "Two", "Three"]) print("$item, ") // One, Two, Three,
for (item in 1..10) print("$item, ")     // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
for (item in 1..<10) print("$item, ")    // 1, 2, 3, 4, 5, 6, 7, 8, 9,
for (item in 6 downTo 0 step 2) print("$item, ") // 6, 4, 2, 0,
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

<DrawnAnnotation text="while (y != null)" label="The condition is checked after the first run" :connect="false" :geometry="{ label: { x: 0.3193, y: 0.5059 } }"/>

```kotlin no-compile
fun retrieveData(): Int? = null

do {
  println("Runs!")
  val y = retrieveData()
} while (y != null) // Runs!
```

---

# `continue`, `break` and `return` steer a loop

<DrawnAnnotation text="continue" label="Skip to the next iteration" :geometry="{ label: { x: 0.4477, y: 0.3704 } }" :connect="false"/>

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

<DrawnAnnotation text="break" label="Stop the loop" :geometry="{ label: { x: 0.4064, y: 0.3448 } }" :connect="false"/>

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

<DrawnAnnotation text="return" label="Leave the closest enclosing function" :geometry="{ label: { x: 0.5132, y: 0.3561 } }" :connect="false"/>

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

<DrawnAnnotation text="?" label="The nullability modifier makes it explicit in the type" :geometry="{ label: { x: 0.4786, y: 0.2713 }, connector: { type: 'quadratic', start: { x: 0.3135, y: 0.2279 }, control: { x: 0.3112, y: 0.2453 }, end: { x: 0.3135, y: 0.2627 } } }" :connect="false"/>

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

<DrawnAnnotation text="?." :connect="false"/>
<DrawnAnnotation text="?." :occurrence="2" label="A safe call yields `null` instead of throwing" :geometry="{ label: { x: 0.4178, y: 0.5862 }, connector: { type: 'quadratic', start: { x: 0.2407, y: 0.4646 }, control: { x: 0.2325, y: 0.5255 }, end: { x: 0.2407, y: 0.5863 } } }" :connect="false"/>

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

<DrawnAnnotation text="!!." label="Forcefully unwrap the nullable type" :geometry="{ label: { x: 0.5238, y: 0.2954 } }" :connect="false"/>

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

<DrawnAnnotation text="nullable.length" label="Non-null access after the check" :geometry="{ label: { x: 0.5238, y: 0.3650 } }" :connect="false"/>

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

<DrawnAnnotation text="?:" label="Elvis: when the left side is `null`, fall back to the right side" :geometry="{ label: { x: 0.6562, y: 0.3086 } }" :connect="false"/>

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

<DrawnAnnotation text="$name" label="`$` substitutes a variable" :geometry="{ label: { x: 0.3382, y: 0.3699 }, connector: { type: 'quadratic', start: { x: 0.2464, y: 0.3226 }, control: { x: 0.2441, y: 0.3400 }, end: { x: 0.2464, y: 0.3574 } } }" :connect="false"/>

```kotlin
val name = "John"

println("Hello $name!") // Hello John!
```

---
magic-move
---

# Strings interpolate with `$`

<DrawnAnnotation text="${name.length}" label="`${...}` substitutes any expression" :geometry="{ label: { x: 0.5062, y: 0.3678 } }" :connect="false"/>

```kotlin
val name = "John"

println("$name.length is ${name.length}") // John.length is 4
```

---
magicMove: true
class: multi-dollar-template
---

# Strings interpolate with `$`

<DrawnAnnotation text="$$" label="A multi-dollar prefix changes the interpolation symbol" :geometry="{ label: { x: 0.3142, y: 0.3718 } }" :connect="false"/>

```kotlin
val name = "John"

println($$"$$name owes you 100$") // John owes you 100$
```

<!--
String literals may contain pieces of code that are evaluated and whose results are concatenated into the string.
-->

---

# Raw strings need no escaping

<DrawnAnnotation text="&quot;&quot;&quot;" :connect="false"/>
<DrawnAnnotation text="&quot;&quot;&quot;" :occurrence="2" label="A raw string is delimited by triple quotes" :geometry="{ label: { x: 0.2454, y: 0.5129 }, connector: { type: 'quadratic', start: { x: 0.0913, y: 0.4501 }, control: { x: 0.0925, y: 0.4720 }, end: { x: 0.0913, y: 0.4994 } } }" :connect="false"/>

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

<DrawnAnnotation text="trimIndent()" label="Trim the common leading whitespace" :geometry="{ label: { x: 0.2319, y: 0.5123 } }" :connect="false"/>

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

<DrawnAnnotation text="trimMargin()" label="Trim up to the `|` margin" :geometry="{ label: { x: 0.1974, y: 0.5137 } }" :connect="false"/>

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

# Raw strings need no escaping

> `const val` is a compile-time constant, inlined where it is used

<DrawnAnnotation text="const" :connect="false"/>

```kotlin
const val name = "John"

val text = """
    |for (c in "$name")
    |    print(c)
""".trimMargin()
```

---

# Raw strings need no escaping

> `const val` is a compile-time constant, inlined where it is used

<DrawnAnnotation text="const" occurrence="2" :connect="false"/>
<DrawnAnnotation text="trimMargin" label="-Xintrinsic-const-evaluation since 2.4.0" :connect="false" :geometry="{ label: { x: 0.2516, y: 0.6857 } }"/>

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
