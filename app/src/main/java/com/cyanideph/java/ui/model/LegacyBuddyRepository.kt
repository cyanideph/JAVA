package com.cyanideph.java.ui.model

object LegacyBuddyRepository {
    val receivedContacts: List<Buddy> = emptyList()

    // No synthetic contacts: the legacy client populates this list from service state.
    // Backend population is intentionally deferred; the frontend must not invent visible users.
    val buddies: List<Buddy> = emptyList()
}
