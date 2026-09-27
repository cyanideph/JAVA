package com.cyanideph.java.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.ui.*

@Composable
fun ChatRoomScreen(room: String, onBack: () -> Unit, onBuddies: () -> Unit) {
    var showOptions by remember { mutableStateOf(false) }
    var showParticipants by remember { mutableStateOf(false) }
    var joined by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("") }
    var participants by remember { mutableStateOf(emptyList<String>()) }

    val actions = buildList {
        if (joined) {
            add("Send Message")
            if (participants.isNotEmpty()) add("Send Whisper")
            add("List Participants")
            add("Invite Participants")
        }
        add("Leave Chatroom")
    }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Chat - $room", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth().padding(6.dp)) {
                if (status.isNotEmpty()) {
                    LegacyText(status)
                    Spacer(Modifier.height(4.dp))
                }
                if (joined) {
                    LegacyText("- No of Chatters: " + (participants.size + 1))
                    participants.forEach { LegacyText("- " + it) }
                    LegacyText("-")
                }
            }
            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Buddies",
                modifier = Modifier.fillMaxWidth(),
                onLeftClick = { showOptions = true },
                onRightClick = onBuddies
            )
        }
    }

    if (showOptions) Dialog(onDismissRequest = { showOptions = false }) {
        LegacyFrame(Modifier.fillMaxWidth().padding(16.dp)) {
            Column(Modifier.fillMaxWidth()) {
                actions.forEach { action ->
                    LegacyText(action, Modifier.fillMaxWidth().clickable {
                        showOptions = false
                        when (action) {
                            "Send Message" -> status = ""
                            "Send Whisper" -> status = ""
                            "List Participants" -> showParticipants = true
                            "Invite Participants" -> status = ""
                            "Leave Chatroom" -> { joined = false; participants = emptyList() }
                        }
                    }.padding(horizontal = 8.dp, vertical = 7.dp))
                }
            }
        }
    }

    if (showParticipants) LegacyDialogMessage(
        "- No of Chatters: " + (participants.size + 1) + "\n" + participants.joinToString("\n") { "- " + it } + "\n-"
    ) { showParticipants = false }
}
