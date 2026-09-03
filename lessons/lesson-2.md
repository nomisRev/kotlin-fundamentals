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

<DrawnAnnotation text="var" label="Mutable variables are declared using `var`" />

```kotlin
var number: Int = 42
var message: String = "Hello World!"
```

---
magic-move
---

# Variables hold mutable state

<DrawnAnnotation text=" =" />
<DrawnAnnotation text=" =" :occurrence="2" label="The type can be inferred by the compiler" />

```kotlin
var number = 42
var message = "Hello World!"
```

---
magic-move
---

# Variables hold mutable state

<DrawnAnnotation text="number += 1" label="Reassign as often as you like" />
<DrawnAnnotation text="message = &quot;Hello from Kotlin!&quot;" />

```kotlin
var number = 42
var message = "Hello World!"

number += 1
message = "Hello from Kotlin!"
```

---

# A value is assigned once

<DrawnAnnotation text="val" label="Read-only values are declared using `val`" />

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

<DrawnAnnotation text="remove" />
<DrawnAnnotation text="add" label="A mutable value can still be mutated" />

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

<DrawnAnnotation text="remove" />
<DrawnAnnotation text="add" label="A mutable value can still be mutated" />

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

<DrawnAnnotation text="Unit" :occurrence="2" label="Return `Unit` when there is no output" placement="down" />

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

<DrawnAnnotation text=") {" label="Writing `Unit` explicitly is optional" placement="down" />

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

<DrawnAnnotation text="val unit: Unit = " label="There are no procedures, only functions" />

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

<DrawnAnnotation text="@MustUseReturnValues" />
<DrawnAnnotation text="calculator.calculate()" label="Ignoring the result is a compiler warning" color="red" />

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

<DrawnAnnotation text="@IgnorableReturnValue" />
<DrawnAnnotation text="calculator.add(1)" label="Opting out is explicit, ignoring this result is intentional" placement="down" />

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

<DrawnAnnotation text="return a + b" label="A block body needs an explicit `return`" />

```kotlin
fun sum(a: Int, b: Int): Int {
  return a + b
}
```

---
magic-move
---

# Expression bodies drop the block

<DrawnAnnotation text="= a + b" label="A single expression, no braces and no `return`" />

```kotlin
fun sum(a: Int, b: Int): Int = a + b
```

<!--
Function block body contains multiple statements.
Function expression body contains a single expression.
-->

---

# Default arguments replace overloads

<DrawnAnnotation text="fun sum(a: Int, b: Int): Int = sum(a, b, 0)" label="Explicit overload for an optional `c`" />

```kotlin
fun sum(a: Int, b: Int, c: Int): Int = a + b + c

fun sum(a: Int, b: Int): Int = sum(a, b, 0)
```

---
magic-move
---

# Default arguments replace overloads

<DrawnAnnotation text="c: Int =" label="Assign an expression after the parameter type" />

```kotlin
fun sum(a: Int, b: Int, c: Int = 0): Int = a + b + c
```

---
magic-move
---

# Default arguments replace overloads

<DrawnAnnotation text="a + b" label="Defaults can reference the earlier parameters `a` and `b`" />

```kotlin
fun sum(a: Int, b: Int, c: Int = a + b): Int = a + b + c
```

---
magic-move
---

# Default arguments replace overloads

<DrawnAnnotation text="sum(1, 1)" label="`c` falls back to its default" />
<DrawnAnnotation text="sum(1, 1, 1)" />

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

<DrawnAnnotation text="b: Int = 0" />
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

<DrawnAnnotation text="c = " label="Name the argument to help the compiler" />

```kotlin
fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

val two = sum(1, c = 1)
```

---

# Named arguments clarify the call

<DrawnAnnotation text="length: Int = message.length" label="Defaults can depend on earlier parameters" />

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

<DrawnAnnotation text="offset = 6, length = 6" label="Name the arguments to say what each value means" />

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

<DrawnAnnotation text="length = 5" label="Skip a defaulted parameter entirely" />

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

<DrawnAnnotation text="length = 6, message" label="Named arguments can come in any order" />

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

<DrawnAnnotation text="vararg" label="Any number of arguments of the same type" />

```kotlin
fun sum(vararg numbers: Int): Int = numbers.sum()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)
```

---
magic-move
---

# Varargs take any number of arguments

<DrawnAnnotation text="*array" label="The spread operator copies the elements into a new array" />

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

<DrawnAnnotation text="Iterable<Int>" />
<DrawnAnnotation text="sum(list)" label="The explicit overload takes any collection" placement="down" />

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

<DrawnAnnotation text="[1, 2, 3]" />
<DrawnAnnotation text="[1, 2, 3, 4, 5, 6]" label="Collection literals, experimental in Kotlin 2.4" placement="down" />

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
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise</div>

# Functions

- Write functions using
    - expression bodies
    - default parameters
    - named arguments

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
<DrawnAnnotation text="): Int =" label="A value is expected, so `if` is used as an expression" placement="down" />

```kotlin
fun max(a: Int, b: Int): Int =
  if (a > b) a
```

</InlineCompilerError>

---
magic-move
---

# `if` is an expression

<DrawnAnnotation text="else b" label="Both branches supply the value" placement="down" />

```kotlin
fun max(a: Int, b: Int): Int =
  if (a > b) a else b

val two = max(1, 2)
```

---
magic-move
---

# `if` is an expression

<DrawnAnnotation text=") {" />
<DrawnAnnotation text="second" :occurrence="3" label="The last expression is the value of the block" placement="down" />

```kotlin
fun max(first: Int, second: Int): Int =
  if (first > second) {
    println("First")
    first
  } else {
    println("Second")
    second
  }

val two = max(1, 2)
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

<DrawnAnnotation text="when" label="A more powerful switch-case" />

```kotlin
fun max(a: Int, b: Int): Int = when {
  a > b -> a
  else -> b
}
```

---

# `when` matches on a subject

<DrawnAnnotation text="condition" :occurrence="2" label="`when` can match a given value" placement="down" />

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

<InlineCompilerError text="when (x)" message="'when' expression must be exhaustive. Add an 'else' branch.">

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

<DrawnAnnotation text="else ->" label="`else` covers every other value" />

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

<DrawnAnnotation text="when (x)" label="A `when` statement does not need to be exhaustive" placement="down" />

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

<DrawnAnnotation text="3, 4" label="Branches can be combined" />

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

<DrawnAnnotation text="-> {" label="A branch can be a block" placement="down" />

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

<DrawnAnnotation text="in 3..10" label="Ranges match with `in`" />

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

<DrawnAnnotation text="specialValue()" label="Arbitrary expressions can be matched" />

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

<DrawnAnnotation text="val x = calculate()" label="Bind the subject to a value while matching" placement="down" />

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

<DrawnAnnotation text="if !animal.mouseHunter" label="The guard runs only when the type match succeeds" />
<DrawnAnnotation text="is Animal.Cat ->" label="Still needed, the guard alone is not exhaustive" />

```kotlin
sealed interface Animal {
  data class Cat(val mouseHunter: Boolean) : Animal
  data class Dog(val breed: String) : Animal
}

fun feed(animal: Animal) = when (animal) {
  is Animal.Dog -> "Feed the ${animal.breed}"
  is Animal.Cat if !animal.mouseHunter -> "Feed the cat"
  is Animal.Cat -> "Leave the cat alone"
}
```

---

# Exhaustiveness follows the data flow

> Stable since Kotlin 2.3.0

<DrawnAnnotation text="if (role == UserRole.ADMIN) return 99" />
<DrawnAnnotation text="UserRole.GUEST -> 1" label="No `else` needed, the early return already handled `ADMIN`" placement="down" />

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

<DrawnAnnotation text="1..<10" label="Open-ended range" />
<DrawnAnnotation text="6 downTo 0 step 2" label="Ranges step in either direction" />

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

<DrawnAnnotation text="while (y != null)" label="The condition is checked after the first run" />

```kotlin
fun retrieveData(): Int? = null

do {
  println("Runs!")
  val y = retrieveData()
} while (y != null)
```

---

# `continue`, `break` and `return` steer a loop

<DrawnAnnotation text="continue" label="Skip to the next iteration" />

```kotlin
fun printOdd() {
  for (d in 1..10) {
    if (d % 2 == 0) continue
    print("$d, ")
  }
  println("done")
} // 1, 3, 5, 7, 9, done
```

---
magic-move
---

# `continue`, `break` and `return` steer a loop

<DrawnAnnotation text="break" label="Stop the loop" />

```kotlin
fun printOdd() {
  for (d in 1..10) {
    if (d % 2 == 0) break
    print("$d, ")
  }
  println("done")
} // 1, done
```

---
magic-move
---

# `continue`, `break` and `return` steer a loop

<DrawnAnnotation text="return" label="Leave the closest enclosing function" />

```kotlin
fun printOdd() {
  for (d in 1..10) {
    if (d % 2 == 0) return
    print("$d, ")
  }
  println("done")
} // 1,
```

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise</div>

# Control flow

- Rewrite an `if` chain as a `when` expression
- Iterate over a range and experiment with `continue` and `break`

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

<DrawnAnnotation text="?" label="The nullability modifier makes it explicit in the type" />

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

<DrawnAnnotation text="?." />
<DrawnAnnotation text="?." :occurrence="2" label="A safe call yields `null` instead of throwing" placement="down" />

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

<DrawnAnnotation text="!!." label="Forcefully unwrap the nullable type" placement="down" />

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
class: smart-cast-slide
---

# A null check smart-casts the type

<DrawnAnnotation text="nullable.length" label="Non-null access after the check" />

```kotlin
val nullable: String? = null
val length =
  if (nullable != null) nullable.length
  else -1

println(length) // -1
```

---
magic-move
---

# A null check smart-casts the type

<DrawnAnnotation text="?:" label="Elvis: when the left side is `null`, fall back to the right side" />

```kotlin
val nullable: String? = null
val length = nullable?.length ?: -1

println(length) // -1
```

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise</div>

# Null safety

- Be explicit about nullability and enforce null safety
- Provide a default with `?:`
- Rely on `!= null` and smart-casting
- Avoid `!!` at all costs, it is a code smell for unhandled cases

---

# Strings interpolate with `$`

<DrawnAnnotation text="$name" label="`$` substitutes a variable" />

```kotlin
val name = "John"

println("Hello $name!") // Hello John!
```

---
magic-move
---

# Strings interpolate with `$`

<DrawnAnnotation text="${name.length}" label="`${...}` substitutes any expression" />

```kotlin
val name = "John"

println("$name.length is ${name.length}") // John.length is 4
```

---
magicMove: true
class: multi-dollar-template
---

# Strings interpolate with `$`

<DrawnAnnotation text="$$" label="A multi-dollar prefix changes the interpolation symbol" />

```kotlin
val name = "John"

println($$"$$name owes you 100$") // John owes you 100$
```

<!--
String literals may contain pieces of code that are evaluated and whose results are concatenated into the string.
-->

---

# Raw strings need no escaping

<DrawnAnnotation text="&quot;&quot;&quot;" />
<DrawnAnnotation text="&quot;&quot;&quot;" :occurrence="2" label="A raw string is delimited by triple quotes" />

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

<DrawnAnnotation text="trimIndent()" label="Trim the common leading whitespace" placement="down" />

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

<DrawnAnnotation text="trimMargin()" label="Trim up to the `|` margin" placement="down" />

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

<DrawnAnnotation text="const" />

```kotlin
const val name = "John"

val text = """
    |for (c in "$name")
    |    print(c)
""".trimMargin()
```

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise</div>

# Strings

- Build a string with template expressions
- Format a multiline string using `trimIndent()` or `trimMargin()`
