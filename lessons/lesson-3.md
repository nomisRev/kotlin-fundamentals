---
layout: intro
class: section-slide
---

<div class="lesson-number">Lesson 3</div>

# Classes and types

---
class: type-hierarchy-slide
---

<KotlinTypeHierarchy />

<!--
Kotlin has a unified type hierarchy. Every non-null type has `Any` as a supertype;
`Any?` is the nullable top type. `Unit` has exactly one value, while `Nothing` has
no values and can stand in for any return type because it never returns.
The diagram is intentionally simplified: it shows representative branches rather
than every standard-library type.
-->

---
class: fundamentals-slide
---

# Defining classes

<DrawnAnnotation text="class Person" label="An empty class does not need a body or an explicit constructor" on="0">
<DrawnAnnotation text="Person()" label="No new keyword, call the constructor like a function" on="1">

````md magic-move [Person.kt]
```kotlin [Person.kt]
class Person
```

```kotlin [Person.kt]
class Person

val person = Person()
```
````

</DrawnAnnotation>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# Classes are final by default

<InlineCompilerError text="MyClass" :occurrence="2" message="This type is final, so it cannot be inherited from" on="0">
<DrawnAnnotation text="open" label="Explicit opt in to inheritance with open keyword, composition > inheritance" on="1">

````md magic-move [Inheritance.kt]
```kotlin [Inheritance.kt]
class MyClass

class MySubClass : MyClass()
```

```kotlin [Inheritance.kt]
open class MyClass

class MySubClass : MyClass()
```
````

</DrawnAnnotation>
</InlineCompilerError>

---
class: fundamentals-slide
---

# Primary constructor

> The primary constructor is part of the class header.

<DrawnAnnotation text="constructor" label="The keyword is optional when there are no modifiers" on="0">
<DrawnAnnotation text="val name: String" label="Constructor parameters can declare properties directly" on="1" placement="right">

````md magic-move [Person.kt]
```kotlin [Person.kt]
class Person constructor(name: String, age: Int) {
    val name: String = name
    var age: Int = age
}

val person = Person("Olivia", 42)
```

```kotlin [Person.kt]
class Person(val name: String, var age: Int)

val person = Person("Olivia", 42)
```
````

</DrawnAnnotation>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# Properties

<DrawnAnnotation text="person.age = 43" label="A var property can be read and reassigned" on="0" placement="right">
<InlineCompilerError :line="6" message="Val cannot be reassigned" on="0">

```kotlin [Person.kt]
class Person(val name: String, var age: Int)

val person = Person("Olivia", 42)
person.age = 43

person.name = "Ada"
```

</InlineCompilerError>
</DrawnAnnotation>


---
class: fundamentals-slide
---

# Java interop

<DrawnAnnotation text="getState();" label="A public Kotlin val exposes a Java getter" on="0" placement="right">
<DrawnAnnotation text="setCount(2);" label="A public Kotlin var exposes a Java getter & setter" on="0" placement="right">

```kotlin [MyClass.kt]
class MyClass(val state: String, var count: Int)
```

```java [JavaUsage.java]
class JavaUsage {
    void example(MyClass value) {
        String state = value.getState();
        value.setCount(2);
    }
}
```

</DrawnAnnotation>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# Initializer blocks

> Property initializers and `init` run in the order they appear in the class body.

```kotlin [Person.kt]
class Person(name: String, age: Int) {
    val name = name.lowercase()
    val age = age

    init {
        require(age >= 0) { "Age cannot be negative" }
    }
}
```


---
class: fundamentals-slide
---

# Member functions

<DrawnAnnotation text="fun introduce" label="Functions declared in a class are member functions" on="0">
<DrawnAnnotation text="$name" label="Members can use the instance's properties" on="0">

```kotlin [Person.kt]
class Person(val name: String, var age: Int) {
    fun introduce(to: String) {
        println("Hello $to, my name is $name.")
    }
}

val person = Person("Olivia", 42)
person.introduce(to = "Kotlin")
// Hello Kotlin, my name is Olivia.
```

</DrawnAnnotation>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# Custom property accessors

<DrawnAnnotation text="set(value)" label="Customize how a `var` is written" on="1" placement="right">
<DrawnAnnotation text="field += value" label="`field` is the property's backing field" on="1" placement="right">

````md magic-move [TextBuffer.kt]
```kotlin [TextBuffer.kt]
class TextBuffer {
    var text = ""
}
```

```kotlin [TextBuffer.kt]
class TextBuffer {
    var text = ""
        set(value) {
            field += value
        }
}

val buffer = TextBuffer()
buffer.text = "Hello"
buffer.text = ", world!"
println(buffer.text) // Hello, world!
```
````

</DrawnAnnotation>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# Computed properties

<DrawnAnnotation text="get()" label="A custom getter computes the value on every access" on="0" placement="right">
<DrawnAnnotation text="text.isBlank()" label="No backing field is needed when the getter does not use `field`" on="0" placement="right">

```kotlin [TextBuffer.kt]
class TextBuffer {
    var text = ""

    val isEmpty: Boolean
        get() = text.isEmpty()
}

val buffer = TextBuffer()
println(buffer.isEmpty) // true
buffer.text = "Hello"
println(buffer.isEmpty) // false
```

</DrawnAnnotation>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# Encapsulate mutation

<DrawnAnnotation text="private set" label="Anyone can read it, but only the class can write it" on="0" placement="right">

```kotlin [Connection.kt]
class Connection {
    var isOpen: Boolean = false
        private set

    fun open() {
        isOpen = true
    }
}

val connection = Connection()
connection.open()
println(connection.isOpen) // true
```

</DrawnAnnotation>

---
class: fundamentals-slide
---

# Object declarations

<DrawnAnnotation text="object" label="Define a single, named instance" on="0">
<DrawnAnnotation text="Environment.current" label="Access its members without constructing an instance" on="1" placement="right">

```kotlin [Environment.kt]
object Environment {
    var current = "production"
}

println(Environment.current) // production
```

</DrawnAnnotation>
</DrawnAnnotation>

> Use objects for one shared instance; avoid turning mutable global state into a default design.

---
class: fundamentals-slide
---

# Data classes

<DrawnAnnotation text="data class" label="Automatic equals, hashCode, toString" on="0" placement="up">
<DrawnAnnotation text="copy" label="Make a new instance with the same properties, except for the specified ones" on="1">

````md magic-move
```kotlin
data class User(val name: String, val age: Int)

val user = User("Ada", 36)

println(user)                      // User(name=Ada, age=36)
println(user.hashCode())           // 2035558
println(user == User("Ada", 36))   // true
```

```kotlin
data class User(val name: String, val age: Int)

val user = User("Ada", 36)

println(user)                      // User(name=Ada, age=36)
println(user.hashCode())           // 2035558
println(user == User("Ada", 36))   // true

val newValue = user.copy(age = 37)
println(newValue)                  // User(name=Ada, age=37)
println(newValue.hashCode())       // 2035559
println(user == newValue)          // false
```
````

</DrawnAnnotation>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# Position based destructuring

<DrawnAnnotation text="(username, email)" label="Order of parameters changed; email became displayName" color="red" on="1">

````md magic-move
```kotlin
data class User(val username: String, val email: String)
val user = User("alice", "alice@example.com")

val (username, email) = user

println(username)                   // alice
println(email)                      // alice@example.com
```

```kotlin
data class User(val username: String, val displayName: String, val email: String)
val user = User("alice", "kotliner", "alice@example.com")

val (username, email) = user

println(username)                   // alice
println(email)                      // kotliner
```
````

</DrawnAnnotation>

---
class: fundamentals-slide
---

# Name-based destructuring

<DrawnAnnotation text="email" on="0">
<DrawnAnnotation text="email" occurrence="2" on="0">
<DrawnAnnotation text="// &quot;alice@example.com&quot;" label="Destructing based on parameter name" on="0">
<DrawnAnnotation text="[name, mail]" label="Explicit position based destructuring. Risky but useful for collections." on="1">
<DrawnAnnotation text="(val name = username, val mail = email)" label="Prefer explicitly renaming name based destructured values" on="2">

````md magic-move
```kotlin
data class User(val username: String, val displayName: String, val email: String)
val user = User("alice", "kotliner", "alice@example.com")

val (username, email) = user

println(username)                   // alice
println(email)                      // "alice@example.com"
```

```kotlin
data class User(val username: String, val displayName: String, val email: String)
val user = User("alice", "kotliner", "alice@example.com")

val [name, mail] = user

println(name)                   // alice
println(mail)                      // "alice@example.com"
```

```kotlin
data class User(val username: String, val displayName: String, val email: String)
val user = User("alice", "kotliner", "alice@example.com")

(val name = username, val mail = email) = user

println(name)                   // alice
println(mail)                      // "alice@example.com"
```
````

</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>
</DrawnAnnotation>

---
class: fundamentals-slide
---

# Interfaces define capabilities

> An interface describes a contract without requiring a shared class hierarchy.

```kotlin [Shape.kt]
interface Shape {
    val sides: Int

    fun draw() = println("Drawing a $sides-sided shape")
}

class Polygon(override val sides: Int) : Shape

Polygon(6).draw() // Drawing a 6-sided shape
```

- No backing fields in interface properties.
- Members are public by default; required members use `override`.

---
class: fundamentals-slide
---

# A class can implement multiple interfaces

<DrawnAnnotation text="super<Clickable>" label="Choose which default implementation to call" on="1" placement="right">

```kotlin [Button.kt]
interface Clickable {
    fun click() = println("clicked")
}

interface Focusable {
    fun click() = println("focused")
}

class Button : Clickable, Focusable {
    override fun click() {
        super<Clickable>.click()
        super<Focusable>.click()
    }
}
```

</DrawnAnnotation>

> A class has one superclass, but it can implement many interfaces.

---
class: fundamentals-slide
---

# Sealed classes restrict a hierarchy

> A sealed type makes all of its direct subtypes known to the compiler.

```kotlin [UiState.kt]
sealed class UiState {
    data object Loading : UiState()
    data class Content(val text: String) : UiState()
    data class Error(val message: String) : UiState()
}

fun message(state: UiState) = when (state) {
    UiState.Loading -> "Loading…"
    is UiState.Content -> state.text
    is UiState.Error -> "Error: ${state.message}"
}
```

Direct subclasses must be in the same package and module; the sealed class itself cannot be instantiated.

---
class: fundamentals-slide
---

# Sealed interfaces model closed alternatives

> Use a sealed interface when alternatives do not need a shared superclass or state.

```kotlin [Result.kt]
sealed interface Result<out T>

data class Success<T>(val value: T) : Result<T>
data class Failure(val cause: String) : Result<Nothing>

fun describe(result: Result<String>) = when (result) {
    is Success -> "Success: ${result.value}"
    is Failure -> "Failure: ${result.cause}"
}
```

The compiler can check that every direct alternative is handled, so no `else` branch is needed.

---
class: fundamentals-slide
---

# Enums represent a fixed set of values

```kotlin [Direction.kt]
enum class Direction { NORTH, SOUTH, EAST, WEST }

val direction = Direction.NORTH

fun arrow(direction: Direction) = when (direction) {
    Direction.NORTH -> "↑"
    Direction.SOUTH -> "↓"
    Direction.EAST -> "→"
    Direction.WEST -> "←"
}
```

- Each enum entry is a singleton object.
- An enum variable contains one of the declared entries at a time.
- The `when` expression is exhaustive because the entries are known.

---
class: fundamentals-slide
---

# Enum entries can carry data

```kotlin [HttpStatus.kt]
enum class HttpStatus(val code: Int) {
    OK(200),
    NOT_FOUND(404),
    SERVER_ERROR(500),
}

for (status in HttpStatus.entries) {
    println("${status.name}: ${status.code}")
}
```

Use `entries` to iterate over all constants and `valueOf("OK")` to look one up by name.

---
class: fundamentals-slide
---

# Context-sensitive resolution

> Experimental in Kotlin 2.2.0: omit a type name when the expected type is known.

```kotlin [Problem.kt]
enum class Problem { CONNECTION, AUTHENTICATION, DATABASE }

// With -Xcontext-sensitive-resolution, Problem is known from the subject.
fun message(problem: Problem): String = when (problem) {
    CONNECTION -> "Connection failed"
    AUTHENTICATION -> "Authentication failed"
    DATABASE -> "Database failed"
}
```

Without the flag, keep the qualifier: `Problem.CONNECTION`.

---
class: fundamentals-slide
---

# Choosing a closed type

| Type | Best for |
| --- | --- |
| `enum class` | A fixed set of named, usually stateless values |
| `sealed class` | A fixed family of types that may hold different data or state |
| `sealed interface` | A fixed family of alternatives that can have unrelated class hierarchies |
| `interface` | An open capability that new implementations may add later |

> Model the set of valid alternatives in the type system, then let exhaustive `when` checks protect it.
