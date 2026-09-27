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

## Remaining gaps

1. Real authentication/transport is not implemented; do not fake success.
2. Full theme.ini parsing and exact metrics remain incomplete.
3. Form input flags/editor semantics remain incomplete.
4. Chatroom/message/history backends remain placeholders.
5. Screenshot-based visual parity verification remains required.
6. Assets are build-time synchronized, not yet fully vendored for offline builds.