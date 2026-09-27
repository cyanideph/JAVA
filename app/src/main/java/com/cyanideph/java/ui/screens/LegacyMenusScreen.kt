package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

@Composable
fun LegacyMenusScreen(onBack: () -> Unit, onSelect: (String) -> Unit) {
    val entries = listOf(
        "main-menu-options" to listOf("Lock Keypad", "Log Off", "Intro Help", "About Uzzap", "Exit Application"),
        "buddy-list-options" to listOf("Contact", "Send Group Message", "Manage Groups", "New Group", "Add/Invite Buddies", "Clear Message History", "Status Message", "Available", "Not Available", "Invisible", "Settings"),
        "message-options" to listOf("Set Recipient", "Send Message", "Edit Message", "Add Recipient", "Add Cc Recipient", "Show/Hide CC Recipients", "Show/Hide Recipients", "Add Emoticon", "Send Whisper"),
        "messenger-options" to listOf("Send New Message", "Reply All", "View History", "Received Contacts", "Profile", "Accept Buddy Invite", "Reject Buddy Invite", "Send Message", "Close Tab"),
        "chat-room-actions" to listOf("Send Message", "Send Whisper", "List Participants", "Invite Participants", "Leave Chatroom"),
        "billing-options" to listOf("Current Billing Status", "Purchase a Package", "Auto-Renew", "View Purchase History"),
        "offline-options" to listOf("Available SMS", "Email", "Store on server")
    )
    var selected by remember { mutableIntStateOf(0) }
    var open by remember { mutableStateOf(false) }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Legacy Menus", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                entries.forEachIndexed { index, entry ->
                    LegacyText(entry.first, Modifier.fillMaxWidth().background(if (index == selected) ReptilianTheme.MenuSelected else ReptilianTheme.Surface).clickable { selected = index; open = true }.padding(horizontal = 8.dp, vertical = 6.dp))
                }
            }
            LegacyFunctionBar(leftLabel = "Options", rightLabel = "Menu", modifier = Modifier.fillMaxWidth().clickable { open = true })
        }
    }
    if (open) {
        val entry = entries[selected]
        Dialog(onDismissRequest = { open = false }) {
            Column(Modifier.background(ReptilianTheme.Surface)) {
                entry.second.forEach { item -> LegacyText(item, Modifier.fillMaxWidth().clickable { open = false; onSelect(item) }.padding(horizontal = 18.dp, vertical = 9.dp)) }
                LegacyText("Close", Modifier.fillMaxWidth().clickable { open = false }.padding(horizontal = 18.dp, vertical = 9.dp))
            }
        }
    }
}
