---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 2</div>

# Kotlin Syntax

## Nullability

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

<DrawnAnnotation text="?" label="The nullability modifier makes it explicit in the type" :geometry="{ label: { x: 0.4786, y: 0.2713 }, connector: { type: 'quadratic', start: { x: 0.3135, y: 0.2279 }, control: { x: 0.3112, y: 0.2453 }, end: { x: 0.3135, y: 0.2627 } } }"/>

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

<DrawnAnnotation text="?."/>
<DrawnAnnotation text="?." :occurrence="2" label="A safe call yields `null` instead of throwing"/>

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

<DrawnAnnotation text="!!." label="Forcefully unwrap the nullable type" on="0" :geometry="{ label: { x: 0.2895, y: 0.3722 } }"/>

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

<DrawnAnnotation text="nullable.length" label="_smart casting_ allows for non-null access after the check" :geometry="{ label: { x: 0.5238, y: 0.3650 } }"/>

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

<DrawnAnnotation text="?:" label="Elvis: when the left side is `null`, fall back to the right side" :geometry="{ label: { x: 0.6562, y: 0.3086 } }"/>

```kotlin
val nullable: String? = null
val length = nullable?.length ?: -1

println(length) // -1
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 3</div>

# Call it safely

> `shout` takes a `String?` and returns a `String?`, `uppercase()` behind a `?.`

```kotlin no-compile
check(shout("hi") == "HI")
check(shout(null) == null)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 3</div>

# Fall back with Elvis

> `lengthOrZero` takes a `String?` and returns an `Int`, `?.` then `?:`

```kotlin no-compile
check(lengthOrZero("Kotlin") == 6)
check(lengthOrZero(null) == 0)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 3</div>

# Drop the `!!`

> Remove both `!!`, the null check already smart-casts `name`

```kotlin
fun firstInitial(name: String?): Char {
  if (name == null || name!!.isEmpty()) return '?'
  return name!!.uppercase().first()
}

check(firstInitial("simon") == 'S')
check(firstInitial(null) == '?')
check(firstInitial("") == '?')
```
