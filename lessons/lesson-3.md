---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 3</div>

# Classes and types

## Model the domain in the type system

---
class: type-hierarchy-slide
---

<KotlinTypeHierarchy />

<!--
Kotlin has a unified type hierarchy. Every non-null type has `Any` as a supertype;
`Any?` is the nullable top type. `Unit` has exactly one value, while `Nothing` has
no values and can stand in for any return type because it never returns. The final
build adds a pink "?" badge to `Any`, `String`, `Boolean`, `Number`, and `Nothing`:
every type has a nullable counterpart, and `Nothing?` is the one type whose only
possible value is `null`.
The diagram is intentionally simplified: it shows representative branches rather
than every standard-library type.
-->

---

# A class needs no body

```kotlin
class Person constructor() {
    /** **/
}
```

---
magic-move
---

# A class needs no body

<DrawnAnnotation text="class Person" label="No body and no explicit constructor" :geometry="{ label: { x: 0.2409, y: 0.2793 } }" :connect="false"/>

```kotlin
class Person
```

---
magic-move
---

# A class needs no body

<DrawnAnnotation text="Person()" label="No `new`, call the constructor like a function" :geometry="{ label: { x: 0.3398, y: 0.3727 } }" :connect="false"/>

```kotlin
class Person

val person = Person()
```

---

# Classes are final by default

<InlineCompilerError text="MyClass" :occurrence="2" message="This type is final, so it cannot be inherited from.">

```kotlin
class MyClass

class MySubClass : MyClass()
```

</InlineCompilerError>

---
magic-move
---

# Classes are final by default

<DrawnAnnotation text="open" label="Explicit opt in to inheritance, prefer composition" :geometry="{ label: { x: 0.3242, y: 0.3762 }, connector: { type: 'quadratic', start: { x: 0.1016, y: 0.2278 }, control: { x: 0.1033, y: 0.2838 }, end: { x: 0.1016, y: 0.3496 } } }" :connect="false"/>

```kotlin
open class MyClass

class MySubClass : MyClass()
```

---

# The primary constructor is the class header

```kotlin
class Person(name: String, age: Int) {
  val name: String = name
  var age: Int = age
}
val person = Person("Olivia", 42)
```

---
magic-move
---

# The primary constructor is the class header

<DrawnAnnotation text="constructor" label="The `constructor` keyword is optional when there are no modifiers or annotations" :geometry="{ label: { x: 0.3894, y: 0.4756 } }" :connect="false"/>

```kotlin
class Person constructor(name: String, age: Int) {
  val name: String = name
  var age: Int = age
}
val person = Person("Olivia", 42)
```

---
magic-move
---

# The primary constructor is the class header

```kotlin
class Person @MyAnnotation private constructor(name: String, age: Int) {
  val name: String = name
  var age: Int = age
}
```

---
magic-move
---

# The primary constructor is the class header

<DrawnAnnotation text="val name: String" label="Constructor parameters can declare properties directly" :connect="false"/>

```kotlin
class Person(val name: String, var age: Int)

val person = Person("Olivia", 42)
```

---
magic-move
---

# Properties follow their keyword

<DrawnAnnotation text="person.age = 43" label="A `var` property can be read and reassigned" :geometry="{ label: { x: 0.2737, y: 0.5267 } }" :connect="false"/>
<InlineCompilerError :line="6" message="'val' cannot be reassigned.">

```kotlin
class Person(val name: String, var age: Int)

val person = Person("Olivia", 42)
person.age = 43

person.name = "Ada"
```

</InlineCompilerError>

---

# Properties become getters and setters in Java

<DrawnAnnotation text="getState();" label="A public Kotlin `val` exposes a Java getter" :geometry="{ label: { x: 0.5895, y: 0.4373 } }" :connect="false"/>
<DrawnAnnotation text="setCount(2);" label="A public Kotlin `var` exposes a getter and a setter" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.3597, y: 0.5056 } }" :connect="false"/>

```kotlin
class MyClass(val state: String, var count: Int)
```

```java
public class JavaUsage {
  void example(MyClass value) {
    String state = value.getState();
    value.setCount(2);
  }
}
```

---

# Getters and setters become properties in Kotlin

<DrawnAnnotation text="value.state" label="A Java getter is read as a property" :connect="false" :geometry="{ label: { x: 0.5089, y: 0.6692 } }"/>
<DrawnAnnotation text="value.count = 2" label="A getter and setter pair becomes a mutable property" color="var(--code-window-amper-color)" :connect="false" :geometry="{ label: { x: 0.5145, y: 0.7538 } }"/>

```java
public class JavaBean {
  private String state;
  private int count;

  public String getState() { return state; }
  public int getCount() { return count; }
  public void setCount(int count) { this.count = count; }
}
```

```kotlin
fun example(value: JavaBean) {
  val state = value.state
  value.count = 2
}
```

---

# A boolean getter keeps its `is` prefix

<DrawnAnnotation text="val open = value.isOpen" label="The property name matches the getter, `is` included" :connect="false" :geometry="{ label: { x: 0.5928, y: 0.5729 } }"/>
<DrawnAnnotation text="value.isOpen = true" label="The setter still works through the same property" color="var(--code-window-amper-color)" :connect="false" :geometry="{ label: { x: 0.5341, y: 0.6514 } }"/>

```java
public class Connection {
  private boolean open;

  public boolean isOpen() { return open; }
  public void setOpen(boolean open) { this.open = open; }
}
```

```kotlin
fun example(value: Connection) {
  val open = value.isOpen
  value.isOpen = true
}
```

---

# Initializers run in declaration order

<DrawnAnnotation text="init {" label="Runs as part of the primary constructor" :connect="false" :geometry="{ label: { x: 0.3434, y: 0.3903 } }"/>
<DrawnAnnotation text="require(age >= 0)" label="Validate before the invalid instance 'escapes'" color="var(--code-window-amper-color)" :connect="false" :geometry="{ label: { x: 0.2972, y: 0.4976 } }"/>

```kotlin
class Person(name: String, age: Int) {
  val name = name.lowercase()
  val age = age

  init {
    require(age >= 0) { "Age cannot be negative" }
  }
}
```

---

# Member functions see the instance

<DrawnAnnotation text="fun introduce" :connect="false"/>
<DrawnAnnotation text="$name" :connect="false"/>
<DrawnAnnotation text="person.introduce" label="Member functions are called on an instance and see its properties" :connect="false"/>

```kotlin
class Person(val name: String, var age: Int) {
  fun introduce(to: String) {
    println("Hello $to, my name is $name.")
  }
}

val person = Person("Olivia", 42)
person.introduce(to = "Kotlin")
// Hello Kotlin, my name is Olivia.
```

---

# Setters are customizable

```kotlin
class TextBuffer {
  var text = ""
}
```

---
magic-move
---

# Setters are customizable

<DrawnAnnotation text="field += value" label="`field` is the property's backing field" :geometry="{ label: { x: 0.3516, y: 0.4172 } }" :connect="false"/>

```kotlin
class TextBuffer {
  var text = ""
    set(value) {
      field += value
    }
}
```

---
magic-move
---

# Setters are customizable

<DrawnAnnotation text="println(buffer.text)" label="Every write went through the setter" :geometry="{ label: { x: 0.2707, y: 0.7476 } }" :connect="false"/>

```kotlin
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

---

# A getter can compute the value

<DrawnAnnotation text="get()" label="A custom getter runs on every access" on="0" :geometry="{ label: { x: 0.2431, y: 0.5099 } }" :connect="false"/>
<DrawnAnnotation text="text.isEmpty()" label="No backing field when the getter never reads `field`" on="1" :geometry="{ label: { x: 0.4171, y: 0.5199 } }" :connect="false"/>

```kotlin
class TextBuffer {
  var text = ""

  val isEmpty: Boolean
    get() = text.isEmpty()
}
```

---
magic-move
---

# A getter can compute the value

<DrawnAnnotation text="println(buffer.isEmpty) // false" label="The value follows `text`" :connect="false"/>

```kotlin
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

---

# Expose the read, keep the write

<DrawnAnnotation text="private set" :connect="false"/>
<DrawnAnnotation text="isOpen = true" label="Only the class itself can write it" :connect="false" :geometry="{ label: { x: 0.3217, y: 0.5135 } }"/>

```kotlin
class Connection {
  var isOpen: Boolean = false
    private set

  fun open() {
    isOpen = true
  }
}
```

---
magic-move
---

# Expose the read, keep the write

<DrawnAnnotation text="connection.isOpen" label="Anyone can read it" :geometry="{ label: { x: 0.5556, y: 0.6651 }, connector: { type: 'quadratic', start: { x: 0.3372, y: 0.6939 }, control: { x: 0.3481, y: 0.7020 }, end: { x: 0.3600, y: 0.7008 } } }" :connect="false"/>
<InlineCompilerError occurrence="5" text="isOpen" message="Cannot access 'isOpen': it is private">

```kotlin
class Connection {
  var isOpen: Boolean = false
    private set

  fun open() {
    isOpen = true
  }
}

val connection = Connection()
println(connection.isOpen) // false
connection.open()
println(connection.isOpen) // true
connection.isOpen = false
```

</InlineCompilerError>

---

# An `object` is a single instance

> One shared instance, not a home for mutable global state

<DrawnAnnotation text="object" label="Define a single, named instance" :connect="false"/>

```kotlin
object Environment {
  var current = "production"
}
```

---
magic-move
---

# An `object` is a single instance

> One shared instance, not a home for mutable global state

<DrawnAnnotation text="Environment.current" label="Access its members without constructing anything" :connect="false"/>

```kotlin
object Environment {
  var current = "production"
}

println(Environment.current) // production
```

---

# An `object` is a single instance

> One shared instance, not a home for mutable global state

<DrawnAnnotation text="@JvmStatic" label="Without `@JvmStatic` Kotlin generates a method on a static `INSTANCE` singleton" :connect="false" :geometry="{ label: { x: 0.5496, y: 0.4675 } }"/>

```kotlin
object Environment {
    @JvmStatic
    var current = "production"
}

println(Environment.current) // production
```

---

# Data classes generate the boilerplate

<DrawnAnnotation text="data class" :connect="false"/>
<DrawnAnnotation text="user == User(&quot;Ada&quot;, 36)" label="`equals`, `hashCode` and `toString` for free" :connect="false"/>

```kotlin
data class User(val name: String, val age: Int)

val user = User("Ada", 36)

println(user)                    // User(name=Ada, age=36)
println(user.hashCode())         // 2035558
println(user == User("Ada", 36)) // true
```

---
magic-move
---

# Data classes generate the boilerplate

<DrawnAnnotation text="copy" label="A new instance with some properties replaced" :connect="false"/>

```kotlin
data class User(val name: String, val age: Int)

val user = User("Ada", 36)

println(user)                    // User(name=Ada, age=36)
println(user.hashCode())         // 2035558
println(user == User("Ada", 36)) // true

val newValue = user.copy(age = 37)
println(newValue)                // User(name=Ada, age=37)
println(newValue.hashCode())     // 2035559
println(user == newValue)        // false
```

---

# Avoid primitive obsession

> A primitive is not always enough

<DrawnAnnotation text="Long" color="red"/>
<DrawnAnnotation text="Long" occurrence="2" label="Both IDs are `Long`, so they are easy to mix up" color="red" :connect="false" :geometry="{ label: { x: 0.4928, y: 0.6317 } }"/>
<DrawnAnnotation text="String" color="red" :connect="false" />
<DrawnAnnotation text="String" occurrence="2" label=" name and email are both `String` also easy to mix up" color="red" :connect="false"  :geometry="{ label: { x: 0.5320, y: 0.6767 } }"/>

```kotlin
data class Employee(
  val id: Long,
  val departmentId: Long,
  val name: String,
  val email: String,
)
```

<!--
Primitive obsession means using generic primitives such as String, Int, or Long
for domain concepts. The compiler cannot distinguish the two IDs because they
have the same underlying primitive.
-->

---

# Give a value a domain type

<DrawnAnnotation text="value class" label="A distinct Kotlin type around one value" :connect="false" :geometry="{ label: { x: 0.6780, y: 0.2487 } }"/>

```kotlin
@JvmInline
value class EmployeeId(val value: Long)

@JvmInline
value class DepartmentId(val value: Long)

@JvmInline
value class Email(val value: String)

@JvmInline
value class Name(val value: String)
```

---

# Give a value a domain type

```kotlin
data class Employee(
  val id: EmployeeId,
  val departmentId: DepartmentId,
  val name: Name,
  val email: Email
)
```

---

# Give a value a domain type

<InlineCompilerError text="id =" message="Expected EmployeeId found DepartmentId">
<InlineCompilerError text="departmentId =" message="Expected DepartmentId found EmployeeId">

```kotlin
Employee(
    id = DepartmentId(7),
    departmentId = EmployeeId(1),
    name = Name("Simon"),
    email = Email("simon.vergauwen@jetbrains.com")
)
```

</InlineCompilerError>
</InlineCompilerError>

---

# Give a value a domain type

```kotlin
Employee(
    id = EmployeeId(1),
    departmentId =  DepartmentId(7),
    name = Name("Simon"),
    email = Email("simon.vergauwen@jetbrains.com")
)
```

---

# Give a value a domain type

```kotlin
@JvmInline
value class Email(val value: String) {
    init {
        require(value.contains("@")) { "Not a valid email" }
    }
}
```

---

# Positional destructuring is fragile

<DrawnAnnotation text="(username, email)" label="Components are matched by position" :connect="false" :geometry="{ label: { x: 0.4291, y: 0.5808 } }"/>

```kotlin no-compile
data class User(val username: String, val email: String)
val user = User("alice", "alice@example.com")

val (username, email) = user

println(username) // alice
println(email)    // alice@example.com
```

---
magic-move
---

# Positional destructuring is fragile

<DrawnAnnotation text="(username, email)" label="A new property in the middle silently changes `email`" color="red" :connect="false" :geometry="{ label: { x: 0.4132, y: 0.5789 } }"/>

```kotlin no-compile
data class User(val username: String, val displayName: String, val email: String)
val user = User("alice", "kotliner", "alice@example.com")

val (username, email) = user

println(username) // alice
println(email)    // kotliner
```

---

# Name-based destructuring is safe

> Preview in Kotlin 2.4

<DrawnAnnotation text="email" :occurrence="2" :connect="false"/>
<DrawnAnnotation text="email" :occurrence="3" label="Destructured by name, not by position" :connect="false" :geometry="{ label: { x: 0.4035, y: 0.6797 } }"/>

```kotlin no-compile
data class User(val username: String, val displayName: String, val email: String)
val user = User("alice", "kotliner", "alice@example.com")

val (username, email) = user

println(username) // alice
println(email)    // alice@example.com
```

---
magic-move
---

# Name-based destructuring is safe

> Preview in Kotlin 2.4

<DrawnAnnotation text="[name, mail]" label="Square brackets are explicitly positional, risky but useful for collections" :connect="false" :geometry="{ label: { x: 0.5032, y: 0.6776 } }"/>

```kotlin no-compile
data class User(val username: String, val displayName: String, val email: String)
val user = User("alice", "kotliner", "alice@example.com")

val [name, mail] = user

println(name) // alice
println(mail) // alice@example.com
```

---
magic-move
---

# Name-based destructuring is safe

> Preview in Kotlin 2.4

<DrawnAnnotation text="(val name = username, val mail = email)" label="Prefer renaming name-based components explicitly" :connect="false" :geometry="{ label: { x: 0.3494, y: 0.6754 } }"/>

```kotlin no-compile
data class User(val username: String, val displayName: String, val email: String)
val user = User("alice", "kotliner", "alice@example.com")

(val name = username, val mail = email) = user

println(name) // alice
println(mail) // alice@example.com
```

---

# Interfaces define capabilities

<DrawnAnnotation text="fun draw() =" label="A default implementation" :connect="false" on="0" :geometry="{ label: { x: 0.3386, y: 0.3941 } }"/>
<DrawnAnnotation text="val sides: Int" :connect="false" on="1" />
<DrawnAnnotation text="override val sides" label="Implementers provide `sides`, the interface has no backing field" :connect="false" on="1" :geometry="{ label: { x: 0.7193, y: 0.5072, width: 0.3825 } }"/>

```kotlin
interface Shape {
  val sides: Int

  fun draw() = println("Drawing a $sides-sided shape")
}

class Polygon(override val sides: Int) : Shape

Polygon(6).draw() // Drawing a 6-sided shape
```

---

# A class implements many interfaces

<InlineCompilerError
    text="class Button"
    message="Button must override click, it inherits multiple interface methods"
    style="--inline-compiler-error-message-size: 1.42rem"
>

```kotlin
interface Clickable {
  fun click() = println("clicked")
}

interface Focusable {
  fun click() = println("focused")
}

class Button : Clickable, Focusable
```

</InlineCompilerError>

---
magic-move
---

# A class implements many interfaces

<DrawnAnnotation text="super<Clickable>" :connect="false"/>
<DrawnAnnotation text="super<Focusable>" label="`super<T>` picks which default implementation to call" :connect="false" :geometry="{ label: { x: 0.4040, y: 0.8003 } }"/>

```kotlin
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

---

# Sealed classes close a hierarchy

<DrawnAnnotation text="sealed class" label="Sealed hierarchy, all subclasses are known and live in the same module & package" :connect="false" :geometry="{ label: { x: 0.6829, y: 0.2198, width: 0.4449 } }"/>

```kotlin
sealed class UiState {
  data object Loading : UiState()
  data class Content(val text: String) : UiState()
  data class Error(val message: String) : UiState()
}
```

---
magic-move
---

# Sealed classes close a hierarchy

<DrawnAnnotation text="when (state)" label="Exhaustive without `else` since all subtypes are known" :connect="false"/>

```kotlin
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

---
magic-move
---

# Sealed classes close a hierarchy

<InlineCompilerError text="when (state) {" message="'when' expression must be exhaustive.\nAdd the 'is Error' branch or an 'else' branch">

```kotlin
sealed class UiState {
  data object Loading : UiState()
  data class Content(val text: String) : UiState()
  data class Error(val message: String) : UiState()
}

fun message(state: UiState) = when (state) {
  UiState.Loading -> "Loading…"
  is UiState.Content -> state.text
}
```

</InlineCompilerError>

---
magic-move
---

# Sealed classes close a hierarchy

<SmartCast text="state" occurrence="3">
<SmartCast text="state" occurrence="4">
<DrawnAnnotation text="if state.text.contains(&quot;error&quot;)" label="Match on inner values. Smart-casting is applied in if-guard expression" :connect="false" />

```kotlin
sealed class UiState {
  data object Loading : UiState()
  data class Content(val text: String) : UiState()
  data class Error(val message: String) : UiState()
}

fun message(state: UiState) = when (state) {
  UiState.Loading -> "Loading…"
  is UiState.Content if state.text.contains("error") -> "Unknown error"
  is UiState.Content -> state.text
  is UiState.Error -> "Error: ${state.message}"
}
```

</SmartCast>
</SmartCast>

---

# Enums are a fixed set of values

```kotlin
enum class Direction { NORTH, SOUTH, EAST, WEST }
```

---
magic-move
---

# Enums are a fixed set of values

<DrawnAnnotation text="enum" :connect="false"/>
<DrawnAnnotation text="when (direction)" :connect="false"/>
<DrawnAnnotation text="Direction.WEST -> &quot;←&quot;" label="Exhaustive, every entry is known" :connect="false" :geometry="{ label: { x: 0.4462, y: 0.6996 } }"/>

```kotlin
enum class Direction { NORTH, SOUTH, EAST, WEST }

val direction = Direction.NORTH

fun arrow(direction: Direction) = when (direction) {
  Direction.NORTH -> "↑"
  Direction.SOUTH -> "↓"
  Direction.EAST -> "→"
  Direction.WEST -> "←"
}
```

---

# Enums are a fixed set of values

<DrawnAnnotation text="direction == NORTH || direction == SOUTH" />
<DrawnAnnotation text="when (direction)" label="Still exhaustive, compiler accumulates conditions"  :connect="false" :geometry="{ label: { x: 0.6065, y: 0.5611 } }"/>

```kotlin
enum class Direction { NORTH, SOUTH, EAST, WEST }

val direction = Direction.NORTH

fun arrow(direction: Direction) {
  if (direction == NORTH || direction == SOUTH) return
  
  when (direction) {
    Direction.EAST -> "→"
    Direction.WEST -> "←"
  }
}
```

---

# Enum entries carry data

<DrawnAnnotation text="val code: Int" :connect="false"/>
<DrawnAnnotation text="HttpStatus.entries" label="Iterate every entry, `valueOf(&quot;OK&quot;)` looks one up by name" :connect="false"/>

```kotlin
enum class HttpStatus(val code: Int) {
  OK(200),
  NOT_FOUND(404),
  SERVER_ERROR(500),
}

for (status in HttpStatus.entries) {
  println("${status.name}: ${status.code}")
  check(HttpStatus.valueOf(status.name) == status)
}
```

---

# Context-sensitive resolution drops the qualifier

> Experimental Kotlin 2.2.0 `-Xcontext-sensitive-resolution`

<DrawnAnnotation text="CONNECTION ->" label="`Problem.` is implied by the subject" :connect="false" :geometry="{ label: { x: 0.2598, y: 0.6856 } }"/>

```kotlin
enum class Problem { CONNECTION, AUTHENTICATION, DATABASE }

fun message(problem: Problem): String = when (problem) {
  CONNECTION -> "Connection failed"
  AUTHENTICATION -> "Authentication failed"
  DATABASE -> "Database failed"
}
```

---

# Choose the closed type deliberately

> Model types as precisely as you can
> 
> Choose the closed type deliberately

| Type | Best for |
| --- | --- |
| `enum class` | A fixed set of named, usually stateless values |
| `sealed class` | A fixed family of types that may hold different data or state |
| `sealed interface` | A fixed family of alternatives that can have unrelated class hierarchies |
| `interface` | An open capability that new implementations may add later |

<style>
.slidev-layout table {
    font-size: 1.9rem;
}
</style>
