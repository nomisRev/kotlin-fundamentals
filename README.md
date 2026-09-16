# Kotlin Fundamentals

The Kotlin Fundamentals course using [Slidev](https://sli.dev) with [`slidev-theme-kotlin`](https://www.npmjs.com/package/slidev-theme-kotlin).

## Run locally

Requires Node.js 18 or newer.

```bash
npm install
npm run dev
```

## Present with a phone remote

Use the phone-first **presenter** page for the controls. The `/entry/` URL
printed by Slidev is only an index page; open the presenter URL directly.

1. Put the laptop and phone on the same network. A personal hotspot is the most
   reliable option at a venue; guest Wi-Fi commonly prevents devices from
   reaching each other.
2. Start the presentation:

   ```bash
   npm run present
   ```

3. Open the slideshow and presenter page using the **same LAN hostname/IP**—do
   not use `localhost` for one and the LAN IP for the other. For example, leave
   `http://192.168.x.x:3030/` on the projector and open
   `http://192.168.x.x:3030/presenter/1` on the phone. You can also scan the
   QR code printed after pressing `c`, then replace `/entry/` with
   `/presenter/1` in the opened URL.

For a remote co-presenter on another network, use:

```bash
npx slidev --remote --tunnel
```

The tunnel URL is public, so only use it when needed. For a password-protected
session, start Slidev with `--remote=your-password` instead.

## Build and export

```bash
npm run build
npm run export
```

## Exercises

`exercises/` is a [Kotlin Toolchain](https://kotlin-toolchain.org) project with
one module per cluster of exercise slides, in deck order: `functions`,
`control-flow`, `nullability`, `strings` (lesson 2), `lambdas`, `collections`,
`iteration` (lesson 3), `data-classes`, `closed-types`, `properties` (lesson 4),
`dsl` (lesson 5) and `coroutines` (lesson 6). Every exercise has a JUnit test;
the slide shows the assertions, the KDoc in the module's source file states the
task.

Where the body is the lesson, the function is there with a `= TODO()` body.
Where writing the declaration is the lesson (an extension, a `vararg`, an
`infix` or `operator` function, a nullable signature, a data class), the KDoc
describes the shape and the function is not there yet: the module's tests do not
compile until it is, so the first step of such an exercise is to make the tests
compile, the second to make them pass. Modules are kept apart for that reason:
an unfinished module never blocks the tests of another.

```bash
cd exercises
./kotlin test                              # every module
./kotlin test --include-module functions   # one module
```

`exercises/solutions/` mirrors the exercise files with reference solutions.
`exercises/check-solutions.sh` copies the project to a temporary directory,
overlays the solutions and runs the tests, which is how the tests themselves are
checked (the starter project deliberately does not compile):

```bash
exercises/check-solutions.sh
exercises/check-solutions.sh --include-module dsl
```

## Lesson 5: DSLs and builders

The lesson takes approximately 75 minutes: 55 minutes of examples and discussion,
then a 20-minute exercise. It assumes extensions from lesson 2 and receiver lambdas
and collections from lesson 4. Lesson 4 is maintained separately; lesson 5 is
included after the currently available lesson 3 in the main deck.

The exercises are the `dsl` module of the exercises project (see below):
`exercises/dsl/src/dsl/AgendaBuilder.kt` holds the builders with `= TODO()`
bodies and the task as KDoc, `exercises/dsl/test/dsl/AgendaTest.kt` the tests.
The complete reference the slides compile against is
`src/main/kotlin/presentation/support/Agenda.kt`:

```bash
npm run snippets:check
./gradlew build
```

The slide snippets compile against Ktor's actual server-core API; the context
parameter examples do not require starting a server. Kotlin 2.4 supports context
parameters without the old experimental context-parameter flag. Explicit named
context arguments are not needed for this lesson.

## Lesson 6: Coroutines

Structured concurrency with an animated Job hierarchy beside the code
(`components/JobTree.vue`, staged by the slide's `clicks:` count), wrapping a
Java SDK in each of its asynchronous styles (callback, `CompletableFuture`,
Reactive Streams `Publisher`), one scope composing all three, Flow as a pull-based
stream (`components/FlowTimeline.vue`), channels (`Channel`, `produce`, the
suspending `for`, `buffer` and `channelFlow`, with `components/ChannelDiagram.vue`)
and `StateFlow`, whose read-only view uses an explicit backing field
(enabled by default since Kotlin 2.4).
The lesson uses KotlinX Coroutines only. The Java SDK the slides wrap is
`src/main/java/presentation/support/ConferenceSdk.java`; the Kotlin helpers are in
`src/main/kotlin/presentation/support/Conference.kt`.

The exercises are the `coroutines` module of the exercises project (see below):
`exercises/coroutines/src/coroutines/` holds the `= TODO()` bodies with the task
as KDoc, `exercises/coroutines/test/coroutines/` the tests:

```bash
cd exercises
./kotlin test --include-module coroutines
```

The lesson's fences compile with the rest of the deck, Java SDK included:

```bash
npm run snippets:check
./gradlew build
```
