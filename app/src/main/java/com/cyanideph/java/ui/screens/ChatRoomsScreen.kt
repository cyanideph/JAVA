package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*
import com.cyanideph.java.ui.model.ChatRoom

@Composable
fun ChatRoomsScreen(onBack: () -> Unit, onRoom: (String) -> Unit) {
    // Room records are service-provided in the legacy client; do not invent visible rooms.
    val rooms = emptyList<ChatRoom>()
    var selected by remember { mutableIntStateOf(0) }
    var showOptions by remember { mutableStateOf(false) }
    var inCategory by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }
    var searchError by remember { mutableStateOf<String?>(null) }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Chat Rooms", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                if (searchError != null) {
                    LegacyText(searchError!!, Modifier.padding(6.dp))
                } else if (loading) {
                    LegacyText(if (inCategory) "Getting Rooms.." else "Getting Categories..", Modifier.padding(6.dp))
                } else if (rooms.isEmpty()) {
                    LegacyText("No chat rooms", Modifier.padding(6.dp))
                } else {
                    rooms.forEachIndexed { index, room ->
                        LegacyText(
                            room.name + " (" + room.participants + ")",
                            Modifier.fillMaxWidth()
                                .background(if (index == selected) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                                .clickable { selected = index }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        )
                    }
                }
            }
            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = if (inCategory) "Back" else "Menu",
                modifier = Modifier.fillMaxWidth(),
                onLeftClick = { showOptions = true },
                onRightClick = { if (inCategory) { inCategory = false; loading = false; searchError = null } else onBack }
            )
        }
    }

    if (showOptions) {
        Dialog(onDismissRequest = { showOptions = false }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
            Column(Modifier.background(ReptilianTheme.Surface)) {
                LegacyText(
                    if (rooms.isEmpty()) if (inCategory) "Join Room" else "Select Category" else "Join Room",
                    Modifier.fillMaxWidth().clickable {
                        showOptions = false
                        if (rooms.isNotEmpty()) onRoom(rooms[selected].name) else { inCategory = true; loading = true }
                    }.padding(horizontal = 18.dp, vertical = 9.dp)
                )
                LegacyText(
                    if (rooms.isEmpty()) if (inCategory) "Refresh Room List" else "Refresh Category List" else "Refresh Room List",
                    Modifier.fillMaxWidth().clickable { showOptions = false; loading = true }
                        .padding(horizontal = 18.dp, vertical = 9.dp)
                )
                LegacyText(
                    "Start Buddy Group Chat",
                    Modifier.fillMaxWidth().clickable { showOptions = false }
                        .padding(horizontal = 18.dp, vertical = 9.dp)
                )
                LegacyText(
                    "Close Tab",
                    Modifier.fillMaxWidth().clickable {
                        showOptions = false
                        onBack()
                    }.padding(horizontal = 18.dp, vertical = 9.dp)
                )
            }
        }
    }
}
