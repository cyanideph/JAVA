package com.cyanideph.java.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.ui.*

@Composable
fun ChatRoomScreen(room: String, onBack: () -> Unit) {
    var showOptions by remember { mutableStateOf(false) }
    var showParticipants by remember { mutableStateOf(false) }
    var joined by remember { mutableStateOf(true) }
    var status by remember { mutableStateOf("") }
    var participants by remember { mutableStateOf(listOf("You")) }

    val actions = buildList {
        if (joined) {
            add("Send Message")
            if (participants.size > 1) add("Send Whisper")
            add("List Participants")
            add("Invite Participants")
        }
        add("Leave Chatroom")
    }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Chat - " + room, Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth().padding(6.dp)) {
                LegacyText(if (joined) "You are in '" + room + "'." else "You have left '" + room + "'.")
                if (status.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    LegacyText(status)
                }
                Spacer(Modifier.height(6.dp))
                LegacyText("- No of Chatters: " + participants.size)
                participants.drop(1).forEach { LegacyText("- " + it) }
            }
            LegacyFunctionBar(leftLabel = "Options", rightLabel = "Buddies", modifier = Modifier.fillMaxWidth().clickable { showOptions = true })
        }
    }

    if (showOptions) Dialog(onDismissRequest = { showOptions = false }) {
        LegacyFrame(Modifier.fillMaxWidth().padding(16.dp)) {
            Column(Modifier.fillMaxWidth()) {
                actions.forEach { action ->
                    LegacyText(action, Modifier.fillMaxWidth().clickable {
                        showOptions = false
                        when (action) {
                            "Send Message" -> status = "- Message editor requires the legacy message transport."
                            "Send Whisper" -> status = "- Whisper requires the legacy message transport."
                            "List Participants" -> showParticipants = true
                            "Invite Participants" -> status = "- Invite Participants requires the legacy service transport."
                            "Leave Chatroom" -> { joined = false; participants = listOf("You"); onBack() }
                        }
                    }.padding(horizontal = 8.dp, vertical = 7.dp))
                }
            }
        }
    }

    if (showParticipants) LegacyDialogMessage(
        "- No of Chatters: " + participants.size + "\n" + participants.joinToString("\n") { "- " + it }
    ) { showParticipants = false }
}
