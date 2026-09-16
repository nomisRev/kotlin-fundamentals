---
theme: kotlin
transition: fade
layout: cover
class: fundamentals-cover
canvasWidth: 1440
aspectRatio: 16/9
colorSchema: both
favicon: /favicon.svg
fonts:
  sans: JetBrains Sans
  mono: JetBrains Mono
  provider: none
  local:
    - JetBrains Sans
    - JetBrains Mono
highlighter: shiki
themeConfig:
  kodee: greeting
  drawnAnnotation:
    connect: false
  snippets:
    dir: src/main/kotlin/presentation/snippets
    package: presentation.snippets
    imports:
      - presentation.support.*
kodee: welcome

# Preview: return value checker

---
layout: intro
---

# Lesson 2 — after "Expression bodies drop the block"

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

<Warning :line="3" text="sum(1, 2)" message="unused return value of 'sum'.">

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

<DrawnAnnotation text="greet(&quot;Ada&quot;)" label="`Unit` is never a dropped result" :geometry="{ label: { x: 0.36, y: 0.50 } }"/>

```kotlin
fun sum(a: Int, b: Int): Int = a + b
fun greet(name: String) = println("Hello!")

val _ = sum(1, 2)
greet("Ada")
```

---
layout: intro
---

# Lesson 4 — after `copy(...)`

---
magic-move
---

# `copy(...)`

<DrawnAnnotation text="copy" label="Compiler generated method to `copy` an instance with new values" :geometry="{ label: { x: 0.5055, y: 0.3508 } }"/>
<DrawnAnnotation text="age = " label="Relies on argument names, and default values"  :geometry="{ label: { x: 0.4308, y: 0.7371 } }"/>

```kotlin
data class User(val name: String, val age: Int) {
    fun copy(name: String = this.name, age: Int = this.age): Person {
        return Person(name, age)
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

<Warning :line="4" text="user.copy(age = 37)" message="unused return value of 'copy'.">

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
layout: intro
---

# Lesson 4 — in the interfaces block (optional)

---

# The interface carries the annotation

<DrawnAnnotation text="@IgnorableReturnValue" label="Like `Set.add`, the `Boolean` is informative, not required" :geometry="{ label: { x: 0.62, y: 0.24 } }"/>
<DrawnAnnotation text="override fun register" label="Overrides inherit it" :geometry="{ label: { x: 0.70, y: 0.475 } }"/>

```kotlin
interface Registry {
  @IgnorableReturnValue
  fun register(user: User): Boolean
}

class InMemoryRegistry : Registry {
  private val users = mutableSetOf<User>()

  override fun register(user: User): Boolean = users.add(user)
}

fun onboard(registry: Registry) {
  registry.register(User("Ada", 36))
}
```
