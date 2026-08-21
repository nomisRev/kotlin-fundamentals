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

<DrawnAnnotation text="var" on="0" label="Mutable variables are declared using `var`">
<DrawnAnnotation text=" =" on="1">
<DrawnAnnotation text=" =" occurrence="2" on="1" label="Type can be inferred by compiler">

````md magic-move [Var.kt]
```kotlin [Var.kt]
var number: Int = 42
var message: String = "Hello World!"
```

```kotlin [Var.kt]
var number = 42
var message = "Hello World!"
```

```kotlin [Var.kt]
var number = 42
var message = "Hello World!"

number += 1
message = "Hello from Kotlin!"
```
````

</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# Read-only values

<DrawnAnnotation text="val" on="0" label="Read-only values are declared using `val`">
<InlineCompilerError :line="4" text="number" message="val cannot be reassigned" on="1">
<InlineCompilerError :line="5" text="message" message="val cannot be reassigned" on="1">

````md magic-move [Values.kt]
```kotlin [Values.kt]
val number = 42
val message = "Hello World!"
```

```kotlin [Values.kt]
val number = 42
val message = "Hello World!"

number += 1
message = "Hello from Kotlin!"
```
````

</InlineCompilerError>
</InlineCompilerError>
</DrawnAnnotation>

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

<InlineCompilerError :line="2" text="mutableList" message="val cannot be reassigned" on="0">
<DrawnAnnotation text="remove" on="1" label="A mutable value can still be mutated">
<InlineCompilerError text='remove' message="Unresolved reference remove on receiver of type List<String>" at="2">
<InlineCompilerError text='add' message="Unresolved reference add on receiver of type List<String>" at="2">

````md magic-move [MutableList.kt]
```kotlin [MutableList.kt]
val mutableList: MutableList<String> = mutableListOf("Hello World!")
mutableList = mutableListOf("Hello Student!")
```

```kotlin [MutableList.kt]
val mutableList: MutableList<String> = mutableListOf("Hello World!")
mutableList.remove("Hello World!")
mutableList.add("Hello Student!")
```

```kotlin [MutableList.kt]
val list: List<String> = listOf("Hello World!")
mutableList.remove("Hello World!")
mutableList.add("Hello Student!")
```
````

</InlineCompilerError>
</InlineCompilerError>
</DrawnAnnotation>
</InlineCompilerError>

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

<InlineCompilerError :line="2" text="mutableList" message="val cannot be reassigned" at="0" until="1">
<DrawnAnnotation text="remove" on="1" label="A mutable value can still be mutated">
<InlineCompilerError text='remove' message="Unresolved reference remove on receiver of type List<String>" at="2">
<InlineCompilerError text='add' message="Unresolved reference add on receiver of type List<String>" at="2">

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
</DrawnAnnotation>
</InlineCompilerError>

---
class: fundamentals-slide
---

# Returns

<DrawnAnnotation on=0 occurrence="2" text="Unit" label="Return Unit when there is no output">
<DrawnAnnotation on=1 text=") {" label="Using `Unit` explicitly is optional">
<DrawnAnnotation on=2 text="val unit: Unit = " label="Functions always return a value">

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

</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>

<!--
Unit expresses that a function does not return any useful value. 
It is a type declared in the language, and it has only one value - Unit.

There are no procedures in Kotlin. Every function returns a value.
-->

---
class: fundamentals-slide
---

# Function body declaration

> Functions can be declared using block body style or as a single expression

````md magic-move [Sum.kt]
```kotlin [Sum.kt]
fun sum(a: Int, b: Int): Int {
    return a + b
}
```

```kotlin [Sum.kt]
fun sum(a: Int, b: Int): Int = a + b
```
````

<!--
Function block body contains multiple statements.
Function expression body contains a single expression.
-->

---
class: fundamentals-slide
---

# Default arguments

<DrawnAnnotation on="0" text="fun sum(a: Int, b: Int): Int = sum(a, b, 0)" label="Explicit overload for optional 'c: Int' parameter">
<DrawnAnnotation at="1" until="3" text="c: Int =" label="Assign an expression after parameter type">
<DrawnAnnotation on="2" text="a + b" label="Can reference previous parameters 'a' & 'b'" color="#eb55e6">

````md magic-move [Sum.kt]
```kotlin [Sum.kt]
fun sum(a: Int, b: Int, c: Int): Int = a + b + c

fun sum(a: Int, b: Int): Int = sum(a, b, 0)
```

```kotlin [Sum.kt]
fun sum(a: Int, b: Int, c: Int = 0): Int = a + b + c
```

```kotlin [Sum.kt]
fun sum(a: Int, b: Int, c: Int = a + b): Int = a + b + c
```

```kotlin [Sum.kt]
fun sum(a: Int, b: Int, c: Int = 0): Int = a + b + c

val two = sum(1, 1)
val three = sum(1, 1, 1)
```

```
````

</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>

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

<InlineCompilerError text="sum(1, 1)" message="error: No value passed for parameter 'c'" on="0">
<DrawnAnnotation on="1" text="c = " label="Use explicit named parameter to help the compiler">

````md magic-move [Sum.kt] {at: 1}
```kotlin [Sum.kt]
fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

val two = sum(1, 1)
```

```kotlin [Sum.kt]
fun sum(a: Int, b: Int = 0, c: Int): Int = a + b + c

val two = sum(1, c = 1)
```
````

</DrawnAnnotation>
</InlineCompilerError>

---
class: fundamentals-slide
---

# Named parameters

> Call a function with a named parameter to identify and clarify arguments

````md magic-move [Print.kt]
```kotlin [Print.kt]
fun print(
  message: String,
  offset: Int = 0,
  length: Int = message.length
) { /*...*/ }
```

```kotlin [Print.kt]
fun print(
  message: String,
  offset: Int = 0,
  length: Int = message.length
) { /*...*/ }

println("Hello World!", offset = 6, length = 6) //World!
```

```kotlin [Print.kt]
fun print(
  message: String,
  offset: Int = 0,
  length: Int = message.length
) { /*...*/ }

println("Hello World!", offset = 6, length = 6) //World!

println("Hello World!", length = 5)              //Hello
```

```kotlin [Print.kt]
fun print(
  message: String,
  offset: Int = 0,
  length: Int = message.length
) { /*...*/ }

println("Hello World!", offset = 6, length = 6) //World!

println("Hello World!", length = 5)              //Hello

println(length = 6, message = "Hello World!", offset = 6)
```
````

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

<DrawnAnnotation text="vararg" label="Accepts a variable number of arguments of the same parameter type" on="0">
<DrawnAnnotation text="*list" label="Spread operator copies elements into a new array" on="1">
<DrawnAnnotation text="Iterable<Int>" label="Explicit support for Iterable" on="2" placement="right">
<DrawnAnnotation text="[1, 2, 3]" on="3">
<DrawnAnnotation text="[1, 2, 3, 4, 5, 6]" label="Collection literals (experimental 2.4.x)" on="3" placement="right">

````md magic-move
```kotlin [VarArg.kt]
fun sum(vararg numbers: Int): Int = TODO()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)
```

```kotlin [VarArg.kt]
fun sum(vararg numbers: Int): Int = TODO()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)

val list = listOf(1, 2, 3, 4, 5, 6)
val spread = sum(*list)
```

```kotlin [VarArg.kt]
fun sum(vararg numbers: Int): Int = numbers.sum()
fun sum(numbers: Iterable<Int>): Int = numbers.sum()

val six = sum(1, 2, 3)
val twentyOne = sum(1, 2, 3, 4, 5, 6)

val list = listOf(1, 2, 3, 4, 5, 6)
val spread = sum(list)
```

```kotlin [VarArg.kt]
fun sum(numbers: Iterable<Int>): Int = numbers.sum()

val six = sum([1, 2, 3])
val twentyOne = sum([1, 2, 3, 4, 5, 6])

val list = listOf(1, 2, 3, 4, 5, 6)
val spread = sum(list)
```
````

</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>

<!--
Simplifies the caller side by avoiding collections at all

Visible type for caller is Int. Inside the function body it is Array<Int>
Only one varargs is allowed.
Must be last parameter. Compiler can't disambiguate when the varargs end, and the other parameters start. 
-->

---
layout: intro class: exercise-slide kodee: heart
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

<InlineCompilerError text="if" message="'if' must have both main and 'else' branches when used as an expression." at="0" until="2">
<DrawnAnnotation text="): Int =" label="'if' is used as an expression, and a value is expected" on="1" >
<DrawnAnnotation text=") {" label="'if' blocks can contain more statements." on="4" placement="right">
<DrawnAnnotation text="second" occurrence="2" label="last expression is used as return value" on="4" placement="right">

````md magic-move [If.kt]
```kotlin [If.kt]
fun max(a: Int, b: Int) =
    if(a > b) a
```

```kotlin [If.kt]
fun max(a: Int, b: Int): Int =
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
    val two = max(1, 2)
}
```

```kotlin [If.kt]
fun max(a: Int, b: Int) =
    if (first > second) {
        println("First")
        first
    } else {
        println("Second")
        second
    }

fun main() {
    val two = max(1, 2)
}
```
````

</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</InlineCompilerError>

---
class: fundamentals-slide
---

# Control flow - when

<DrawnAnnotation text="when" label="A powerful switch-case statement." on="1" >

````md magic-move [Max.kt]
```kotlin [Max.kt]
fun max(a: Int, b: Int) =
    if(a > b) a
```

```kotlin [Max.kt]
fun max(a: Int, b: Int) = when {
    a > b -> a
    else -> b
}
```
````

</DrawnAnnotation>

---
class: fundamentals-slide
---

# Control flow - when with subject

<DrawnAnnotation text="condition" occurrence="2" label="when can work over a given value." on="0" >
<InlineCompilerError text="when(x)" message="'when' expression must be exhaustive. Add an 'else' branch." at="1" until="2">
<DrawnAnnotation text="when(x)" label="'when' doesn't require exhaustiveness when used as statement" on="3" >
<DrawnAnnotation text="3, 4" label="branches can be combined." on="4" >
<DrawnAnnotation text="in 3..10" label="ranges can be used for matching the branches" on="6" >
<DrawnAnnotation text="specialValue()" label="arbitrary expressiosn can be used for matching the branches" on="7" >
<DrawnAnnotation text="val x = calculate()" label="Define a value 'when' matching it" on="8" >

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

```kotlin [When.kt]
fun describe(x: Int) {
  when(x) {
    1 -> println("x == 1")
    2 -> println("x == 2")
  }
}
```

```kotlin [When.kt]
fun describe(x: Int) {
  when(x) {
    1 -> println("x == 1")
    2 -> println("x == 2")
    3, 4 -> println("3 or 4")
  }
}
```

```kotlin [When.kt]
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

```kotlin [When.kt]
fun describe(x: Int) {
  when (x) {
    1 -> println("x == 1")
    2 -> println("x == 2")
    in 3..10 -> println("in 3..10")
  }
}
```

```kotlin [When.kt]
fun describe(x: Int) {
  when (x) {
    1 -> println("x == 1")
    2 -> println("x == 2")
    specialValue() -> println("x matches the special value")
  }
}
```

```kotlin [When.kt]
when (val x = calculate()) {
  1 -> println("x == 1")
  2 -> println("x == 2")
  specialValue() -> println("x matches the special value")
}
```
````

</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</InlineCompilerError>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# Control flow - for loop

> Can iterate over collections, ranges, and similar structures.

```kotlin [For.kt]
fun main() {
    for (item in ["One", "Two", "Three"]) print("$item, ") // One, Two, Three,
    for (item in 1..10) print("$item, ") // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
    for (item in 1..<10) print("$item, ") // 1, 2, 3, 4, 5, 6, 7, 8, 9,
    for (item in 6 downTo 0 step 2) print("$item, ") // 6, 4, 2, 0,
}
```

---
class: fundamentals-slide
---

# Control flow - while loop

```kotlin [While.kt]
var x = 0
while (x < 10) {
    print("$x, ")
    x++
} // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
```

---
class: fundamentals-slide
---

# Control flow - while loop

<DrawnAnnotation text="while (y != null)" label="Always executed once before condition is checked" on="0">

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

> (TODO: better examples or skip?)

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
layout: intro class: exercise-slide kodee: heart
---

<div class="lesson-number">Exercise</div>

# Control flow

- Rewrite an `if` chain as a `when` expression.
- Iterate over a range and experiment with `continue` and `break`.

---
class: fundamentals-slide
---

# Null Safety

<DrawnAnnotation text="?" label="Nullability modifier helps to make nullability explicit at a type level" on="1">
<InlineCompilerError text="null" message="null can not be a value of a non-null" on="0">

````md magic-move [Nullable.kt]
```kotlin [Nullable.kt]
val valueOrNull: String = null
```

```kotlin [Nullable.kt]
val valueOrNull: String? = null
```
````

</InlineCompilerError>
</DrawnAnnotation>

<!--
In Kotlin, the type system distinguishes between references that can hold null (nullable references), 
and those that cannot (non-null references).

To allow nulls, we can declare a variable as nullable string, written String?
-->

---
class: fundamentals-slide
---

# Safe call operator

<DrawnAnnotation text="?." label="Safely access nullable values" on="0"  placement="right">
<DrawnAnnotation text="?." occurrence="2" on="0" >

```kotlin [Nullable.kt]
var nullable: String? = "Hello World!"
println(nullable?.length)
//12

nullable = null
println(nullable?.length)
//null  
```

</DrawnAnnotation>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# !! operator

<DrawnAnnotation text="!!." label="Forcefully unwrap nullable type" on="0" placement="right">

```kotlin [Nullable.kt]
val nullable: String? = null

println(nullable!!.length)
```

```console
Exception in thread "main" java.lang.NullPointerException
```

</DrawnAnnotation>

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

<DrawnAnnotation text="nullable.length" label="safe non-null access after smart-cast" :on="0">
<DrawnAnnotation text="?:" label="When left side is null then fallback on right side" :on="1">

````md magic-move [SmartCast.kt]
```kotlin [SmartCast.kt]
val nullable: String? = null
val length =
    if (nullable != null) nullable.length
    else -1

println(length)  //-1
```

```kotlin [SmartCast.kt]
val nullable: String? = null
val length = nullable?.length ?: -1

println(length)  //-1
```
````

</DrawnAnnotation>
</DrawnAnnotation>

<style>
.smart-cast-slide .slidev-code code.language-kotlin > .line:nth-of-type(3) > span:last-child {
  background: linear-gradient(#dcf8df, #dcf8df) 2ch center / 8ch 100% no-repeat;
}
</style>

---
layout: intro class: exercise-slide kodee: heart
---

<div class="lesson-number">Exercise</div>

# Null safety

- By explicit about nullability and enforce null safety
- Provide a default with `?:`
- Rely on != null & smart-casting
- `!!` should be avoided at all costs. It's a code smell for unhandled cases.

---
class: fundamentals-slide
---

# String templates

<DrawnAnnotation text="$name" label="Use `$` to substitute variables" on="0">
<DrawnAnnotation text=${name.length} label="Use `${...}` to substitute arbitrary expressions" on="1">
<DrawnAnnotation text=$$ label="Change substitute symbol with a multi-dollar symbol" on="2">

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

println($$"$$name owes you 100$") //John.length owes you 100$
```
````

</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>

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

> Contains no escaping and can contain newlines and any other characters

<DrawnAnnotation text="&quot;&quot;&quot;" label="A raw string is delimited by a triple quote" on="0">
<DrawnAnnotation text="&quot;&quot;&quot;" occurrence="2" on="0">
<DrawnAnnotation text="trimIndent()" on="1" label="Trim leading whitespace">
<DrawnAnnotation text="trimMargin()" on="2" label="Trim before delimiter (default) `|`">
<DrawnAnnotation text="const" on="3" label="Compile-time constant">
<DrawnAnnotation text="const" on="3" occurrence="2">

````md magic-move
```kotlin [MultiString.kt]
val name = "John"

val text = """
    for (c in "$name")
        print(c)
"""
```

```kotlin [MultiString.kt]
val name = "John"

val text = """
    for (c in "$name")
        print(c)
""".trimIndent()
```

```kotlin [MultiString.kt]
val name = "John"

val text = """
    |for (c in "$name")
    |    print(c)
""".trimMargin()
```

```kotlin [MultiString.kt]
const val name = "John"

const val text = """
    |for (c in "$name")
    |    print(c)
""".trimMargin()
```
````

</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>

---
layout: intro class: exercise-slide kodee: heart
---

<div class="lesson-number">Exercise</div>

# Strings

- Build a string with template expressions.
- Format a multiline string using `trimIndent()` or `trimMargin()`.

