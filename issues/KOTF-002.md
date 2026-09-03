# Verify the DrawnAnnotation targets in kotlin-fundamentals after the lesson edits

**Status:** open
**Repository:** kotlin-fundamentals
**Related:** ../../slidedev-theme-kotlin/issues/001 (dev-mode error chip for missing targets)

## Problem

While exporting kotlin-fundamentals on theme 0.12.1, a `DrawnAnnotation` in
lesson 2 with `text="text.isBlank()"` no longer matched the code below it. The
mark silently never appeared on the slide; only the export log showed

```
[DrawnAnnotation] Text "text.isBlank()" was not found in the slot.
```

`lessons/lesson-1.md`, `lesson-2.md`, and `lesson-3.md` have uncommitted edits
and the string `isBlank` no longer occurs in any lesson, so the specific case
may already be fixed. Nothing confirms that the other annotations still match.

## Work

- [ ] Run `npm run screenshot` (or `slidev export`) and read the log for
      `[DrawnAnnotation]` and `[InlineCompilerError]` warnings; fix every
      `text`, `selector`, or `occurrence` that no longer matches.
- [ ] Once the theme ships the dev-mode error chip (theme issue 001), rely on
      it while authoring instead of the export log.
- [ ] Commit the lesson edits.

## Acceptance

- [ ] A full export of the deck logs no missing-target warnings.
