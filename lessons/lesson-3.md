---
layout: intro
class: section-slide
---

<div class="lesson-number">Lesson 3</div>

# Classes

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

<DrawnAnnotation text="fun introduce" label="Functions declared in a class are member functions" on="0" placement="right">
<DrawnAnnotation text="$name" label="Members can use the instance's properties" on="0" placement="down">

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
