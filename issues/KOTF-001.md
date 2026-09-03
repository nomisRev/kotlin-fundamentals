# Make kotlin-fundamentals crawlable, deep-linkable, and measured

**Status:** open
**Repository:** kotlin-fundamentals
**Depends on:** slidev-theme-kotlin release (see ../../slidedev-theme-kotlin/issues/002)

## Problem

The deployed deck at https://nomisrev.github.io/kotlin-fundamentals/ publishes
no `sitemap.xml`, handout, or `llms.txt`: `package.json` pins the theme at
0.12.1, which predates the static content. Even on a newer theme the deck
would write no sitemap, because the headmatter has no `themeConfig.siteUrl`.
The blog (new-blog) already lists `/kotlin-fundamentals/sitemap.xml` in its
`robots.txt` and `sitemap-index.xml`.

`deploy.yml` builds in history-router mode. GitHub Pages has no single-page-app
rewrite, so every deep link the handout would emit (`/kotlin-fundamentals/12`)
is served by `404.html` with an HTTP 404 status. Crawlers following those links
record dead pages.

## Work

- [ ] Bump `slidev-theme-kotlin` to the released version.
- [ ] Add to the headmatter:

  ```yaml
  info: |
    <one paragraph describing the course>
  themeConfig:
    siteUrl: https://nomisrev.github.io/kotlin-fundamentals/
    analytics:
      goatcounter: nomsrev
  ```

- [ ] Build with `--router-mode hash` in `deploy.yml`, like the other decks.
      The theme ships a redirect script so links shared before the switch
      (`/kotlin-fundamentals/12`) keep working as `/kotlin-fundamentals/#/12`.
- [ ] After the deploy, verify `sitemap.xml`, `handout/`, `llms.txt`, and
      `llms-full.txt` return 200 under `/kotlin-fundamentals/`.
- [ ] Confirm the talk entry in new-blog (`src/content/talks/kotlin-fundamentals.md`);
      its date and description were drafted from the cover and agenda slides.

## Acceptance

- [ ] The handout's "slide n ↗" links resolve to the live slide with HTTP 200.
- [ ] Slide paths appear in the GoatCounter dashboard under `/kotlin-fundamentals/`.
