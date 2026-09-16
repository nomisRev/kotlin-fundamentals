---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 2</div>

# Kotlin Syntax

## Strings and constants

---

# Strings interpolate with `$`

<DrawnAnnotation text="$name" label="`$` substitutes a variable" :geometry="{ label: { x: 0.3382, y: 0.3699 }, connector: { type: 'quadratic', start: { x: 0.2464, y: 0.3226 }, control: { x: 0.2441, y: 0.3400 }, end: { x: 0.2464, y: 0.3574 } } }"/>

```kotlin
val name = "John"

println("Hello $name!") // Hello John!
```

---
magic-move
---

# Strings interpolate with `$`

<DrawnAnnotation text="${name.length}" label="`${...}` substitutes any expression" :geometry="{ label: { x: 0.5062, y: 0.3678 } }"/>

```kotlin
val name = "John"

println("$name.length is ${name.length}") // John.length is 4
```

---
magicMove: true
class: multi-dollar-template
---

# Strings interpolate with `$`

<DrawnAnnotation text="$$" label="A multi-dollar prefix changes the interpolation symbol" :geometry="{ label: { x: 0.3142, y: 0.3718 } }"/>

```kotlin
val name = "John"

println($$"$$name owes you 100$") // John owes you 100$
```

---

# Raw strings need no escaping

<DrawnAnnotation text="&quot;&quot;&quot;"/>
<DrawnAnnotation text="&quot;&quot;&quot;" :occurrence="2" label="A raw string is delimited by triple quotes" :geometry="{ label: { x: 0.2454, y: 0.5129 }, connector: { type: 'quadratic', start: { x: 0.0913, y: 0.4501 }, control: { x: 0.0925, y: 0.4720 }, end: { x: 0.0913, y: 0.4994 } } }"/>

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

<DrawnAnnotation text="trimIndent()" label="Trim the common leading whitespace" :geometry="{ label: { x: 0.2319, y: 0.5123 } }"/>

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

<DrawnAnnotation text="trimMargin()" label="Trim up to the `|` margin" :geometry="{ label: { x: 0.1974, y: 0.5137 } }"/>

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

# Const values

> `const val` is a compile-time constant
>
> inlined where it is used

<DrawnAnnotation text="const"/>

```kotlin
const val name = "John"

val text = """
    |for (c in "$name")
    |    print(c)
""".trimMargin()
```

---

# Const functions

> `const val` is a compile-time constant
>
> inlined where it is used

<DrawnAnnotation text="const" occurrence="2"/>
<DrawnAnnotation text="trimMargin" label="-Xintrinsic-const-evaluation since 2.4.0" :geometry="{ label: { x: 0.2516, y: 0.6857 } }"/>

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

<div class="lesson-number">Exercise 1 / 3</div>

# Interpolate

> `birthday(name, age)`, `$name` for the variable, `${...}` for the expression

```kotlin no-compile
check(birthday("Ada", 36) == "Ada turns 37 next year")
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 3</div>

# Frame it with a raw string

> `receipt(item, price)`, one raw string with `$item` and `$price`, then `trimIndent()`

```kotlin no-compile
check(receipt("Coffee", 3.5) == "Coffee costs 3.5\n----------\nThank you!")
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 3</div>

# Stop escaping `$`

> Rewrite `payload` with a `$$` prefix so `$id` needs no `${'$'}`

```kotlin
fun payload(name: String): String = "{ \"${'$'}id\": \"$name\" }"

check(payload("kodee") == "{ \"${'$'}id\": \"kodee\" }")
```
