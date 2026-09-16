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

## Lesson 5: DSLs and builders

The lesson takes approximately 75 minutes: 55 minutes of examples and discussion,
then a 20-minute exercise. It assumes extensions from lesson 2 and receiver lambdas
and collections from lesson 4. Lesson 4 is maintained separately; lesson 5 is
included after the currently available lesson 3 in the main deck.

Students edit `src/main/kotlin/exercises/lesson5/AgendaExercise.kt`. Models,
`@AgendaDsl`, scheduling, and display configuration are supplied. Complete the TODOs
and run:

```bash
./gradlew lesson5ExerciseCheck
```

This command intentionally fails until the starter is completed. The acceptance
checks cover defaults, declaration order, positive durations (including inserted
sessions), independent snapshots, and scheduling/rendering the same agenda with
different runtime parameters. As an IDE check, try nesting `session` inside a
session block: the shared DSL marker should reject implicit outer access.

The complete reference is `src/main/kotlin/presentation/support/Agenda.kt`:

```bash
./gradlew lesson5ReferenceCheck
npm run snippets:check
./gradlew compileLesson5Kotlin
```

The lesson-specific tasks compile independently of other lesson snippets.
The slide snippets compile against Ktor's actual server-core API; the context
parameter examples do not require starting a server. Kotlin 2.4 supports context
parameters without the old experimental context-parameter flag. Explicit named
context arguments are not needed for this lesson.

## Lesson 6: Coroutines

Structured concurrency with an animated Job hierarchy beside the code
(`components/JobTree.vue`, staged by the slide's `clicks:` count), wrapping a
Java SDK in each of its asynchronous styles (callback, `CompletableFuture`,
Reactive Streams `Publisher`), one scope composing all three, and Flow. The
Java SDK the slides wrap is `src/main/java/presentation/support/ConferenceSdk.java`;
the Kotlin helpers are in `src/main/kotlin/presentation/support/Conference.kt`.

The exercises live in `exercises/src/coroutines/` (Kotlin Toolchain project,
`= TODO()` bodies with the task as KDoc) with tests in `exercises/test/coroutines/`:

```bash
cd exercises
./kotlin test
```

The lesson's fences compile on their own, Java SDK included:

```bash
npm run snippets:check
./gradlew compileLesson6Kotlin compileLesson6Java
```
