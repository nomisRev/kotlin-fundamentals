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
