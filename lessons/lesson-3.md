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

<DrawnAnnotation text="(Int) -> Int" label="Kotlin has function types" />

```kotlin
val increment: (Int) -> Int = { input -> input + 1 } 
```

---
magic-move
---

# Single parameter `it`

<DrawnAnnotation text="it" label="Single parameter lambdas can reference to their parameter using `it`" />

```kotlin
val increment: (Int) -> Int = { it + 1 } 
```

---
magic-move
---

# Single parameter `it`

<DrawnAnnotation text="it + 1" label="Last statement is always returned without `return`"  :geometry="{ label: { x: 0.3159, y: 0.3764 } }"/>

```kotlin
val increment: (Int) -> Int = { 
    it + 1
}
```

---

# Ignore a parameter with `_`

> Like `val _ =` for return values, `_` marks a value we deliberately ignore

<DrawnAnnotation text="_" label="The argument is still required, but we don't use it" />

```kotlin
val alwaysOne: (Int) -> Int = { _ -> 1 }

alwaysOne(42) // 1
```

---

# Parameterless lambdas

<DrawnAnnotation text="()" label="The parameter types list may be empty" />

```kotlin
val lazyOne: () -> Int = { 1 }
```

---

# Null result vs nullable lambda

<DrawnAnnotation text="Unit?" label="Nullable return type" :geometry="{ label: { x: 0.3866, y: 0.2625 } }"/>
<DrawnAnnotation text="(() -> Unit)?" color="var(--code-window-amper-color)" label="Nullable lambda" />

```kotlin
import kotlin.random.Random

val nullable: () -> Unit? = { if (Random.nextBoolean()) Unit else null }


val nullLambda: (() -> Unit)? = null
```

---

# Lambda with receiver

<DrawnAnnotation text="String.()" label="`lambda with receiver`, extension function equivalent for lambda (DSL lesson)."/>
<DrawnAnnotation text="this." label="`this` `String` receiver value inside lambda" on="1" color="var(--fundamentals-blue)"/>

```kotlin
val extension: String.() -> Int = { this.length }
```

---
magic-move
---

# Lambda with receiver

<DrawnAnnotation text="{ l"  label="`this.` can be omitted" color="var(--fundamentals-blue)"/>

```kotlin
val extension: String.() -> Int = { length }
```

---
magic-move
---

# Method references

<DrawnAnnotation text="String::length" label="`::` references a member function"/>

```kotlin
val len: String.() -> Int = String::length
```

---
magic-move
---

# Method references

<DrawnAnnotation text="(String) -> Int"  label="Most common lambda shape (i.e. Collections API)\nThese lambda shapes are interchangeable"/>

```kotlin
val len: String.() -> Int = String::length

val len2: (String) -> Int = len
```

---
magic-move
---

# Method references

<DrawnAnnotation text=".len()" label="Lambda with receiver can be called using extension function syntax"/>

```kotlin
val len: String.() -> Int = String::length

val len2: (String) -> Int = len

"test".len()
len2("test")
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 4</div>

# Write a lambda

> One parameter, so `it` will do

```kotlin no-compile
val isEven: (Int) -> Boolean

check(isEven(4))
check(!isEven(7))
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 4</div>

# Name the parameters

> Two parameters, so `it` is off the table

```kotlin no-compile
val add: (Int, Int) -> Int

check(add(2, 3) == 5)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 4</div>

# Give the lambda a receiver

> `this` is the `String`, so `uppercase()` needs no qualifier

```kotlin no-compile
val shout: String.() -> String

check("hi".shout() == "HI!")
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 4 / 4</div>

# Reference instead of wrap

> Replace the lambda with a `::` reference

```kotlin
val len: (String) -> Int = { it.length }

check(len("Kotlin") == 6)
```

---

# Functions that take lambdas

> A higher-order function is a function that takes or returns other functions as arguments or values

```kotlin
fun <A, B> Iterable<A>.map(transform: (A) -> B): List<B> = TODO()
```

---

# Functions that take lambdas

<DrawnAnnotation text="({" />
<DrawnAnnotation text="})" label="`map` has single parameter with function type"/>


```kotlin
fun <A, B> Iterable<A>.map(transform: (A) -> B): List<B> = TODO()

[1, 2, 3].map({ input -> input * 2 })
```

---

# Functions that take lambdas

<DrawnAnnotation text=" { " />
<DrawnAnnotation text=" }" label="Parentheses can be omitted"/>

```kotlin
fun <A, B> Iterable<A>.map(transform: (A) -> B): List<B> = TODO()

[1, 2, 3].map { it * 2 }
```

---

# Collections API

- Transforming and filtering
- Sorting, and grouping
- Partitioning

---
class: collection-hierarchy-slide
---

<KotlinCollectionContracts />

---

# A collection literal is a `List` by default

> `-Xcollection-literals` · experimental in Kotlin 2.4

<DrawnAnnotation text="[1, 2, 3]" label="No expected type, so the literal is a `List<Int>`" />

```kotlin
val numbers = [1, 2, 3]

val list: List<Int> = numbers
```

---
magic-move
---

# The expected type picks the collection

<DrawnAnnotation text="Set<Int>" label="Literal builds a `Set`, duplicates collapse" :geometry="{ label: { x: 0.72, y: 0.29 } }" />
<DrawnAnnotation text="MutableList<Int>" label="Literal builds a `MutableList`" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.72, y: 0.34 } }" />

```kotlin
val numbers = [1, 2, 3]

val set: Set<Int> = [1, 2, 3, 3]
val mutable: MutableList<Int> = [1, 2, 3]
mutable.add(4)

println(numbers)
println(set)
println(mutable)
```
```console
[1, 2, 3]
[1, 2, 3]
[1, 2, 3, 4]
```

---
magic-move
---

# The expected type picks the collection

<DrawnAnnotation text="Iterable<Int>" label="A supertype of `List` still gets a `List`" :geometry="{ label: { x: 0.70, y: 0.43 } }" />
<DrawnAnnotation text="sum([1, 2, 3])" label="A parameter type is an expected type too" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.55, y: 0.575 } }" />

```kotlin
val numbers = [1, 2, 3]

val set: Set<Int> = [1, 2, 3, 3]
val mutable: MutableList<Int> = [1, 2, 3]

val iterable: Iterable<Int> = [1, 2, 3]

fun sum(numbers: Iterable<Int>): Int = numbers.sum()
sum([1, 2, 3])
```

---

# FlatMap vs Map

<DrawnAnnotation text="-> B" label="`map` A to _single_ B"  :geometry="{ label: { x: 0.5363, y: 0.2719 } }"/>
<DrawnAnnotation text="-> Iterable<B>" label="`map` A to _many_ B's and _flatten_ nested lists" color="var(--code-window-amper-color)"/>

```kotlin
fun <A, B> Iterable<A>.map(transform: (A) -> B): List<B> = TODO()
 
 
fun <A, B> Iterable<A>.flatMap(transform: (A) -> Iterable<B>): List<B> = TODO()
```

---
magic-move
---

# FlatMap vs Map

```kotlin
fun <A, B> Iterable<A>.map(transform: (A) -> B): List<B> = TODO()
fun <A, B> Iterable<A>.flatMap(transform: (A) -> Iterable<B>): List<B> = TODO()

[1, 2, 3].map { it + 1 }
[1, 2, 3].map { [it, it+1] }
[1, 2, 3].flatMap { [it, it+1] }
```
```console
[2, 3, 4]
[[1, 2], [2, 3], [3, 4]]
[1, 2, 2, 3, 3, 4]
```

---

# Filter

<DrawnAnnotation text="-> Boolean" label="If `true` it's allowed to _pass through_ the filter"  :geometry="{ label: { x: 0.6872, y: 0.2790 } }"/>

```kotlin
 fun <A> Iterable<A>.filter(predicate: (A) -> Boolean): List<A> = TODO()
 
[1, 2, 3].filter { it % 2 == 0 }
```
```console
[2]
```

---

# Filter

<DrawnAnnotation text="filterNot" label="filterNot flips the predicate"  :geometry="{ label: { x: 0.5932, y: 0.3410 } }"/>

```kotlin
 fun <A> Iterable<A>.filter(predicate: (A) -> Boolean): List<A> = TODO()
 
[1, 2, 3].filter { it % 2 == 0 }
[1, 2, 3].filterNot { it % 2 == 0 }
```
```console
[2]
[1, 3]
```

---

# Map & Filter

<DrawnAnnotation text="else null" label="Our transformations may return nullable values" />

```kotlin
 fun <A, B> Iterable<A>.map(transform: (A) -> B): List<B> = TODO()
 fun <A> Iterable<A>.filter(predicate: (A) -> Boolean): List<A> = TODO()
 
[1, 2, 3].map { if (it % 2 == 0) it else null }
```
```console
[null, 2, null]
```

---

# Map & Filter

<DrawnAnnotation text="Int?"  />
<DrawnAnnotation text="it != null" label="We can filter out values, but lose the smart casting in the result" />

```kotlin
 fun <A, B> Iterable<A>.map(transform: (A) -> B): List<B> = TODO()
 fun <A> Iterable<A>.filter(predicate: (A) -> Boolean): List<A> = TODO()

val result: List<Int?> = [1, 2, 3].map { if (it % 2 == 0) it else null }
    .filter { it != null }
```
```console
[2]
```

---

# mapNotNull

<DrawnAnnotation text="Int" />
<DrawnAnnotation text="mapNotNull" label="Preserve the non-null type" occurrence="2" />

```kotlin
fun <A, B> Iterable<A>.mapNotNull(transform: (A) -> B?): List<B> = TODO()
 
val result: List<Int> = [1, 2, 3].mapNotNull { if (it % 2 == 0) it else null }
```
```console
[2]
```

---

# filterIsInstance()

<DrawnAnnotation text="filterIsInstance" label="Filter by type" occurrence="2"  :geometry="{ label: { x: 0.5438, y: 0.4888 } }"/>

```kotlin no-compile
fun <reified R> Iterable<*>.filterIsInstance(): List<R>

val numbers: List<Number> = [1, 2, 3.0, 4.0f, 0x10]
println(numbers)

val ints: List<Int> = numbers.filterIsInstance<Int>()
println(ints)
```
```console
[1, 2, 3.0, 4.0, 16]
[1, 2, 16]
```

---

# inline reified

<DrawnAnnotation text="inline" />
<DrawnAnnotation text="reified" label="Inlines a _reified_ function call with `KClass<R>` as parameter"  :geometry="{ label: { x: 0.3541, y: 0.2750 } }"/>

```kotlin
inline fun <reified R> Iterable<*>.filterIsInstance(): List<R> = TODO()
```
<div style="height: 4rem"></div>

```java no-compile
public <R> List<R> filterIsInstance(Iterable<R> values, Class<R> clazz)
```

---

# Grouping

<DrawnAnnotation text="List<T>" label="Aggregate values for a given key"  :geometry="{ label: { x: 0.7857, y: 0.2766 } }"/>

```kotlin
fun <T, K> Iterable<T>.groupBy(keySelector: (T) -> K): Map<K, List<T>> = TODO()
```

---

# Grouping

```kotlin
fun <T, K> Iterable<T>.groupBy(keySelector: (T) -> K): Map<K, List<T>> = TODO()

["a", "aaa", "bb", "ab", "ccc"].groupBy { it.length }
```
```console
{1=[a], 3=[aaa, ccc], 2=[bb, ab]}
```

---

# Grouping

```kotlin
fun <T, K, V> Iterable<T>.groupBy(
    keySelector: (T) -> K,
    valueTransform: (T) -> V
): Map<K, List<V>> = TODO()

["a", "aaa", "bb", "ab", "ccc"]
  .groupBy({ it.length }) { it.encodeToByteArray() }
```
```console
{1=[[B@12edcd21], 3=[[B@34c45dca, [B@52cc8049], 2=[[B@5b6f7412, [B@27973e9b]}
```

---

# Associate

<DrawnAnnotation text="T" occurrence="4" label="Only keeps last seen value" />

```kotlin
fun <T, K> Iterable<T>.associateBy(keySelector: (T) -> K): Map<K, T> = TODO()
```

---

# Associate

```kotlin
fun <T, K> Iterable<T>.associateBy(keySelector: (T) -> K): Map<K, T> = TODO()

["a", "aaa", "bb", "ab", "ccc"].associateBy { it.length }
```
```console
{1=[a], 3=[ccc], 2=[ab]}
```

---

# Associate

```kotlin
fun <T, K> Iterable<T>.associateBy(keySelector: (T) -> K): Map<K, T> = TODO()

["a", "aaa", "bb", "ab", "ccc"]
    .associate { it.length to it.encodeToByteArray() }
```
```console
{1=[B@300ffa5d, 3=[B@1f17ae12, 2=[B@4d405ef7}
```

---

# sortedBy accepts a key

```kotlin
fun <T, R : Comparable<R>> Iterable<T>.sortedBy(selector: (T) -> R?): List<T> = TODO()

["a", "aaa", "bb", "ab", "ccc"].sortedBy { it.length }
```
```console
[a, bb, ab, aaa, ccc]
```

---

# sortedBy accepts a key

```kotlin
fun <T, R : Comparable<R>> Iterable<T>.sortedBy(selector: (T) -> R?): List<T> = TODO()

["a", "aaa", "bb", "ab", null, "ccc", null].sortedBy { it?.length }
```
```console
[null, null, a, bb, ab, aaa, ccc]
```

---

# sortedBy accepts a key

```kotlin
fun <T, R : Comparable<R>> Iterable<T>.sortedByDescending(
    selector: (T) -> R?
): List<T> = TODO()

["a", "aaa", "bb", "ab", null, "ccc", null].sortedByDescending { it?.length }
```
```console
[aaa, ccc, bb, ab, a, null, null]
```

---

# sortedBy accepts a key

```kotlin
fun <T, R : Comparable<R>> Iterable<T>.sortedByDescending(
    selector: (T) -> R?
): List<T> = TODO()

["a", "aaa", "bb", "ab", "ccc"].sortedWith { a, b -> a.compareTo(b) }
```
```console
[a, aaa, ab, bb, ccc]
```

---

# Chain the collection operations

```kotlin
val names = members
  .filter { it.age >= 18 }
  .sortedBy { it.age }
  .map { it.name }
```

---

# Partition

<DrawnAnnotation text="partition" label="Split on predicate, first matched the predicate, second didn't" />

```kotlin
["a", "aaa", "bb", "ab", "ccc"].partition { it.length > 2 }
```
```console
([aaa, ccc], [a, bb, ab])
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 6</div>

# Transform

> `names` takes a `List<Member>`, `map` each `Member` to its `name`

```kotlin no-compile
class Member(val name: String, val age: Int)

val members = [Member("Ada", 36), Member("Linus", 17)]
check(names(members) == ["Ada", "Linus"])
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 6</div>

# Select

> `adults` keeps the members of 18 and older, `filter` on `age >= 18`

```kotlin no-compile
class Member(val name: String, val age: Int)

val ada = Member("Ada", 36)
val members = [ada, Member("Linus", 17)]
check(adults(members) == [ada])
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 6</div>

# Flatten

> `letters` turns a `List<String>` into every `Char`, `flatMap` and `toList()`

```kotlin no-compile
check(letters(["ab", "c"]) == ['a', 'b', 'c'])
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 4 / 6</div>

# Keep the non-null type

> `numbers(input)`, `toIntOrNull()` returns `Int?`, `mapNotNull` drops the `null`s

```kotlin no-compile
check(numbers(["1", "two", "3"]) == [1, 3])
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 5 / 6</div>

# Group

> `byLength` returns a `Map<Int, List<String>>`, `groupBy` on `length`

```kotlin no-compile
check(byLength(["a", "bb", "cc"]) == mapOf(1 to ["a"], 2 to ["bb", "cc"]))
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 6 / 6</div>

# Chain

> `adultNamesByAge`, youngest first: `filter`, then `sortedBy`, then `map`

```kotlin no-compile
class Member(val name: String, val age: Int)

val members = [
  Member("Ada", 36),
  Member("Linus", 17),
  Member("Grace", 25),
]
check(adultNamesByAge(members) == ["Grace", "Ada"])
```

---

# A function can return a function

> A higher-order function is a function that takes or returns other functions as arguments or values

<DrawnAnnotation text="(Member) -&gt; Boolean"/>

```kotlin
fun olderThan(age: Int): (Member) -> Boolean =
  { member -> member.age > age }

val overThirty = olderThan(30)
val selected = members.filter(overThirty)
```

---

# Capturing a var does not freeze it

```kotlin
var minimumAge = 18
val eligible: (Int) -> Boolean = { it >= minimumAge }

eligible(28)
minimumAge = 30
eligible(28)
```
```console
true
false
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 1</div>

# Return a function

> `startsWith(prefix)` returns a `(String) -> Boolean` that captures `prefix`

```kotlin no-compile
val isK = startsWith("K")
check(isK("Kotlin"))
check(!isK("Java"))
check(["Kotlin", "Java"].filter(startsWith("K")) == ["Kotlin"])
```

---

# Read the function type to understand the call


| Operation | Function supplied | Purpose |
| --- | --- | --- |
| `filter` | `(Value) -> Boolean` | Select elements |
| `map` | `(Value) -> String` | Transform elements |
| `sortedBy` | `(Value) -> Int` | Sort by sort key |
| `groupBy` | `(Value) -> Key` | Group by key |
| `fold` | `(Accumulator, Value) -> Accumulator` | Combine values |
| `forEach` | `(Value) -> Unit` | Perform an action |

<style>
.slidev-layout table { font-size: 1.9rem; }
</style>

---

# `for` needs an `iterator()`

<DrawnAnnotation text="private val members" label="`iterator()` is the API, the list is not" :geometry="{ label: { x: 0.62, y: 0.243 } }"/>
<InlineCompilerError :line="5" text="team" message="For-loop range must have an 'iterator()' method">

```kotlin
class Team(private val members: List<Member>)

val team = Team([Member("Ada", 36), Member("Linus", 17)])

for (member in team) {
  println(member.name)
}
```

</InlineCompilerError>

---
magic-move
---

# `for` needs an `iterator()`

<DrawnAnnotation text="operator fun iterator()" label="Any type with an `iterator()` operator is a `for` range" :geometry="{ label: { x: 0.62, y: 0.33 } }" />

```kotlin
class Team(private val members: List<Member>) {
  operator fun iterator(): Iterator<Member> = members.iterator()
}

val team = Team([Member("Ada", 36), Member("Linus", 17)])

for (member in team) {
    println(member.name)
}
```
```console
Ada
Linus
```

---
magic-move
---

# `for` needs an `iterator()`

<DrawnAnnotation text="operator fun Team.iterator()" label="Operators can always be defined as extensions"  :geometry="{ label: { x: 0.6364, y: 0.4751 } }"/>

```kotlin
class Team(private val members: List<Member>) 

operator fun Team.iterator(): Iterator<Member> = members.iterator()

val team = Team([Member("Ada", 36), Member("Linus", 17)])

for (member in team) {
    println(member.name)
}
```
```console
Ada
Linus
```

---
magic-move
---

# `for` desugars to `iterator()`

<DrawnAnnotation text="team.iterator()" label="The `for` loop is sugar for this `while`" :geometry="{ label: { x: 0.5928, y: 0.5320 } }" />
<DrawnAnnotation text="hasNext()" />
<DrawnAnnotation text="next()" />

```kotlin{7-9}
class Team(private val members: List<Member>) {
  operator fun iterator(): Iterator<Member> = members.iterator()
}

val team = Team([Member("Ada", 36), Member("Linus", 17)])

val iterator = team.iterator()
while (iterator.hasNext()) {
  val member = iterator.next()
  println(member.name)
}
```
```console
Ada
Linus
```

---

# `Iterable` unlocks the collections API

<InlineCompilerError :line="7" text="filter" message="Unresolved reference 'filter' on receiver of type 'Team'">

```kotlin
class Team(private val members: List<Member>) {
  operator fun iterator(): Iterator<Member> = members.iterator()
}

val team = Team([Member("Ada", 36), Member("Linus", 17)])

team.filter { it.age >= 18 }
    .map { it.name }
```

</InlineCompilerError>

---
magic-move
---

# `Iterable` unlocks the collections API

<DrawnAnnotation text=": Iterable<Member>" label="Every `Iterable<T>` extension now applies to `Team`" :geometry="{ label: { x: 0.70, y: 0.30 } }" />
<DrawnAnnotation text="override" />

```kotlin
class Team(private val members: List<Member>) : Iterable<Member> {
  override operator fun iterator(): Iterator<Member> = members.iterator()
}

val team = Team([Member("Ada", 36), Member("Linus", 17)])

team.filter { it.age >= 18 }
    .map { it.name }
```
```console
[Ada]
```

---

# A literal needs an `of()`

<InlineCompilerError :line="6" text="[Member(&quot;Ada&quot;, 36), Member(&quot;Linus&quot;, 17)]" message="Expected type Team for collection literal does not define operator of and is not a supertype of List">

```kotlin
class Team(private val members: List<Member>) : Iterable<Member> {
  override operator fun iterator(): Iterator<Member> = members.iterator()
}

val team: Team =
  [Member("Ada", 36), Member("Linus", 17)]
```

</InlineCompilerError>

---
magic-move
---

# A literal needs an `of()`

<DrawnAnnotation text="operator fun of" label="A companion `of` operator makes `Team` a literal target" :geometry="{ label: { x: 0.3776, y: 0.4544 } }" />
<DrawnAnnotation text="vararg members: Member" label="The literal's elements arrive as a `vararg`" on="1" color="var(--code-window-amper-color)" :geometry="{ label: { x: 0.6129, y: 0.5143 } }" />

```kotlin
class Team(private val members: List<Member>) : Iterable<Member> {
  override operator fun iterator(): Iterator<Member> = members.iterator()

  companion object {
    operator fun of(vararg members: Member): Team = Team(members.toList())
  }
}

val team: Team = [Member("Ada", 36), Member("Linus", 17)]

team.filter { it.age >= 18 }
    .map { it.name }
```
```console
[Ada]
```

---
magic-move
---

# A literal desugars to `of()`

<DrawnAnnotation text="Team.of(" label="The literal is sugar for this call" :geometry="{ label: { x: 0.60, y: 0.62 } }" />

```kotlin{9}
class Team(private val members: List<Member>) : Iterable<Member> {
  override operator fun iterator(): Iterator<Member> = members.iterator()

  companion object {
    operator fun of(vararg members: Member): Team = Team(members.toList())
  }
}

val team: Team = Team.of(Member("Ada", 36), Member("Linus", 17))

team.filter { it.age >= 18 }
    .map { it.name }
```
```console
[Ada]
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 2</div>

# Make `Team` iterable

> Implement `Iterable<Member>`, `override` its `iterator()`

```kotlin no-compile
class Member(val name: String, val age: Int)

class Team(private val members: List<Member>)

val team = Team([Member("Ada", 36), Member("Linus", 17)])
check(team.map { it.name } == ["Ada", "Linus"])
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 2</div>

# Make `Team` a literal target

> A companion `operator fun of(vararg members: Member)`

```kotlin no-compile
class Team(private val members: List<Member>) : Iterable<Member> {
  override operator fun iterator(): Iterator<Member> = members.iterator()
}

val team: Team = [Member("Ada", 36), Member("Linus", 17)]
check(team.count() == 2)
```
