package com.cyanideph.java.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.ui.*

@Composable
fun HelpScreen(onBack: () -> Unit) {
    val entries = listOf(
        "Intro Help", "Adding New Buddies", "Sending Messages",
        "Sending Indicator", "Unread Indicator", "Messaging Shortcuts",
        "Inviting Friends", "Sending Contacts", "User Status",
        "Available by SMS", "SMS/ESMS", "Phone Calling",
        "Hide Application", "Changing Mobile Number", "Log Off/Exit"
    )
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Help", Modifier.fillMaxWidth())
            Column(
                Modifier.weight(1f).fillMaxWidth().padding(horizontal = 6.dp)
            ) {
                entries.forEach { entry ->
                    LegacyText(
                        entry,
                        Modifier.fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                }
            }
            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Back",
                modifier = Modifier.fillMaxWidth().clickable { onBack() }
            )
        }
    }
}
