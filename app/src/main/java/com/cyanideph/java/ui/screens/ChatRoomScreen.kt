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
fun ChatRoomScreen(room: String, onBack: () -> Unit) {
    var showOptions by remember { mutableStateOf(false) }
    var joined by remember { mutableStateOf(true) }

    val actions = buildList {
        if (joined) {
            add("Send Message")
            add("Send Whisper")
            add("List Participants")
            add("Invite Participants")
        }
        add("Leave Chatroom")
    }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Chat - $room", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth().padding(6.dp)) {
                LegacyText(if (joined) "You are in '$room'." else "You have left '$room'.")
                Spacer(Modifier.height(6.dp))
                LegacyText("- No of Chatters: 1")
            }
            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Buddies",
                modifier = Modifier.fillMaxWidth().clickable { showOptions = true }
            )
        }
    }

    if (showOptions) {
        Dialog(onDismissRequest = { showOptions = false }) {
            Column(Modifier.background(ReptilianTheme.Surface)) {
                actions.forEach { action ->
                    LegacyText(
                        action,
                        Modifier.fillMaxWidth().clickable {
                            showOptions = false
                            if (action == "Leave Chatroom") {
                                joined = false
                                onBack()
                            }
                        }.padding(horizontal = 18.dp, vertical = 9.dp)
                    )
                }
            }
        }
    }
}
