---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 4</div>

# Lambdas and collections

## Pass behavior as a value

---

# A simple lambda

<DrawnAnnotation text="(Int) -> Int" label="Kotlin has function types" :connect="false" />

```kotlin
val increment: (Int) -> Int = { input -> input + 1 } 
```

---
magic-move
---

# Single parameter `it`

<DrawnAnnotation text="it" label="Single parameter lambdas can reference to their parameter using `it`" :connect="false" />

```kotlin
val increment: (Int) -> Int = { it + 1 } 
```

---
magic-move
---

# Single parameter `it`

<DrawnAnnotation text="it + 1" label="Last statement is always returned without `return`" :connect="false"  :geometry="{ label: { x: 0.3159, y: 0.3764 } }"/>

```kotlin
val increment: (Int) -> Int = { 
    it + 1
}
```

---

# Parameterless lambdas

<DrawnAnnotation text="()" label="The parameter types list may be empty" :connect="false" />

```kotlin
val lazyOne: () -> Int = { 1 }
```

---

# Lambda parameter names

<DrawnAnnotation text="message: String" label="Lambda parameters may _suggest_ names\nThese can be used by LSP or IDE" :connect="false"/>

```kotlin
val print: (message: String) -> Unit = { 
    println(message)
}
```

---

# Null result vs nullable lambda

<DrawnAnnotation text="Unit?" label="Nullable return type" :connect="false" :geometry="{ label: { x: 0.3866, y: 0.2625 } }"/>
<DrawnAnnotation text="(() -> Unit)?" color="var(--code-window-amper-color)" label="Nullable lambda" :connect="false" />

```kotlin
val nullable: () -> Unit? = { if (Random.nextBoolean()) Unit else null }


val nullLambda: (() -> Unit)? = null
```

---

# Lambda with receiver

<DrawnAnnotation text="String.()" label="`lambda with receiver`" :connect="false"/>
<DrawnAnnotation text="this." label="`this` `String` receiver value inside lambda" on="1" color="var(--fundamentals-blue)" :connect="false"/>

```kotlin
val extension: String.() -> Int = { this.length }
```

---
magic-move
---

# Lambda with receiver

<DrawnAnnotation text="{ l"  label="`this.` can be omitted" :connect="false"/>

```kotlin
val extension: String.() -> Int = { length }
```

---
magic-move
---

# Method references

<DrawnAnnotation text="String::length"  label="`::` references a method on a type" :connect="false"/>

```kotlin
val length: String.() -> Int = String::length
```

---
magic-move
---

# Method references

<DrawnAnnotation text="(String) -> Int"  label="Most common lambda shape (i.e. Collections API)\nThese lambda shapes are interchangeable" :connect="false"/>

```kotlin
val length: String.() -> Int = String::length

val length2: (String) -> Int = String::length 
```

---

# Functions that take lambdas

```kotlin
fun <A> Iterable<A>.map(transform: (A) -> B): List<B>
```

---

# Functions that take lambdas

<DrawnAnnotation text="({" />
<DrawnAnnotation text="})" label="`map` has single parameter with function type" :connect="false"/>


```kotlin
fun <A> Iterable<A>.map(transform: (A) -> B): List<B>

[1, 2, 3].map({ input -> input * 2 })
```

---

# Functions that take lambdas

<DrawnAnnotation text=" { " />
<DrawnAnnotation text=" }" label="Parentheses can be omitted" :connect="false"/>

```kotlin
fun <A> Iterable<A>.map(transform: (A) -> B): List<B>

[1, 2, 3].map { it * 2 }
```

---

# Collections API

- `map` & `flatMap`
- `filter` & `mapNotNull`
- `groupBy`, `associate`
- `partition`
- `sumOf`, `maxOf`, `minOf`
- `fold` & `reduce`

---
class: collection-hierarchy-slide
---

<KotlinCollectionHierarchy />

---

# FlatMap vs Map

<DrawnAnnotation text="-> B" label="`map` A to _single_ B" :connect="false"  :geometry="{ label: { x: 0.5363, y: 0.2719 } }"/>
<DrawnAnnotation text="-> Iterable<B>" label="`map` A to _many_ B's and _flatten_ nested lists" color="var(--code-window-amper-color)" :connect="false"/>

```kotlin
 fun <A> Iterable<A>.map(tranform: (A) -> B): List<B>
 
 
 fun <A> Iterable<A>.flatMap(tranform: (A) -> Iterable<B>): List<B>
```

---
magic-move
---

# FlatMap vs Map

```kotlin
 fun <A> Iterable<A>.map(tranform: (A) -> B): List<B>
 fun <A> Iterable<A>.flatMap(tranform: (A) -> Iterable<B>): List<B>

[1, 2, 3].map { it + 1 }    // [2, 3, 4]
[1, 2, 3].map { [it, it+1] } // [[1, 2], [2, 3], [3, 4]]
[1, 2, 3].flatMap { [it, it+1] } // [1, 2, 2, 3, 3, 4]
```

---

# Filter

<DrawnAnnotation text="-> Boolean" label="If `true` it's allowed to _passthrough_ the filter" />

```kotlin
 fun <A> Iterable<A>.filter(tranform: (A) -> Boolean): List<A>
 
[1, 2, 3].filter { it % 2 == 0 } // [2]
```

---

# Filter

<DrawnAnnotation text="filterNot" label="filterNot flips the predicate" />

```kotlin
 fun <A> Iterable<A>.filter(tranform: (A) -> Boolean): List<A>
 
[1, 2, 3].filter { it % 2 == 0 } // [2]
[1, 2, 3].filterNot { it % 2 == 0 } // [1, 3]
```

---

# Map & Filter

<DrawnAnnotation text="filterNot" label="filterNot flips the predicate" />

```kotlin
 fun <A> Iterable<A>.map(tranform: (A) -> B): List<B>
 fun <A> Iterable<A>.filter(tranform: (A) -> Boolean): List<A>
 
[1, 2, 3].map { if (it % 2 == 0) it else null } // [null, 2, null]
```

---

# Map & Filter

```kotlin
 fun <A> Iterable<A>.map(tranform: (A) -> B): List<B>
 fun <A> Iterable<A>.filter(tranform: (A) -> Boolean): List<A>
 
[1, 2, 3].map { if (it % 2 == 0) it else null }
    .filter { it != null } // [2]
```

---

# Map & Filter

```kotlin
 fun <A> Iterable<A>.map(tranform: (A) -> B): List<B>
 fun <A> Iterable<A>.filter(tranform: (A) -> Boolean): List<A>
 
val result: List<Int?> = [1, 2, 3].map { if (it % 2 == 0) it else null }
    .filter { it != null } // [2]
```

---

# Grouping

```kotlin
fun <A, B> Iterable<A>.groupBy(keySelector: (A) -> B): Map<B, List<A>>
```

---

# Grouping

```kotlin
fun <A, B> Iterable<A>.groupBy(keySelector: (A) -> B): Map<B, List<A>>

["a", "aaa", "bb", "ab", "ccc"].groupBy { it.length }
```

---

# Grouping

```kotlin
fun <A, B> Iterable<A>.groupBy(keySelector: (A) -> B): Map<B, List<A>>

val result: Map<Int, String> =
    ["a", "aaa", "bb", "ab", "ccc"]
        .groupBy { it.length }
```

---

# Grouping

```kotlin
fun <A, B> Iterable<A>.groupBy(keySelector: (A) -> B): Map<B, List<A>>

val result: Map<Int, ByteArray> =
    ["a", "aaa", "bb", "ab", "ccc"]
        .groupBy({ it.length }) { it.encodeToByteArray() }
```

---

# Simple DSL

```kotlin
val adults = mutableListOf<Member>()
for (member in members) {
  if (member.age >= 18) {
    adults.add(member)
  }
}
```

---
magic-move
---

# A loop selects the adults

```kotlin
val adults = buildList {
    for (member in members) {
        if (member.age >= 18) {
            add(member)
        }
    }
}
```

<!--
Opening section: 5 minutes including the dataset. Ask which part walks the collection and which part decides whether to keep a member.
-->

---

# filter accepts the decision

<DrawnAnnotation text="member.age &gt;= 18" :connect="false"/>

```kotlin
val adults = members.filter({ member: Member ->
  member.age >= 18
})
check(adults == listOf(members[0], members[2]))
```

<!--
filter handles traversal and builds a result list. The caller supplies a function returning Boolean: true keeps this element. The original list is unchanged.
-->

---

# A lambda is a function expression

<DrawnAnnotation text="{ member: Member -&gt;" :connect="false"/>

```kotlin
val isAdult: (Member) -> Boolean = { member: Member ->
  member.age >= 18
}
```

<!--
Syntax section: 12 minutes. Separate the two arrows: the type describes input and output; the lambda arrow separates parameters from the body. Braces delimit the function expression.
-->

---

# The call supplies the parameter type

```kotlin
val adults = members.filter({ member ->
  member.age >= 18
})
```

<!--
filter on List<Member> expects a predicate accepting Member. The compiler can infer the parameter type; removing the annotation does not remove the type.
-->

---

# The final lambda can move outside

```kotlin
val adults = members.filter() { member ->
  member.age >= 18
}
```

<!--
A lambda supplied to the final function parameter can be written after the parentheses. This is the same call, not a new collection feature.
-->

---

# An only lambda needs no parentheses

```kotlin
val adults = members.filter { member ->
  member.age >= 18
}
```

<!--
When the lambda is the only argument, omit the empty parentheses. Keep each syntactic change separate.
-->

---

# One parameter can be called it

<DrawnAnnotation text="it.age" :connect="false"/>

```kotlin
val adults = members.filter { it.age >= 18 }
```

<!--
it is the implicit name for a single lambda parameter. Prefer a named parameter when it makes the expression easier to read, particularly in nested code.
-->

---

# The last expression is the result

<DrawnAnnotation text="oldEnough" :connect="false"/>

```kotlin
val adults = members.filter { member ->
  val minimumAge = 18
  val oldEnough = member.age >= minimumAge
  oldEnough
}
```

<!--
A multiline lambda still returns its last expression. Do not introduce bare return here: lambdas have additional control-flow rules reserved for a later topic.
-->

---

# Which members remain?

```kotlin
val selected = members.filter { member ->
  val startsWithA = member.name.startsWith("A")
  startsWithA || member.age < 18
}
```

Predict the result and its type before running the code.

<!--
Allow one minute to predict before advancing. Answer: Ada and Linus, in original order. Ask for the type of the lambda result and the type of selected.
-->

---

# The predicate returns a Boolean

```kotlin
val selected = members.filter { member ->
  val startsWithA = member.name.startsWith("A")
  startsWithA || member.age < 18
}
check(selected == listOf(members[0], members[1]))
```

<!--
The result is List<Member>. The predicate produces one Boolean per visited member; filter produces the list.
-->

---

# Store and invoke a function value

<DrawnAnnotation text="(Member) -&gt; Boolean" :connect="false"/>

```kotlin
val isAdult: (Member) -> Boolean = { it.age >= 18 }

println(isAdult(members[0])) // true
val adults = members.filter(isAdult)
```

<!--
Function values section: 8 minutes. Calling isAdult(member) computes a Boolean. Passing isAdult supplies behavior for filter to invoke. No lambda braces are needed around an existing function value.
-->

---

# A named function can be passed too

<DrawnAnnotation text="::isAdult" :connect="false"/>

```kotlin
fun isAdult(member: Member): Boolean = member.age >= 18

val adults = members.filter(::isAdult)
check(adults == listOf(members[0], members[2]))
```

<!--
::isAdult is a function reference. Compare with members.filter { member -> isAdult(member) }. Keep references to this simple named function; avoid overloads and reflection.
-->

---

# map transforms each element

<DrawnAnnotation text="List&lt;String&gt;" :connect="false"/>

```kotlin
val names: List<String> = members.map { member ->
  member.name
}
println(names) // [Ada, Linus, Grace]
```

<!--
Transformation section: 10 minutes. This lambda returns String, not Boolean. map produces one result for each input element, in input order.
-->

---

# sortedBy accepts a key

```kotlin
val youngestFirst = members.sortedBy { it.age }
println(youngestFirst.map { it.name }) // [Linus, Grace, Ada]
```

<!--
The lambda returns the key to compare, here an Int. sortedBy returns a sorted list without reordering the source.
-->

---

# Follow the types through a pipeline

```kotlin
val adults: List<Member> = members.filter { it.age >= 18 }
val sorted: List<Member> = adults.sortedBy { it.age }
val names: List<String> = sorted.map { it.name }

println(names) // [Grace, Ada]
```

<!--
Ask learners to name each lambda type: (Member) -> Boolean, (Member) -> Int, and (Member) -> String. These list operations run eagerly; defer sequences.
-->

---

# Chain the collection operations

```kotlin
val names = members
  .filter { it.age >= 18 }
  .sortedBy { it.age }
  .map { it.name }

check(names == listOf("Grace", "Ada"))
```

<!--
Same operations as the preceding slide. Each line receives the previous result. Once mapped to names, age is no longer available; order operations deliberately.
-->

---

# forEach performs an action

```kotlin
val names: List<String> = members.map { it.name }
val result: Unit = names.forEach { name -> println(name) }

check(result == Unit)
```

<!--
map collects results; forEach returns Unit. Use forEach for effects such as printing, not to construct a transformed list. Ask what map { println(it.name) } would produce: List<Unit>.
-->

---

# A lambda can capture its surroundings

<DrawnAnnotation text="minimumAge" :connect="false"/>

```kotlin
val minimumAge = 30
val selected = members.filter { it.age >= minimumAge }

check(selected == listOf(members[0]))
```

<!--
Capture section: 8 minutes. minimumAge is not a lambda parameter; it comes from the enclosing scope.
-->

---

# A function can return a predicate

<DrawnAnnotation text="(Member) -&gt; Boolean" :connect="false"/>

```kotlin
fun olderThan(age: Int): (Member) -> Boolean =
  { member -> member.age > age }

val overThirty = olderThan(30)
val selected = members.filter(overThirty)
check(selected == listOf(members[0]))
```

<!--
The returned lambda keeps access to age after olderThan returns. olderThan uses strict greater-than to match its name. A higher-order function accepts a function, returns a function, or both.
-->

---

# Capturing a var does not freeze it

```kotlin
var minimumAge = 18
val eligible: (Member) -> Boolean = { it.age >= minimumAge }

check(eligible(members[2])) // Grace is 28
minimumAge = 30
check(!eligible(members[2]))
```

<!--
Ask for the second result before revealing it verbally. The same function value reads the current value of the captured variable. Prefer a fixed captured val when a stable rule is intended.
-->

---

# fold accepts two parameters

<DrawnAnnotation text="total, member" :connect="false"/>

```kotlin
val totalAge = members.fold(0) { total, member ->
  total + member.age
}
check(totalAge == 81)
```

<!--
fold section: 7 minutes. Here the function type is (Int, Member) -> Int. The initial value is a regular argument; the combining lambda is the final argument. Two parameters must be named explicitly.
-->

---

# The result becomes the next accumulator


| Accumulator | Member age | Lambda result |
| --- | --- | --- |
| `0` | Ada: `36` | `36` |
| `36` | Linus: `17` | `53` |
| `53` | Grace: `28` | `81` |

<style>
.slidev-layout table { font-size: 1.9rem; }
</style>

<!--
Trace each row aloud. fold calls the same function value with a new accumulator and member. No external mutable total is required.
-->

---

# An empty fold returns the initial value

```kotlin
val nobody = emptyList<Member>()
check(nobody.fold(0) { total, member -> total + member.age } == 0)

val totalAge = members.sumOf { it.age }
check(totalAge == 81)
```

<!--
Ask for the empty-list result before explaining it. The lambda is never invoked. sumOf expresses this particular aggregation directly; fold helps us understand functions with multiple parameters.
-->

---

# Write a function that accepts behavior

<DrawnAnnotation text="predicate(member)" :connect="false"/>

```kotlin
fun List<Member>.select(
  predicate: (Member) -> Boolean,
): List<Member> {
  val result = mutableListOf<Member>()
  for (member in this) {
    if (predicate(member)) result.add(member)
  }
  return result
}
```

<!--
Implementation section: 8 minutes. Reuse extension syntax from lesson 2. Identify receiver, function parameter, invocation, and result. Use concrete Member types so generics do not compete with the new concept. This is a teaching implementation of filter.
-->

---

# The caller chooses the predicate

```kotlin
val adults = members.select { it.age >= 18 }
val namesStartingWithA = members.select { it.name.startsWith("A") }

check(adults == listOf(members[0], members[2]))
check(namesStartingWithA == listOf(members[0]))
```

<!--
Both calls use the implementation just shown, supplied as snippet support on this slide. The collection traversal stays the same; the caller supplies the decision.
-->

---

# Check the collection boundaries

```kotlin
check(members.select { true } == members)
check(members.select { false }.isEmpty())
check(emptyList<Member>().select { true }.isEmpty())
check(members.size == 3)
```

<!--
All matches, no matches, and empty input. Result order follows the input and the source remains unchanged. Standard filter is what we would use in application code.
-->

---

# Practice: build an age report


For the same members:

1. Create a reusable predicate for members older than 20.
2. Select them, sort by age, and extract their names.
3. Check that the report is `[Grace, Ada]`.

Use `olderThan`, `filter`, `sortedBy`, and `map`.

<!--
Practice and review: 12 minutes total. Allow four minutes here and four for firstMatching, with four for discussion. Expected solution follows on the next slide. Reuse olderThan from earlier.
-->

---

# An age report composes function values

```kotlin
val eligible = olderThan(20)
val report = members
  .filter(eligible)
  .sortedBy { it.age }
  .map { it.name }

check(report == listOf("Grace", "Ada"))
```

<!--
Ask which call creates a function value and which calls invoke supplied behavior. olderThan(20) returns the predicate; filter uses it.
-->

---

# Practice: find the first match


Implement this extension using a loop and a predicate:

```kotlin
fun List<Member>.firstMatching(
  predicate: (Member) -> Boolean,
): Member? = TODO("Find the first match")
```

Return the first matching member, or `null` if none match.

Check multiple matches, no matches, and an empty list.

<!--
Implement with a loop. Return immediately when the predicate succeeds; return null after the loop. A bare return here is in a named function, not a lambda. Allow four minutes. The solution follows.
-->

---

# Return the first match or null

```kotlin
fun List<Member>.firstMatching(
  predicate: (Member) -> Boolean,
): Member? {
  for (member in this) {
    if (predicate(member)) return member
  }
  return null
}
```

<!--
The loop stops at the first match. The nullable return type makes absence explicit. This exercise models the standard firstOrNull(predicate) operation.
-->

---

# Verify first-match behavior

```kotlin
check(members.firstMatching { it.age >= 18 } == members[0])
check(members.firstMatching { it.age > 100 } == null)
check(emptyList<Member>().firstMatching { true } == null)

check(members.firstMatching { it.age >= 18 } ==
  members.firstOrNull { it.age >= 18 })
```

<!--
Discuss why the first result is Ada even though Grace also matches. Compare with the library operation learners should use in production.
-->

---

# Read the function type to understand the call


| Operation | Function supplied | Purpose |
| --- | --- | --- |
| `filter` | `(Member) -> Boolean` | Select elements |
| `map` | `(Member) -> String` | Transform elements |
| `sortedBy` | `(Member) -> Int` | Choose a sort key |
| `fold` | `(Int, Member) -> Int` | Combine values |
| `forEach` | `(Member) -> Unit` | Perform an action |

Next: receiver lambdas and DSLs.

<style>
.slidev-layout table { font-size: 1.9rem; }
</style>

<!--
Exit check: ask learners to explain each row without naming the syntax first. Next lesson introduces receiver lambdas, which make an object available as this inside a function value. Sources: https://kotlinlang.org/docs/lambdas.html and https://kotlinlang.org/docs/collection-transformations.html
-->
