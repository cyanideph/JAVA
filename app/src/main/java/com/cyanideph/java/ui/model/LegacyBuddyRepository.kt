package com.cyanideph.java.ui.model

import android.content.Context

object LegacyBuddyRepository {
    val receivedContacts: List<Buddy> = emptyList()

    // Production/frontend-parity mode stays empty until legacy service state supplies contacts.
    val buddies: List<Buddy> = emptyList()

    // Explicit local fixture used only by the "demo/demo" build-test account.
    // This does not represent backend/service data.
    private val demoBuddies = listOf(
        Buddy("demo-alice", "Alice", "Available", "buddies", email = "alice@example.test", mobile = "+639170000001"),
        Buddy("demo-bob", "Bob", "Not Available", "buddies", email = "bob@example.test", mobile = "+639170000002"),
        Buddy("demo-charlie", "Charlie", "Invisible", "chatterbox", yahooId = "charlie-demo"),
        Buddy("demo-action", "Pending Buddy", "Available", "action_required", mobile = "+639170000003"),
        Buddy("demo-contact", "Phone Contact", "Not Available", "other_contacts", email = "contact@example.test", mobile = "+639170000004")
    )

    fun buddiesFor(context: Context): List<Buddy> =
        if (context.getSharedPreferences("uzzap_legacy", Context.MODE_PRIVATE)
                .getBoolean("uzzap.demo.account", false)) demoBuddies else buddies
}
