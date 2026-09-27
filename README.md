# JAVA — Uzzap Android Reconstruction

This repository is the Android reconstruction workspace for the recovered Uzzap client.

## Goal

Rebuild the observable Uzzap frontend as an Android reconstruction while preserving the historical screen, menu, navigation, messaging, buddy, chatroom, settings, theme, help, and asset behavior documented from the recovered Java ME client.

## Current foundation

- Kotlin + Jetpack Compose
- Android API 24+ / target 36
- Compose Navigation
- Legacy renderer and Compose primitives; Material 3 is not part of the legacy UI layer
- Screen architecture ready for incremental parity work

## Reconstruction scope

1. Authentication
2. Main menu / buddy list
3. Messaging and message history
4. Buddy management and groups
5. Chat rooms and invitations
6. Status / offline settings
7. Profile / account settings
8. Themes and legacy visual system
9. Help
10. Billing / subscription surfaces
11. SMS / phonebook integration where Android permissions and platform APIs allow
12. Historical assets and emoticons

The legacy client remains the behavioral reference. Obsolete Java ME transport internals are not copied into the Android runtime; observable behavior is reimplemented with modern Android architecture.

## Status

Active parity reconstruction: legacy frontend/source parity is the current priority; backend transport is intentionally deferred.
