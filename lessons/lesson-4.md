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

---

# `Nothing` or bottom type

<DrawnAnnotation text="Nothing" label="No values, can stand in for any return type because it never returns"  :geometry="{ label: { x: 0.3768, y: 0.2768 } }"/>

```kotlin
fun TODO(): Nothing = throw NotImplementedError()
```

---

# `Nothing` or bottom type

<DrawnAnnotation text="Nothing" />
<DrawnAnnotation text="Unit = TODO()" label="Nothing infers to _any_ type because it has _no value_"  :geometry="{ label: { x: 0.3118, y: 0.5616 } }"/>

```kotlin
fun TODO(): Nothing = throw NotImplementedError()

val int: Int = TODO()
val string: String = TODO()
val bool: Boolean = TODO()

fun program(): Unit = TODO()
```

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

<DrawnAnnotation text="class Person" label="No body and no explicit constructor" :geometry="{ label: { x: 0.2409, y: 0.2793 } }"/>

```kotlin
class Person
```

---
magic-move
---

# A class needs no body

<DrawnAnnotation text="Person()" label="No `new`, call the constructor like a function" :geometry="{ label: { x: 0.3398, y: 0.3727 } }"/>

```kotlin
class Person

val person = Person()
```

---
magic-move
---

# The primary constructor is the class header

<DrawnAnnotation text="constructor" label="The `constructor` keyword is optional when there are no modifiers or annotations" :geometry="{ label: { x: 0.4865, y: 0.3855 } }"/>

```kotlin
class Person constructor()

val person = Person()
```

---
magic-move
---

# The primary constructor is the class header

```kotlin
class Person @MyAnnotation internal constructor()

val person = Person()
```

---
magic-move
---

# The primary constructor is the class header

```kotlin
class Person(name: String, age: Int) {
  val name: String
  var age: Int
  
  init {
      this.name = name
      this.age = age
  }
}
val person = Person("Olivia", 42)
```

---
magic-move
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

<DrawnAnnotation text="val name: String" label="Constructor parameters can declare properties directly" :geometry="{ label: { x: 0.3455, y: 0.3670 } }"/>

```kotlin
class Person(val name: String, var age: Int)

val person = Person("Olivia", 42)
```

---

# Properties become getters and setters in Java

<DrawnAnnotation text="getName();" label="A public Kotlin `val` exposes a Java getter" :geometry="{ label: { x: 0.5895, y: 0.4373 } }"/>
<DrawnAnnotation text="setAge(34);" label="A public Kotlin `var` exposes a getter and a setter" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.3597, y: 0.5056 } }"/>

```kotlin
class Person(val name: String, var age: Int)
```

```java
public class JavaUsage {
  void example(Person value) {
    String name = value.getName();
    value.setAge(34);
  }
}
```

---
magic-move
---

# `private set` keeps the write inside the class

<DrawnAnnotation text="private set" label="Only `Person` writes it, `birthday()` is the API" :geometry="{ label: { x: 0.45, y: 0.29 } }"/>
<DrawnAnnotation text="value.getAge();" label="From Java there is only a getter" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.6, y: 0.705 } }"/>

```kotlin
class Person(val name: String, age: Int) {
  var age: Int = age
    private set

  fun birthday() {
    age++
  }
}
```

<InlineCompilerError text="setAge(34)" message="setAge(int) has private access in Person">

```java
public class JavaUsage {
  void example(Person value) {
    int age = value.getAge();
    value.birthday();
    value.setAge(34);
  }
}
```

</InlineCompilerError>

---

# Getters and setters become properties in Kotlin

<DrawnAnnotation text="value.name" label="A Java getter is read as a property" :geometry="{ label: { x: 0.5089, y: 0.6692 } }"/>
<DrawnAnnotation text="value.age = 34" label="A getter and setter pair becomes a mutable property" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.5145, y: 0.7538 } }"/>

```java
public class Person {
  private String name;
  private int age;

  public String getName() { return name; }
  public int getAge() { return age; }
  public void setAge(int age) { this.age = age; }
}
```

```kotlin
fun example(value: Person) {
  val name = value.name
  value.age = 34
}
```

---

# A boolean getter keeps its `is` prefix

<DrawnAnnotation text="val allowed = value.isAllowed" label="The property name matches the getter, `is` included" :geometry="{ label: { x: 0.6452, y: 0.5925 } }"/>
<DrawnAnnotation text="value.isAllowed = true" label="The setter still works through the same property" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.5455, y: 0.6517 } }"/>

```java
public class Person {
  private boolean allowed;

  public boolean isAllowed() { return allowed; }
  public void setAllowed(boolean allowed) { this.allowed = allowed; }
}
```

```kotlin
fun example(value: Person) {
  val allowed = value.isAllowed
  value.isAllowed = true
}
```

---

# Initializers run in declaration order

<DrawnAnnotation text="init {" label="Runs as part of the primary constructor" :geometry="{ label: { x: 0.3356, y: 0.3366 } }"/>
<DrawnAnnotation text="require(age >= 0)" label="Validate before the invalid instance 'escapes'" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.3299, y: 0.4504 } }"/>

```kotlin
class Person(name: String, val age: Int) {
  val name = name.lowercase()
    
  init {
    require(age >= 0) { "Age cannot be negative" }
  }
}
```

---

# Initializers run in declaration order

<DrawnAnnotation text="constructor" />
<DrawnAnnotation text=": this(" label="Define secondary constructors. Must call `this(...)`."  :geometry="{ label: { x: 0.3790, y: 0.3929 } }"/>

```kotlin
class Person(name: String, val age: Int) {
  val name = name.lowercase()
  
  constructor(member: Member): this(member.name, member.age)

  init {
    require(age >= 0) { "Age cannot be negative" }
  }
}
```

---

# Initializers run in declaration order

<DrawnAnnotation text="require(member.name.isNotBlank())" label="Can include an initializer that runs **after** all super init"  :geometry="{ label: { x: 0.4542, y: 0.4661 } }"/>

```kotlin
class Person(name: String, val age: Int) {
  val name = name.lowercase()
  
  constructor(member: Member): this(member.name, member.age) {
      require(member.name.isNotBlank()) { "A person needs a name" }
  }

  init {
    require(age >= 0) { "Age cannot be negative" }
  }
}
```

---

# Member functions

<DrawnAnnotation text="println(&quot;Hello $to, my name is $name.&quot;)" label="Access to class and function state"  :geometry="{ label: { x: 0.5316, y: 0.3614 } }"/>

```kotlin
class Person(val name: String, var age: Int) {
  fun introduce(to: String) {
    println("Hello $to, my name is $name.")
  }
}

val person = Person("Olivia", 42)
person.introduce(to = "Kotlin")
```
```console
Hello Kotlin, my name is Olivia.
```

---

# Nested classes

<DrawnAnnotation text="class Nested" label="Has no access to the `Outer` class instance it's a `static inner`"  :geometry="{ label: { x: 0.3807, y: 0.4246 } }"/>

```kotlin
class Outer {

  class Nested
}
```

---
magic-move
---

# Nested classes

<DrawnAnnotation text="Outer.Nested" label="Fully qualified name is also _nested_ so prefix with outer classes"  :geometry="{ label: { x: 0.3882, y: 0.5137 } }"/>

```kotlin
class Outer {

  class Nested
}

val nested = Outer.Nested()
```

---
magic-move
---

# Nested classes

<DrawnAnnotation text="inner" label="An `inner` class can access the `Outer` class instance"  :geometry="{ label: { x: 0.3259, y: 0.5648 } }"/>

```kotlin
class Outer {
    val name = "Outer"

  inner class Nested {
      val fullName = "$name.Inner"
  }
}
```

---
magic-move
---

# Nested classes

<DrawnAnnotation text="outerClass." label="To create an inner class we need an instance of the outer class"  :geometry="{ label: { x: 0.3791, y: 0.7035 } }"/>

```kotlin
class Outer {
    val name = "Outer"

  inner class Nested {
      val fullName = "$name.Inner"
  }
}

val outerClass = Outer()
val innerClass = outerClass.Nested()
```

---

# Data

- In Java known as 'POJO'. Lombok, AutoValue, Immutables, ...
- Prefer `val` and immutability
- Represents a set of named properties
- Equals, hash, toString
- Way to _update_ immutable data (Java builders)

---

# Data classes generate the boilerplate

```kotlin
data class Person(val name: String, val age: Int)
```

---

# Data class requirements

<InlineCompilerError text="()" message="Data class must have at least one primary constructor parameter">

```kotlin
data class Tags()
```

</InlineCompilerError>

---

# Data class requirements

<InlineCompilerError text="key: String" message="Primary constructor must only have property val/var parameters" style="--inline-compiler-error-message-size: 1.55rem">

```kotlin
data class Tags(key: String)
```

</InlineCompilerError>

---

# Data class requirements

<InlineCompilerError text="vararg tags: String" message="Primary constructor vararg parameters are prohibited for data classes">

```kotlin
data class Tags(
    val key: String,
    vararg tags: String
)
```

</InlineCompilerError>

---

# Data class requirements

<Warning text="val tags: Array<String>" message="Property with Array type in a data class\nit is recommended to override equals() and hashCode() ">

```kotlin
data class Tags(
    val key: String,
    val tags: Array<String>
)
```

</Warning>

---

# Data class requirements

<DrawnAnnotation text="equals" />
<DrawnAnnotation text="hashCode" label="Explicitly specify Array behavior"  :geometry="{ label: { x: 0.3835, y: 0.4132 } }"/>

```kotlin
data class Tags(val key: String, val tags: Array<String>) {
    override fun equals(other: Any?): Boolean = TODO()
    override fun hashCode(): Int = TODO()
}
```

---

# `toString()`


```kotlin
data class User(val name: String, val age: Int)

val user = User("Ada", 36)
println(user)                  
```
```console
User(name=Ada, age=36)
```

---
magic-move
---

# `hashCode()`

<DrawnAnnotation text="hashCode()" label="Important for `HashMap`, `HashSet`, ..."/>

```kotlin
data class User(val name: String, val age: Int)

val user = User("Ada", 36)
println(user.hashCode())        
```
```console
2035558
```

---
magic-move
---

# `equals(other)`

<DrawnAnnotation text="==" label="Comparison of properties relies on properties to properly implement equals\nThis is why override for Array is required"  :geometry="{ label: { x: 0.4687, y: 0.5167 } }"/>

```kotlin
data class User(val name: String, val age: Int)

val user = User("Ada", 36)
println(user == user)        
```
```console
true
```

---
magic-move
---

# `copy(...)`

<DrawnAnnotation text="copy" label="Compiler generated method to `copy` an instance with new values" :geometry="{ label: { x: 0.5055, y: 0.3508 } }"/>
<DrawnAnnotation text="age = " label="Relies on argument names, and default values"  :geometry="{ label: { x: 0.4308, y: 0.7371 } }"/>

```kotlin no-compile
data class User(val name: String, val age: Int) {
    fun copy(name: String = this.name, age: Int = this.age): User {
        return User(name, age)
    }
}

val user = User("Ada", 36)
val newValue = user.copy(age = 37)
println(user == newValue)        
```
```console
false
```

---
magic-move
---

# A dropped `copy` changes nothing

> `-Xreturn-value-checker=full`

<Warning :line="4" text="user.copy(age = 37)" message="Unused return value of 'copy'.">

```kotlin
data class User(val name: String, val age: Int)

val user = User("Ada", 36)
user.copy(age = 37)
println(user)
```
```console
User(name=Ada, age=36)
```

</Warning>

---

# A result that is safe to drop opts out

<DrawnAnnotation text="@IgnorableReturnValue" label="`add` returns `this` for chaining, dropping it is intended" :geometry="{ label: { x: 0.64, y: 0.24 } }"/>
<DrawnAnnotation text="counter.add(1)" :line="10"/>

```kotlin
class Counter(var total: Int = 0) {
  @IgnorableReturnValue
  fun add(value: Int): Counter {
    total += value
    return this
  }
}

val counter = Counter()
counter.add(1)
counter.add(2).add(3)
println(counter.total)
```
```console
6
```

---

# Java libraries opt in with `@CheckReturnValue`

> Error Prone, FindBugs, Spring and jOOQ ship one too

<DrawnAnnotation text="@CheckReturnValue" label="Read as `@MustUseReturnValues`, unannotated Java is never checked" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.60, y: 0.307 } }"/>

```java
import org.jetbrains.annotations.CheckReturnValue;

@CheckReturnValue
public class Money {
  private final long cents;

  public Money(long cents) { this.cents = cents; }

  public Money plus(Money other) { return new Money(cents + other.cents); }
}
```

<Warning :line="2" text="price.plus(Money(250))" message="Unused return value of 'plus'.">

```kotlin
val price = Money(1000)
price.plus(Money(250))
```

</Warning>

<!--
Even in `full` mode the checker only treats the functions of your own module as
must-use. A Java library has to say so itself, and the checker reads these
annotations as `@MustUseReturnValues`:

- `com.google.errorprone.annotations.CheckReturnValue`
- `edu.umd.cs.findbugs.annotations.CheckReturnValue`
- `org.jetbrains.annotations.CheckReturnValue`
- `org.springframework.lang.CheckReturnValue`
- `org.jooq.CheckReturnValue`

`com.google.errorprone.annotations.CanIgnoreReturnValue` is read as
`@IgnorableReturnValue`.
-->

---

# Avoid primitive obsession

> A primitive is not always enough

<DrawnAnnotation text="Long" color="red"/>
<DrawnAnnotation text="Long" occurrence="2" label="Both IDs are `Long`, so they are easy to mix up" color="red" :geometry="{ label: { x: 0.4928, y: 0.6317 } }"/>
<DrawnAnnotation text="String" color="red" />
<DrawnAnnotation text="String" occurrence="2" label=" name and email are both `String` also easy to mix up" color="red"  :geometry="{ label: { x: 0.5320, y: 0.6767 } }"/>

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

<DrawnAnnotation text="value class" label="A distinct Kotlin type around one value" :geometry="{ label: { x: 0.6780, y: 0.2487 } }"/>

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

# Operators keep the domain type

<DrawnAnnotation text="plus(other: Money)" label="`Money + Money` stays `Money`" :geometry="{ label: { x: 0.55, y: 0.48 } }" />
<DrawnAnnotation text="times(factor: Int)" label="Operands need not share a type" :geometry="{ label: { x: 0.55, y: 0.525 } }" />

```kotlin
@JvmInline
value class Money(val cents: Long) {
  operator fun plus(other: Money): Money = Money(cents + other.cents)
  operator fun times(factor: Int): Money = Money(cents * factor)
}

Money(250) + Money(199)
Money(250) * 3
```
```console
Money(cents=449)
Money(cents=750)
```

---

# `<` needs a `compareTo`

<InlineCompilerError :line="8" text="<" message="Unresolved reference 'compareTo' on receiver of type 'Money'">

```kotlin
@JvmInline
value class Money(val cents: Long) {
  operator fun plus(other: Money): Money = Money(cents + other.cents)
  operator fun times(factor: Int): Money = Money(cents * factor)
}

Money(250) + Money(199)
Money(250) < Money(199)
```

</InlineCompilerError>

---
magic-move
---

# `<` needs a `compareTo`

<DrawnAnnotation text="operator fun compareTo" label="Delegate to the `compareTo` of the wrapped value" :geometry="{ label: { x: 0.55, y: 0.48 } }" />

```kotlin
@JvmInline
value class Money(val cents: Long) {
  operator fun plus(other: Money): Money = Money(cents + other.cents)
  operator fun times(factor: Int): Money = Money(cents * factor)
  operator fun compareTo(other: Money): Int = cents.compareTo(other.cents)
}

Money(250) + Money(199)
Money(250) < Money(199)
```
```console
Money(cents=449)
false
```

---

# `Comparable` unlocks sorting

<InlineCompilerError :line="8" text="sorted" message="fun <T : Comparable<T>> Iterable<T>.sorted(): List<T> is inapplicable because of a receiver type mismatch" style="--inline-compiler-error-message-size: 1.25rem">

```kotlin
@JvmInline
value class Money(val cents: Long) {
  operator fun compareTo(other: Money): Int = cents.compareTo(other.cents)
}

val prices = [Money(250), Money(199), Money(999)]

prices.sorted()
```

</InlineCompilerError>

---
magic-move
---

# `Comparable` unlocks sorting

<DrawnAnnotation text=": Comparable<Money>" label="`sorted`, `max`, `coerceIn` and friends now apply to `Money`" :geometry="{ label: { x: 0.62, y: 0.375 } }" />
<DrawnAnnotation text="override fun compareTo" label="`Comparable.compareTo` is already an operator" :geometry="{ label: { x: 0.38, y: 0.80 } }" />

```kotlin
@JvmInline
value class Money(val cents: Long) : Comparable<Money> {
  override fun compareTo(other: Money): Int = cents.compareTo(other.cents)
}

val prices = [Money(250), Money(199), Money(999)]

prices.sorted()
prices.max()
```
```console
[Money(cents=199), Money(cents=250), Money(cents=999)]
Money(cents=999)
```

---

# Positional destructuring is fragile

<DrawnAnnotation text="(username, email)" label="Components are matched by position" :geometry="{ label: { x: 0.4291, y: 0.5808 } }"/>

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

<DrawnAnnotation text="(username, email)" label="A new property in the middle silently changes `email`" color="red" :geometry="{ label: { x: 0.4132, y: 0.5789 } }"/>

```kotlin no-compile
data class User(val username: String, val displayName: String, val email: String)
val user = User("alice", "kotliner", "alice@example.com")

val (username, email) = user

println(username) // alice
println(email)    // kotliner
```

---

# Name-based destructuring is safe

> `-Xname-based-destructuring=complete` · preview in Kotlin 2.4

<DrawnAnnotation text="email" :occurrence="2"/>
<DrawnAnnotation text="email" :occurrence="3" label="Destructured by name, not by position" :geometry="{ label: { x: 0.4035, y: 0.6797 } }"/>

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

> `-Xname-based-destructuring=complete` · preview in Kotlin 2.4

<DrawnAnnotation text="[name, mail]" label="Square brackets are explicitly positional, risky but useful for collections" :geometry="{ label: { x: 0.5032, y: 0.6776 } }"/>

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

> `-Xname-based-destructuring=complete` · preview in Kotlin 2.4

<DrawnAnnotation text="(val name = username, val mail = email)" label="Prefer renaming name-based components explicitly" :geometry="{ label: { x: 0.3494, y: 0.6754 } }"/>

```kotlin no-compile
data class User(val username: String, val displayName: String, val email: String)
val user = User("alice", "kotliner", "alice@example.com")

(val name = username, val mail = email) = user

println(name) // alice
println(mail) // alice@example.com
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 3</div>

# Declare `Member`

> A data class with `name` and `age`, equality and `toString()` come for free

```kotlin no-compile
check(Member("Ada", 36) == Member("Ada", 36))
check(Member("Ada", 36).toString() == "Member(name=Ada, age=36)")
check(Member("Ada", 36).copy(age = 37) == Member("Ada", 37))
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 3</div>

# Give `age` a domain type

> A `value class` around an `Int`, its `init` block `require`s `0` or more

```kotlin no-compile
check(Age(36).value == 36)
check(Age(36) == Age(36))
check(runCatching { Age(-1) }.isFailure)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 3</div>

# Make `Age` sortable

> `Comparable<Age>`, delegate `compareTo` to the wrapped `Int`

```kotlin no-compile
check(Age(17) < Age(36))
check([Age(36), Age(17), Age(25)].sorted() == [Age(17), Age(25), Age(36)])
```

---

# An `object` is a single instance

> One shared instance, not a home for mutable global state

<DrawnAnnotation text="object" label="Define a single, named instance"/>

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

<DrawnAnnotation text="Environment.current" label="Access its members without constructing anything"/>

```kotlin
object Environment {
  var current = "production"
}

println(Environment.current) // production
```

---

# An `object` is a single instance

> One shared instance, not a home for mutable global state

<DrawnAnnotation text="@JvmStatic" label="Without `@JvmStatic` Kotlin generates a method on a static `INSTANCE` singleton" :geometry="{ label: { x: 0.5496, y: 0.4675 } }"/>

```kotlin
object Environment {
    @JvmStatic
    var current = "production"
}

println(Environment.current) // production
```

---

# Object expressions

<DrawnAnnotation text="object : EventHandler()" label="Create an anonymous class" />

```kotlin
client.addEventHandler(object : EventHandler() {
    override fun onEvent(event: Event): Unit {
        println("Event received: $event")
    }
})
```

---

# Companion object

```kotlin
data class Vector(val x: Double, val y: Double) {
    companion object {
        val Zero: Vector = Vector(0.0, 0.0)
    }
}

val zero = Vector.Zero
```

---
magic-move
---

# The companion is the only door

<DrawnAnnotation text="fun of(" label="Validate, then construct" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.8, y: 0.525 } }"/>
<Warning text="private constructor" message="Private primary constructor is exposed\nvia the generated 'copy()' method of a 'data' class.">

```kotlin
data class Vector private constructor(
    val x: Double,
    val y: Double,
) {
    companion object {
        val Zero: Vector = Vector(0.0, 0.0)

        fun of(x: Double, y: Double): Vector {
            require(x.isFinite() && y.isFinite()) { "Vector must be finite" }
            return Vector(x, y)
        }
    }
}

val unit = Vector.of(1.0, 0.0)
```

</Warning>

---
magic-move
---

# The companion is the only door

<DrawnAnnotation text="@ConsistentCopyVisibility" label="`copy()` takes the constructor's visibility, no back door" :geometry="{ label: { x: 0.65, y: 0.196 } }"/>

```kotlin
@ConsistentCopyVisibility
data class Vector private constructor(
    val x: Double,
    val y: Double,
) {
    companion object {
        val Zero: Vector = Vector(0.0, 0.0)

        fun of(x: Double, y: Double): Vector {
            require(x.isFinite() && y.isFinite()) { "Vector must be finite" }
            return Vector(x, y)
        }
    }
}

val unit = Vector.of(1.0, 0.0)
```

---
magic-move
---

# The companion is the only door

<InlineCompilerError text="Vector(Double.NaN, 0.0)" message="Cannot access 'constructor': it is private in 'Vector'">
<InlineCompilerError text="copy(x = Double.NaN)" message="Cannot access 'copy': it is private in 'Vector'">

```kotlin
@ConsistentCopyVisibility
data class Vector private constructor(
    val x: Double,
    val y: Double,
) {
    companion object {
        fun of(x: Double, y: Double): Vector {
            require(x.isFinite() && y.isFinite()) { "Vector must be finite" }
            return Vector(x, y)
        }
    }
}

val broken = Vector(Double.NaN, 0.0)

val copied = Vector.of(1.0, 0.0).copy(x = Double.NaN)
```

</InlineCompilerError>
</InlineCompilerError>

---

# Companion extensions and blocks (Not released)

> `-Xcompanion-blocks-and-extensions`
> 
> This will give us better static interoperability with Java

<DrawnAnnotation text="companion val" label="Create static extensions"  :geometry="{ label: { x: 0.2110, y: 0.7862 } }"/>

```kotlin
data class Vector(val x: Double, val y: Double) {
    companion {
        val Zero: Vector = Vector(0.0, 0.0)
    }
}

companion val Vector.UnitX
  get() = Vector(1.0, 0.0)
```

---

# Companion extensions and blocks (Not released)

> `-Xcompanion-blocks-and-extensions`
>
> This will give us better static interoperability with Java

<DrawnAnnotation text="UUID" label="Create static extensions on types we do not own nor have a `companion` defined"  :geometry="{ label: { x: 0.4719, y: 0.9086 } }"/>

```kotlin
import java.util.UUID

data class Vector(val x: Double, val y: Double) {
    companion {
        val Zero: Vector = Vector(0.0, 0.0)
    }
}

companion val Vector.UnitX
  get() = Vector(1.0, 0.0)

companion val UUID.Empty
  get() = UUID(0L, 0L)
```

---

# Enums are a fixed set of values

```kotlin
enum class Direction { NORTH, SOUTH, EAST, WEST }
```

---
magic-move
---

# Enums are a fixed set of values

<DrawnAnnotation text="enum"/>
<DrawnAnnotation text="when (direction)"/>
<DrawnAnnotation text="Direction.WEST -> &quot;←&quot;" label="Exhaustive, every entry is known" :geometry="{ label: { x: 0.4462, y: 0.6996 } }"/>

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
<DrawnAnnotation text="when (direction)" label="Still exhaustive, compiler accumulates conditions"  :geometry="{ label: { x: 0.6065, y: 0.5611 } }"/>

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

<DrawnAnnotation text="val code: Int"/>
<DrawnAnnotation text="HttpStatus.entries" label="Iterate every entry, `valueOf(&quot;OK&quot;)` looks one up by name"/>

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

<DrawnAnnotation text="CONNECTION ->" label="`Problem.` is implied by the subject" :geometry="{ label: { x: 0.2598, y: 0.6856 } }"/>

```kotlin
enum class Problem { CONNECTION, AUTHENTICATION, DATABASE }

fun message(problem: Problem): String = when (problem) {
  CONNECTION -> "Connection failed"
  AUTHENTICATION -> "Authentication failed"
  DATABASE -> "Database failed"
}
```

---

# Representing states by Booleans

<DrawnAnnotation text="String?" label="non-null if `hasError && isLoading == false`" on="0"  :geometry="{ label: { x: 0.5834, y: 0.2611 } }"/>
<DrawnAnnotation text="Throwable?" label="non-null if `hasError == true`" on="1"  :geometry="{ label: { x: 0.5075, y: 0.3184 } }"/>
<DrawnAnnotation text="isLoading" label="When `true` `text` and `error` are `null`" on="2"  :geometry="{ label: { x: 0.5504, y: 0.4320 } }"/>

```kotlin
data class UiState(
    val text: String?,
    val error: Throwable?,
    val hasError: Boolean,
    val isLoading: Boolean
)
```

<div v-click="3" class="dont-do-this">DON'T DO THIS!</div>

---

# Sealed classes close a hierarchy

<DrawnAnnotation text="sealed class" label="Sealed hierarchy, all subclasses are known and live in the same module & package" :geometry="{ label: { x: 0.6829, y: 0.2198, width: 0.4449 } }"/>

```kotlin
sealed class UiState {
  data object Loading : UiState()
  data class Content(val text: String) : UiState()
  data class Error(val error: Throwable) : UiState()
}
```

---
magic-move
---

# Sealed classes close a hierarchy

<DrawnAnnotation text="data object" label="`toString()` prints `Loading` instead of `package.Loading@52cc8049`\nUse this to give empty `{ }` data a type" :geometry="{ label: { x: 0.4838, y: 0.5020, width: 0.6815 } }"/>

```kotlin
sealed class UiState {
  data object Loading : UiState()
  data class Content(val text: String) : UiState()
  data class Error(val error: Throwable) : UiState()
}
```

---
magic-move
---

# Sealed classes close a hierarchy

<SmartCast text="state" occurrence="3">
<SmartCast text="state" occurrence="4">
<DrawnAnnotation text="when (state)" label="Exhaustive without `else` since all subtypes are known"/>

```kotlin
sealed class UiState {
  data object Loading : UiState()
  data class Content(val text: String) : UiState()
  data class Error(val error: Throwable) : UiState()
}

fun message(state: UiState) = when (state) {
  UiState.Loading -> "Loading…"
  is UiState.Content -> state.text
  is UiState.Error -> "Error: ${state.error.message}"
}
```

</SmartCast>
</SmartCast>

---
magic-move
---

# Sealed classes close a hierarchy

<SmartCast text="state" occurrence="3">
<InlineCompilerError text="when (state) {" message="'when' expression must be exhaustive.\nAdd the 'is Error' branch or an 'else' branch">

```kotlin
sealed class UiState {
  data object Loading : UiState()
  data class Content(val text: String) : UiState()
  data class Error(val error: Throwable) : UiState()
}

fun message(state: UiState) = when (state) {
  UiState.Loading -> "Loading…"
  is UiState.Content -> state.text
}
```

</InlineCompilerError>
</SmartCast>

---
magic-move
---

# Sealed classes close a hierarchy

<SmartCast text="state" occurrence="3">
<SmartCast text="state" occurrence="4">
<SmartCast text="state" occurrence="5">
<DrawnAnnotation text="if state.text.contains(&quot;error&quot;)" label="Match on inner values. Smart-casting is applied in if-guard expression"  :geometry="{ label: { x: 0.4920, y: 0.8017 } }"/>

```kotlin
sealed class UiState {
  data object Loading : UiState()
  data class Content(val text: String) : UiState()
  data class Error(val error: Throwable) : UiState()
}

fun message(state: UiState) = when (state) {
  UiState.Loading -> "Loading…"
  is UiState.Content if state.text.contains("error") -> "Unknown error"
  is UiState.Content -> state.text
  is UiState.Error -> "Error: ${state.error.message}"
}
```

</SmartCast>
</SmartCast>
</SmartCast>

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 3</div>

# Parse in the companion

> A `private constructor` and a `companion object` with `parse`, a static factory in Kotlin clothes.
> `data` keeps `==`, `@ConsistentCopyVisibility` keeps `copy` private

```kotlin no-compile
data class Version(val major: Int, val minor: Int)

check(Version.parse("2.4") == Version.parse("2.4"))
check(Version.parse("banana") == null)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 3</div>

# Carry data in an entry

> `Priority` has `LOW`, `MEDIUM` and `HIGH`, each with a `level`

```kotlin no-compile
check(Priority.HIGH.level == 3)
check(Priority.entries.map { it.level } == [1, 2, 3])
check(Priority.valueOf("LOW") == Priority.LOW)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 3</div>

# Close the hierarchy

> A `sealed class Shape`, `area` is one `when` without `else`

```kotlin no-compile
check(area(Shape.Empty) == 0.0)
check(area(Shape.Circle(1.0)) == PI)
check(area(Shape.Rectangle(2.0, 3.0)) == 6.0)
check(Shape.Empty.toString() == "Empty")
```

---

# Interfaces define capabilities

<DrawnAnnotation text="fun draw() =" label="A default implementation" on="0" :geometry="{ label: { x: 0.3386, y: 0.3941 } }"/>
<DrawnAnnotation text="val sides: Int" on="1" />
<DrawnAnnotation text="override val sides" label="Implementers provide `sides`, the interface has no backing field" on="1" :geometry="{ label: { x: 0.7193, y: 0.5072, width: 0.3825 } }"/>

```kotlin
interface Shape {
  val sides: Int

  fun draw() = println("Drawing a $sides-sided shape")
}

class Polygon(override val sides: Int) : Shape

Polygon(6).draw() // Drawing a 6-sided shape
```

---

# The interface carries the annotation

<DrawnAnnotation text="@IgnorableReturnValue" label="Like `Set.add`, the `Boolean` is informative, not required" :geometry="{ label: { x: 0.62, y: 0.24 } }"/>
<DrawnAnnotation text="override fun register" label="Overrides inherit it" :geometry="{ label: { x: 0.4075, y: 0.6360 } }"/>

```kotlin
interface Registry {
  @IgnorableReturnValue
  fun register(user: Member): Boolean
}

class InMemoryRegistry : Registry {
  private val users = mutableSetOf<Member>()

  override fun register(user: Member): Boolean = users.add(user)
}

fun onboard(registry: Registry) {
  registry.register(Member("Ada", 36))
}
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

<DrawnAnnotation text="super<Clickable>"/>
<DrawnAnnotation text="super<Focusable>" label="`super<T>` picks which default implementation to call" :geometry="{ label: { x: 0.4040, y: 0.8003 } }"/>

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

# Classes are final by default

<InlineCompilerError text="Person" :occurrence="2" message="This type is final, so it cannot be inherited from.">

```kotlin
class Person

class Student : Person()
```

</InlineCompilerError>

---
magic-move
---

# Classes are final by default

<DrawnAnnotation text="open" label="Explicit opt in to inheritance, prefer composition" :geometry="{ label: { x: 0.3242, y: 0.3762 }, connector: { type: 'quadratic', start: { x: 0.1016, y: 0.2278 }, control: { x: 0.1033, y: 0.2838 }, end: { x: 0.1016, y: 0.3496 } } }"/>

```kotlin
open class Person

class Student : Person()
```

---

# Classes are final by default

<DrawnAnnotation text="(name, age)" label="Primary constructor of super type **must** be called" />

```kotlin
open class Person(val name: String, val age: Int)

class Student(val id: String, name: String, age: Int) : Person(name, age)
```

---

# Classes are final by default

<InlineCompilerError text="override" message="'hello' in 'Person' is final and cannot be overridden." >

```kotlin
open class Person(val name: String, val age: Int) {
    fun hello() { 
        println("Hello, I am $name.")
    }
}

class Student(val id: String, name: String, age: Int) : Person(name, age) {
    override fun hello() {
        println("Hello, I am $name. I am a student.")
    }
}
```

</InlineCompilerError>

---

# Classes are final by default

<DrawnAnnotation text="open" :line="2" label="Explicitly open function for inheritance"  :geometry="{ label: { x: 0.3363, y: 0.3724 } }"/>

```kotlin
open class Person(val name: String, val age: Int) {
    open fun hello() { 
        println("Hello, I am $name.")
    }
}

class Student(val id: String, name: String, age: Int) : Person(name, age) {
    override fun hello() {
        println("Hello, I am $name. I am a student.")
    }
}
```

---

# Classes are final by default

<DrawnAnnotation text="open" :line="7" />
<DrawnAnnotation text="final" :line="8" label="`final` limits function for further extension"  :geometry="{ label: { x: 0.3029, y: 0.7617 } }"/>

```kotlin
open class Person(val name: String, val age: Int) {
    open fun hello() { 
        println("Hello, I am $name.")
    }
}

open class Student(val id: String, name: String, age: Int) : Person(name, age) {
    final override fun hello() {
        println("Hello, I am $name. I am a student.")
    }
}
```

---

# Classes are final by default

<DrawnAnnotation text="open val" />
<DrawnAnnotation text="override val" label="Same applies to properties" />

```kotlin
import kotlin.uuid.Uuid

open class Person(val name: String, val age: Int) {
    open val id = Uuid.random().toString()
    
    open fun hello() { 
        println("Hello, I am $name.")
    }
}

open class Student(override val id: String, name: String, age: Int) : Person(name, age) {
    final override fun hello() {
        println("Hello, I am $name. I am a student.")
    }
}
```

---

# Classes are final by default

<DrawnAnnotation text="open val" />
<DrawnAnnotation text="override var" label="Allowed to change `val` to `var` but not other way around" />

```kotlin
import kotlin.uuid.Uuid

open class Person(val name: String, val age: Int) {
    open val id = Uuid.random().toString()
    
    open fun hello() { 
        println("Hello, I am $name.")
    }
}

open class Student(override var id: String, name: String, age: Int) : Person(name, age) {
    final override fun hello() {
        println("Hello, I am $name. I am a student.")
    }
}
```

---

# Abstract class

<DrawnAnnotation text="abstract" :line="1" />
<DrawnAnnotation text="abstract" :line="2" />
<DrawnAnnotation text="abstract" :line="4" label="`abstract` allows omitting the implementation but it cannot be instantiated"  :geometry="{ label: { x: 0.5204, y: 0.4093 } }"/>
<InlineCompilerError text="Person(&quot;Ada&quot;, 35)" :line="13" message="Cannot create an instance of an abstract class">

```kotlin
abstract class Person(val name: String, val age: Int) {
    abstract val id: String
    
    abstract fun hello()
}

open class Student(override var id: String, name: String, age: Int) : Person(name, age) {
    final override fun hello() {
        println("Hello, I am $name. I am a student.")
    }
}

val person = Person("Ada", 35)
```

</InlineCompilerError>

---
magic-move
---

# `protected` is the subclass API

<DrawnAnnotation text="protected abstract fun greeting" label="Only subclasses see it" :geometry="{ label: { x: 0.78, y: 0.243 } }"/>
<DrawnAnnotation text="fun hello()" :line="4" label="The public API stays in the base class" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.48, y: 0.337 } }"/>

```kotlin
abstract class Person(val name: String, val age: Int) {
    protected abstract fun greeting(): String

    fun hello() {
        println("${greeting()}, I am $name.")
    }
}

class Student(val id: String, name: String, age: Int) : Person(name, age) {
    override fun greeting(): String = "Hi"
}

Student("s-1", "Ada", 36).hello()
```
```console
Hi, I am Ada.
```

---
magic-move
---

# `protected` is the subclass API

<DrawnAnnotation text="protected" :line="2" label="Not package-visible like Java's" :geometry="{ label: { x: 0.76, y: 0.243 } }"/>
<InlineCompilerError text=".greeting()" message="Cannot access 'greeting': it is protected in 'Person'">

```kotlin
abstract class Person(val name: String, val age: Int) {
    protected abstract fun greeting(): String

    fun hello() {
        println("${greeting()}, I am $name.")
    }
}

class Student(val id: String, name: String, age: Int) : Person(name, age) {
    override fun greeting(): String = "Hi"
}

Student("s-1", "Ada", 36).greeting()
```

</InlineCompilerError>

---

# Choose the closed type deliberately

> Model types as precisely as you can
> 
> Choose the closed type deliberately

| Type | Best for |
| --- | --- |
| `data class` | A fixed set of named properties |
| `enum class` | A fixed set of named, usually stateless values |
| `sealed interface` | A fixed family of alternatives that can have unrelated class hierarchies |
| `interface` | An open capability that new implementations may add later |
| `abstract class` | An open family of types that may hold different data or state |
| `(open) class` | An open family of types that may hold different data or state |

<style>
.slidev-layout table {
    font-size: 1.9rem;
}
</style>

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

<DrawnAnnotation text="field += value" label="`field` is the property's backing field" :geometry="{ label: { x: 0.3561, y: 0.4540 } }"/>

```kotlin
class TextBuffer {
  var text = ""
    set(value) {
      println("Appending text: $value")
      field += value
    }
}
```

---
magic-move
---

# Setters are customizable

<DrawnAnnotation text="println(buffer.text)" label="Every write went through the setter" :geometry="{ label: { x: 0.4627, y: 0.7252 } }"/>

```kotlin
class TextBuffer {
  var text = ""
    set(value) {
      println("Appending text: $value")
      field += value
    }
}

val buffer = TextBuffer()
buffer.text = "Hello"
buffer.text = ", world!"
println(buffer.text)
```
```console
Appending text: Hello
Appending text: , world!
Hello, world!
```

---

# A getter can compute the value

<DrawnAnnotation text="get()" label="A custom getter runs on every access" on="0" :geometry="{ label: { x: 0.2431, y: 0.5099 } }"/>
<DrawnAnnotation text="text.isEmpty()" label="No backing field when the getter never reads `field`" on="1" :geometry="{ label: { x: 0.4171, y: 0.5199 } }"/>

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

```kotlin
class TextBuffer {
  var text = ""

  val isEmpty: Boolean
    get() = text.isEmpty()
}

val buffer = TextBuffer()
println(buffer.isEmpty)
buffer.text = "Hello"
println(buffer.isEmpty)
```
```console
true
false
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 3</div>

# Expose the read, keep the write

> `count` gets a `private set`, `increment()` is the only write

```kotlin no-compile
class Counter {
  var count: Int = 0
}

val counter = Counter()
counter.increment()
counter.increment()
check(counter.count == 2)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 3</div>

# Compute the getter

> `count` and `isEmpty` are computed by a `get()`, no backing field

```kotlin no-compile
class Inventory {
  private val items = mutableListOf<String>()

  fun add(item: String) {
    items.add(item)
  }
}

val inventory = Inventory()
check(inventory.isEmpty)
inventory.add("Kodee")
check(inventory.count == 1)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 3</div>

# Validate in the setter

> A `set(value)` that `require`s `5..30` before writing `field`

```kotlin no-compile
class Thermostat {
  var target: Int = 20
}

val thermostat = Thermostat()
thermostat.target = 25
check(runCatching { thermostat.target = 40 }.isFailure)
check(thermostat.target == 25)
```
