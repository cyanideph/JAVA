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
fun StatusScreen(onBack: () -> Unit) {
    var selected by remember { mutableStateOf("Available") }
    var message by remember { mutableStateOf("") }
    var showOptions by remember { mutableStateOf(false) }

    val states = listOf("Available", "Not Available", "Invisible")

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Change Status", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth().padding(6.dp)) {
                LegacyText("Status: ${selected}")
                Spacer(Modifier.height(6.dp))
                LegacyText("Status Message: ${message.ifBlank { "(none)" }}")
            }
            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Menu",
                modifier = Modifier.fillMaxWidth().clickable { showOptions = true }
            )
        }
    }

    if (showOptions) {
        Dialog(onDismissRequest = { showOptions = false }) {
            Column(Modifier.background(ReptilianTheme.Surface)) {
                LegacyText("Status Message..", Modifier.fillMaxWidth().clickable { showOptions = false }.padding(horizontal = 18.dp, vertical = 9.dp))
                states.forEach { state ->
                    LegacyText(state, Modifier.fillMaxWidth().clickable {
                        selected = state
                        showOptions = false
                    }.padding(horizontal = 18.dp, vertical = 9.dp))
                }
                LegacyText("Settings..", Modifier.fillMaxWidth().clickable { showOptions = false }.padding(horizontal = 18.dp, vertical = 9.dp))
                LegacyText("Close", Modifier.fillMaxWidth().clickable {
                    showOptions = false
                    onBack()
                }.padding(horizontal = 18.dp, vertical = 9.dp))
            }
        }
    }
}
