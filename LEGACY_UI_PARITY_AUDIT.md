# Uzzap Legacy UI Parity Audit

Source of truth: cyanideph/javauzzap main (recovered Uzzap 1.0.14).

## Coverage checked

- 171 recovered Java source files.
- 139 UI/theme/audio/help resources selected for Android legacy synchronization.
- 124 default-theme PNG resources.
- Reptilian theme chrome and theme.ini.
- Authentication, menus, buddy list, messaging, chat rooms, settings, profile/status, history, billing/subscription, help, SMS/phone/contact flows, themes, battery saving and connection/status states.
- Custom kt renderer primitives: Display/Canvas, title/function bars, menus, popups, tabs, forms, labels, bitmap labels, checkboxes, frames, scrolling and editors.
- Menu/state asset mappings were traced through the recovered source, including main-menu icons, tabs, tickboxes, avatars, presence, message indicators and emoticons.

## Main menu exact structure

1. Subscription — 000-smart
2. Buddy Matching — 001-abm
3. Add or Invite Buddies — 002-buddies
4. Settings — 003-settings
5. Silent Mode — 008-ringtone
6. Themes — 005-themes
7. Help — 006-help
8. Battery Saving — 007-batteryinfo
9. Extended Messaging — d000-em
10. Instant Messaging — d001-im
11. Chat Rooms — d002-chat
12. Change Status — d003-status

Options menu:
- Lock Keypad
- Log Off
- Intro Help Screen
- About Uzzap
- Exit Application

Additional menus/states identified in the source audit:
- Add-buddy-options
- connections
- statuses
- settings-options
- buddy-list-options
- message-options
- messenger-options
- chat-room-options
- chat-room-actions
- chat-invitation-actions
- history-options
- billing-options
- billing-history
- offline-options
- contact-upload-options
- invite-friends
- emoticons
- themes
- helpview-options
- disabled-options

## Asset classes that must not be replaced by generic Material UI

- Reptilian title bar left/middle/right
- Reptilian function bar left/middle/right
- Reptilian menu bottom bar
- Reptilian background pattern
- Default theme fallback chrome/background
- Selected/unselected tabs
- Selected/unselected tickboxes
- 16 main/dynamic menu icon pairs
- Presence/status icons
- Message unread/sending indicators
- Chat/message icons
- Buddy/room/settings icons
- Avatars
- All emoticons
- Frames
- Branding/logo assets
- Billing/contact/search/sync/settings assets
- Yahoo/MSN presence assets
- Alert audio
- Help/battery resource text

## Current Android gap

The Android repository has the legacy renderer foundation and asset manifest, but the existing feature screens are still mostly modern placeholder screens. MainMenuScreen was referenced by navigation but was not present during this audit. It must be implemented before claiming compile/UI parity.

## Exact-copy rule

Do not mark a screen complete until:
1. Its source Java screen/controller has been traced.
2. Every visual asset used by that screen is mapped.
3. Title/function bars, background, typography, selection states, icons, tabs, checkboxes, scrolling and popup behavior are reproduced.
4. All menu actions and secondary states are represented.
5. Screenshot comparison is performed against the recovered layout where a reference image/device render is available.
