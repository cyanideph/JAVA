package com.cyanideph.java.ui.model

object LegacyBuddyRepository {
    val receivedContacts: List<Buddy> = emptyList()

    val buddies: List<Buddy> = listOf(
        Buddy("cy", "cy", "Available", "buddies", yahooId = "cy_yahoo", msnId = "cy_msn", email = "cy@example.com", mobile = "09170000001"),
        Buddy("friend1", "Friend 1", "Available", "buddies", yahooId = "friend1_yahoo", msnId = "friend1_msn", email = "friend1@example.com", mobile = "09170000002"),
        Buddy("friend2", "Friend 2", "Not Available", "buddies", yahooId = "friend2_yahoo", msnId = "friend2_msn", email = "friend2@example.com", mobile = "09170000003"),
        Buddy("chatter", "Chatterbox", "Invisible", "chatterbox", email = "chatter@example.com", mobile = "09170000004")
    )
}
