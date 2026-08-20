---
layout: intro
class: section-slide
---

<div class="lesson-number">Lesson 2</div>

# Kotlin Syntax

---
class: fundamentals-slide
---

# Variables

> Variables are defined with the `var` keyword

````md magic-move [Var.kt]
```kotlin [Var.kt]
var number: Int = 42
var message: String = "Hello World!"
```

```kotlin [Var.kt]
var number = 42
var message = "Hello World!"
```
````

---
class: fundamentals-slide
---

# Variables

> `var` can be reassigned or mutated

````md magic-move [Mutation.kt]
```kotlin [Mutation.kt]
var count = 0
count = 1

println(count) // 1
```

```kotlin [Mutation.kt]
var count = 0
count = 1

println(count) // 1

count += 1
println(count) // 2
```
````

---
class: fundamentals-slide
---

# Read-only values

> Define **read-only** value using `val`

<InlineCompilerError :line="4" message="error: Val cannot be reassigned" at="1">

````md magic-move [Values.kt]
```kotlin [Values.kt]
val number = 42
val message = "Hello World!"
```
```kotlin [Values.kt]
val number = 42
val message = "Hello World!"

message = "Hello from Kotlin!"
```
````

</InlineCompilerError>

<!--
Variables can be defined as read-only using the val keyword instead of var.
Apart from the keyword change, the syntax remains the same.

However, this simple change has a big impact. The variable cannot be reassigned after the
initialization, and trying to do so will result in a compilation error. 

Try to use read-only variables when possible as mutability adds a layer of complexity to reason with code.  
-->

---
class: fundamentals-slide
---

# Read-only variables

> Can be understood as an immutable reference

<InlineCompilerError :line="2" message="error: Val cannot be reassigned">

```kotlin [MutableList.kt]
val mutableList = mutableListOf("Hello World!")
mutableList = mutableListOf("Hello Student!")
```

</InlineCompilerError>

> It is possible to change mutable objects

```kotlin [MutableList.kt]
val mutableList = mutableListOf("Hello World!")
mutableList.remove("Hello World!")
mutableList.add("Hello Student!")

println(mutableList) // [Hello Student!]
```

<!--
It is important to note that `val` implies read-only.
If a mutable structure is assigned, the variable cannot be mutated (reassigned), but the actual structure can. 

In the example, we have a mutable list which contains Hello World.
If we want to replace the message to greet the student instead, the variable cannot be reassigned.
However, as it is a mutable list, we can modify the messages.
-->

---
class: fundamentals-slide
---

# Read-only parameters

> Parameters are read-only; object references are passed by value

<InlineCompilerError :line="2" message="error: Val cannot be reassigned" at="0" until="1">
<InlineCompilerError :line="2" message="Unresolved reference 'remove' on receiver of type List<String>" at="2">
<InlineCompilerError :line="3" message="Unresolved reference 'add' on receiver of type List<String>" at="2">

````md magic-move [ReadOnlyParameters.kt]
```kotlin [ReadOnlyParameters.kt]
fun changeGreeting(mutableList: MutableList<String>) {
  mutableList = mutableListOf("Hello Student!")
}
```
```kotlin [ReadOnlyParameters.kt]
fun changeGreeting(mutableList: MutableList<String>) {
  mutableList.remove("Hello World!")
  mutableList.add("Hello Student!")
}
```
```kotlin [ReadOnlyParameters.kt]
fun changeGreeting(list: List<String>) {
  list.remove("Hello World!")
  list.add("Hello Student!")
}
```
````

</InlineCompilerError>
</InlineCompilerError>
</InlineCompilerError>

---
class: fundamentals-slide
---

# Unit-returning functions

> Functions always return a value

> Return `Unit` when there is no output

````md magic-move [Unit.kt]
```kotlin [Unit.kt]
fun helloWorld(): Unit {
  println("Hello World!")
  return Unit
}

helloWorld()
```

```kotlin [Unit.kt]
fun helloWorld() {
  println("Hello World!")
}

helloWorld()
```

```kotlin [Unit.kt]
fun helloWorld() {
  println("Hello World!")
}

val unit: Unit = helloWorld()
```
````

<v-click :at="1">

> Using `Unit` explicitly is optional

</v-click>

<!--
Unit expresses that a function does not return any useful value. 
It is a type declared in the language, and it has only one value - Unit.

There are no procedures in Kotlin. Every function returns a value.
-->

---
class: fundamentals-slide
---

# Statement vs Expression

> Statement 👉 every part of code that carries out an action.

```kotlin [HelloWorld.kt]
println("Hello world!")
val a = 1
```

> Expression 👉 every part of code that produces a value

```kotlin [Sum.kt]
1 + 1
sum(1, 2)
2 + sum(1 + 1, sum(1, 2))
```

<!--
A statement is the smallest standalone element of a programming language that expresses some action to be carried out.
An expression is a combination of one or more explicit values, constants, variables, operators and functions that the 
programming language interprets and computes to produce another value. This is called evaluation.
 
As Kotlin is a language that mixes imperative and functional styles, the distinction between both of them is less strong.
All functions return a value, hence all functions are expressions. 
However, those that return Unit could be considered statements.
-->

---
class: fundamentals-slide
---

# Function body declaration

> Functions can be declared using block body style or as a single expression

```kotlin [Sum.kt]
fun sum(a: Int, b: Int): Int {
    return a + b
}
```

```kotlin [Sum.kt]
fun sum(a: Int, b: Int): Int = a + b
```

<!--
Function block body contains multiple statements.
Function expression body contains a single expression.
-->

---
class: fundamentals-slide
---

# Default arguments

> Avoid overloads for optional parameters

```kotlin [Sum.kt]
fun sum(a: Int, b: Int, c: Int): Int = a + b + c

fun sum(a: Int, b: Int): Int = sum(a, b, 0)
```

> Assign an expression after parameter type

```kotlin [Sum.kt]
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
class: fundamentals-slide
---

# Default arguments

> Order is important, it can be ambiguous

<InlineCompilerError :line="3" message="error: No value passed for parameter 'c'" until="1">

````md magic-move [Sum.kt] {at: 1}
```kotlin [Sum.kt]
fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

val two = sum (1, 1)
```

```kotlin [Sum.kt]
fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

val two = sum(1, c = 1)
```
````

</InlineCompilerError>

<v-click :at="1">

> Use explicit named parameter to help the compiler

</v-click>

---
class: fundamentals-slide
---

# Named parameters

> Call function with named parameter to identify and clarify arguments

```kotlin [Print.kt]
fun print(
  message: String,
  offset: Int = 0,
  length: Int = message.length
) { /*...*/ }

print("Hello World!", offset = 6, length = 6) //World!

print("Hello World!", length = 5)              //Hello

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
class: fundamentals-slide
---

# Variable arguments

> Accepts a variable number of arguments of the same parameter type

```kotlin [VarArg.kt]
fun sum(vararg numbers: Int): Int = numbers.sum()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)
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
  - defaults parameters
  - named arguments

---
class: fundamentals-slide
---

# Control flow - if

> `if` is an expression and returns a value

> If used as an expression exhaustive branching is enforced

<InlineCompilerError :line="2" message="'if' must have both main and 'else' branches when used as an expression." until="1">

````md magic-move [Sum.kt]  {at: 1}
```kotlin [If.kt]
fun max(a: Int, b: Int) =
    if(a > b) a
```
```kotlin [If.kt]
fun max(a: Int, b: Int) =
    if(a > b) a else b
```
```kotlin [If.kt]
fun max(a: Int, b: Int) =
    if(a > b) a else b

fun main() {
    val max = max(1, 2)
    if (max >= 0) { // can omit else if used as statement 💡
        println(max)
    }
}
```
````

</InlineCompilerError>

---
class: fundamentals-slide
---

# Control flow - if

> If branches can be blocks and contain more statements.

```kotlin [Max.kt]
fun max(first: Int, second: Int): Int =
    if (first > second) {
        println("First")
        first
    } else {
        println("Second")
        second  // last expression is used as return value
    }
```

---
class: fundamentals-slide
---

# Control flow - when

> A powerful switch-case statement.

> Can work both as a statement or as an expression.

```kotlin [Max.kt]
fun max(a: Int, b: Int) = when {
    a > b -> a
    else -> b
}
```

---
class: fundamentals-slide
---

# Control flow - when with subject

> Can work over a given value.

> When used as expression, **all possible inputs must be covered**.

<InlineCompilerError :line="1" message="'when' expression must be exhaustive. Add an 'else' branch." at="1" until="2">

````md magic-move [When.kt]
```kotlin  [When.kt]
fun print(condition: Boolean) = when(condition) {
  true -> println("Encountered $condition")
  false -> println("Encountered $condition")
}
```
```kotlin [When.kt]
fun describe(x: Int) = when(x) {
  1 -> "x == 1"
  2 -> "x == 2"
}
```
```kotlin [When.kt]
fun describe(x: Int) = when(x) {
  1 -> "x == 1"
  2 -> "x == 2"
  else -> "x is neither 1 nor 2"
}
```
````

</InlineCompilerError>

---
class: fundamentals-slide
---

# Control flow - when

> `when` doesn't require exhaustiveness when used as statement

```kotlin [Statement.kt]
fun main() {
    val x: Int = 2
    when (x) {
        1 -> println("x == 1")
        2 -> println("x == 2")
    }
}
```

---
class: fundamentals-slide
---

# Control flow - when

> Branches can be combined.

> Branches can be code blocks

```kotlin [Descibe.kt]
fun describe(x: Int) = when (x) {
    1, 2 -> "x == 1 or x == 2 "
    else -> {
        println("Hello World!")
        "x is neither 1 nor 2"
    }
}
```

---
class: fundamentals-slide
---

# Control flow - when

> Arbitrary expressions or ranges can be used for matching the branches.

```kotlin
when (x) {
    parseInt(s) -> print("s encodes x")
    else -> print("s does not encode x")
}

when (x) {
    in 1..10 -> print("x is in the range")
    else -> print("x is outside the range")
}
```

---
class: fundamentals-slide
---

# Control flow - when

> Can capture value on a variable before matching.
> Can use the value inside the case blocks.

```kotlin
sealed class Result {
    object Success : Result()
    object Failure : Result()
}

fun main(args: Array<String>) {
    fun doSomething(): Result = Result.Success

    when (val result = doSomething()) {
        is Result.Success -> println("Worked! $result")
        is Result.Failure -> println("Failed $result")
    }
}
```

---
class: fundamentals-slide
---

# Control flow - for loop

> For loops are statements, not expressions.

> Can iterate over collections, ranges, and similar structures.

```kotlin [For.kt]
fun main() {
    val collection = listOf("One", "Two", "Three")
    for (item in collection) print("$item, ")
    for (item in 1..10) print("$item, ")
    for (item in 1 until 10) print("$item, ")
    for (item in 6 downTo 0 step 2) print("$item, ")
}
```

---
class: fundamentals-slide
---

# Control flow - while loop

> <code>while</code> and <code>do while</code> are <b>statements</b>, not expressions

```kotlin [While.kt]
var x = 0
while (x < 10) {
    print("$x, ")
    x++ // Same as x += 1
}
```

---
class: fundamentals-slide
---

# Control flow - while loop

> Do - while evaluates the condition after block execution.

> At least 1 execution always takes place.

<DrawnAnnotation text="while (y != null)" label="Y is visible here" :at="1">

```kotlin [DoWhile.kt]
fun retrieveData(): Int? = null

do {
    println("Runs!")
    val y = retrieveData()
} while (y != null)
```

</DrawnAnnotation>

---
class: fundamentals-slide
---

# Control flow - continue / break

> `continue` -> skips to the next iteration

> `break` -> stops the loop

> `return` -> returns from closest enclosing function

```kotlin [NestedFor.kt]
for (d in 1..10) {
    if (d % 2 == 0) {
        continue // try break and return here
    } else {
        print("$d, ")
    }
}
```

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise</div>

# Control flow

- Rewrite an `if` chain as a `when` expression.
- Iterate over a range and experiment with `continue` and `break`.

---
class: fundamentals-slide
---

# Null Safety

> Nullability is a first class concept in Kotlin. Use `?`.

> Nullability modifier helps to make nullability explicit at a type level.

<InlineCompilerError :line="2" message="error: Null can not be a value of a non-null">

```kotlin [Nullable.kt]
val nullable: String? = null
val nonNullable: String = null
```

</InlineCompilerError>

<!--
In Kotlin, the type system distinguishes between references that can hold null (nullable references), 
and those that cannot (non-null references).

To allow nulls, we can declare a variable as nullable string, written String?
-->

---
class: fundamentals-slide
---

# Safe call operator

> First class nullability control by the `?` modifier.

> Use it to access inner members safely.

```kotlin [Nullable.kt]
var nullable: String? = "Hello World!"
println(nullable?.length)
//12

nullable = null
println(nullable?.length)
//null  
```

---
class: fundamentals-slide
---

# !! operator

* **!!** to force unwrap, but 🚨☢️ Danger ☢️🚨

```kotlin [Nullable.kt]
val nullable: String? = null

println(nullable!!.length)
```

```console
Exception in thread "main" java.lang.NullPointerException
```

<style>
.slidev-code code.language-console,
.slidev-code code.language-console * {
  color: #ef4444 !important;
}
</style>

---
class: fundamentals-slide smart-cast-slide
---

# Smart casting

> `if` a value is `!= null` it is automatically casted to non-null

<DrawnAnnotation text="nullable.length" label="safe non-null access after smart-cast" :at="1">

```kotlin [SmartCast.kt]
val nullable: String? = null
val length =
    if (nullable != null) nullable.length
    else -1

println(length)  //-1
```

</DrawnAnnotation>

<style>
.smart-cast-slide .slidev-code code.language-kotlin > .line:nth-of-type(3) > span:last-child {
  background: linear-gradient(#dcf8df, #dcf8df) 2ch center / 8ch 100% no-repeat;
}
</style>

---
class: fundamentals-slide smart-cast-slide
---

# Elvis operator

> Replace if-else with elvis operator

> Right side of **?:** provides the value when left side is `null`.

````md magic-move [Elvis.kt]
```kotlin [Elvis.kt]
val nullable: String? = null
val length =
        if (nullable != null) nullable.length
        else -1

println(length) // -1
```

```kotlin [Elvis.kt]
val nullable: String? = null
val length = nullable?.length ?: -1

println(length) // -1
```
````

<style>
/* The first magic-move snippet's `) nullable.length` token is child 18. */
.smart-cast-slide :deep(pre.shiki-magic-move-container > span:nth-child(18)) {
  background: linear-gradient(#dcf8df, #dcf8df) 2ch center / 8ch 100% no-repeat;
}
</style>

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise</div>

# Null safety

- Make a value nullable and safely transform it.
- Provide a default with `?:`; discuss why `!!` should be exceptional.

---
class: fundamentals-slide
---

# Type inference

> Types can be omitted and inferred

````md magic-move [Infer.kt]
```kotlin [Infer.kt]
fun increment(x: Int): Int = x + 1

val two: Int = increment(1)
```

```kotlin [Infer.kt]
fun increment(x: Int) = x + 1

val two = increment(1)
```
````

<!--
Kotlin is: 
 * Strongly typed, which means that values have a specific type
 * Statically typed, which means that value types are known in compile time 
-->

---
class: fundamentals-slide
---

# String templates

> Kotlin allows string templates using `$` to subtitute variables, or an arbitrary expression in curly braces.

````md magic-move [StringTemplate.kt] { class: 'multi-dollar-template' }
```kotlin [StringTemplate.kt]
val name = "John"

println("Hello $name!") //Hello John!
```
```kotlin [StringTemplate.kt]
val name = "John"

println("$name.length is ${name.length}") //John.length is 4
```
```kotlin [StringTemplate.kt]
val name = "John"

println($$"$$name owes you 100$") //John.length is 4
```
````

<style>
/* Shiki splits the opening quote and first `$` of `$$name` into one token.
   Paint that two-character token per glyph so the quote stays a string green
   while both interpolation dollars match the blue `name` token. */
:global(.multi-dollar-template .shiki-magic-move-item[style*="--shiki-light: #067D17"][style*="--smm-stagger: 1ms"]) {
  color: transparent !important;
  background: linear-gradient(to right, #067d17 0 50%, #0033b3 50% 100%);
  -webkit-background-clip: text;
  background-clip: text;
}

:global(html.dark .multi-dollar-template .shiki-magic-move-item[style*="--shiki-dark: #6AAB73"][style*="--smm-stagger: 1ms"]) {
  background: linear-gradient(to right, #6aab73 0 50%, #cf8e6d 50% 100%);
}
</style>

<!--
String literals may contain pieces of code that are evaluated and whose results are concatenated into the string.
-->

---
class: fundamentals-slide
---

# Multiline strings

> A raw string is delimited by a triple quote

> Contains no escaping and can contain newlines and any other characters

```kotlin [MultiString.kt]
val name = "John"

val text = """
    for (c in "$name")
        print(c)
"""
```

---
class: fundamentals-slide
---

# Multiline strings

> Trim leading whitespace, `trimIndent()`

> Alternatively use `trimMargin` with `|` delimiter 

```kotlin [TrimMargin.kt]
val multiline = """
                |ABC
                |123
                |456
        """.trimIndent()

println(multiline) 
//ABC
//123
//456
```

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise</div>

# Strings

- Build a string with template expressions.
- Format a multiline string using `trimIndent()` or `trimMargin()`.

