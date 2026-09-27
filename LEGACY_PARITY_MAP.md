# Legacy Uzzap -> Android parity map

Legacy source: javauzzap@4645532460a865a6196ab04091a46f0d33d381e7

| Legacy class | Flow | Android target |
|---|---|---|
| dg.java | Offline landing: logo, arrow rows, Login/Register/Forgot/Help/About/Exit | Landing/Login navigation |
| k.java | Login: User ID max 12, password max 31, Login/Cancel | LoginScreen + auth boundary |
| cn.java | Auto-login confirmation, Yes/No persistence | Login confirmation |
| au.java | Registration fields, exact validation, mobile and username stages | Registration flow |
| cv.java | Forgot password, User ID/Mobile Number max 30 | Recovery flow |
| an.java | Main menu ordering and command routing | MainMenu |
| dd.java | Yahoo/MSN connection forms and state | IMConnections |
| db.java | Status message input and persistence | Status message editor |
| aa.java | Settings and status submenu routing | Settings |
| p.java | App-level alert mute; WAV then AMR fallback | AlertSoundController |
| af.java | Chatroom join/leave/error/message/whisper events and alerts | Chatroom event layer |

## Renderer requirements

Model the legacy toolkit instead of Material3: title bar, function/softkey bar, frame, tiled borders, menus, popup dialogs, forms/editors, tabs, indicators, checkbox, scrollbar, focus, selection, keyboard semantics, and theme.ini metrics.

## Verified facts

- 139 legacy resources are integrity tracked and pinned to the legacy commit.
- Login keys: amazilia.username and amazilia.password.
- Auto-login key: kolipri.xmpp.autologin.
- Legacy login lowercases username before transport/persistence.
- Alert playback uses alert.wav with alert.amr fallback.
- Status order: Status Message, Available, Not Available, Invisible.
- Settings rows: Edit My Profile, Change Status, Change Password, Change Mobile Number, Offline Settings, Chatroom Tones.

## Latest bulk form audit (legacy commit `4645532460a865a6196ab04091a46f0d33d381e7`)

- `co.java` Change Password: `old`, `new1`, `new2`; password flag `65536`; max length `30`; exact validation order and error strings are now represented in the Android form pipeline.
- `r.java` Add/Edit Other Contact: `custom-displayname` max `50`, `custom-mobile` constraint `3` max `13`, `custom-email` max `50`; OK/Cancel softkeys and validation strings are represented.
- `s.java` Edit Buddy: `custom-displayname` max `50`, `custom-email` max `50`; OK/Cancel and nickname/email validation are represented.
- `ai.java` mobile/PIN validation: mobile constraint `3` max `13`; PIN max `6`; mobile uses OK/Cancel, PIN uses OK/Exit; validation screens/routes are now represented without pretending the legacy transport exists.
- `dd.java` Yahoo/MSN: username max `50`; password flag `65536`, max `50`; provider-specific labels/help and Login/Cancel softkeys now flow through `LegacyFormList`.
- Legacy form password rendering was normalized so `PasswordVisualTransformation` is the single masking path rather than double-rendering masked text.

## Bulk frontend parity status

- Backend/auth/transport is intentionally excluded from the frontend parity target.
- Theme catalogue is source-derived from legacy `kt.aj.a()`: black, dolphins, hearts, roses, uzzap.
- Chat Rooms option labels and empty-state text are aligned with legacy `bm.java`.
- Received Contacts softkeys/options are aligned with legacy `ac.java`.
- Buddy List chrome no longer renders invented group-navigation arrows; buddy options are sourced against legacy `bo.java` conditions.
- Main Menu ordering/assets and its five legacy application options are aligned with `an.java`.
- Legacy asset inventory remains pinned to commit `4645532460a865a6196ab04091a46f0d33d381e7`.

## Remaining frontend gaps

1. Exact toolkit popup geometry/focus/selection behavior still needs screenshot-level verification.
2. Exact Main Menu grid/scrollbar pixel geometry still needs screenshot comparison against the legacy toolkit.
3. Buddy List group switching/row geometry needs source-level and screenshot verification.
4. Chat Rooms category/room rendering is service-driven and requires legacy data to verify populated rows; the empty state is source-aligned.
5. Form editor keyboard/caret/selection semantics need device-level verification.
6. Full asset-usage mapping (every legacy bitmap -> exact Android call site) remains to be completed.
7. Full end-to-end screenshot regression across every reachable frontend screen remains to be completed.
8. Assets are build-time synchronized, not yet fully vendored for offline builds.