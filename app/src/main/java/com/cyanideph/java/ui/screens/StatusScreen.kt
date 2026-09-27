package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

/**
 * Java parity trace:
 * an.java -> case 103 -> menu id "statuses":
 *   4 = Status Message..
 *   1 = Available
 *   2 = Not Available
 *   3 = Invisible
 *
 * The Settings-screen status menu (aa.java) uses different numeric IDs and is
 * intentionally not merged here; this screen represents the main-menu path.
 */
@Composable
fun StatusScreen(onBack: () -> Unit) {
    var selected by remember { mutableStateOf("Available") }
    var message by remember { mutableStateOf("") }
    var showOptions by remember { mutableStateOf(false) }
    var showStatusMessage by remember { mutableStateOf(false) }

    val states = listOf("Available", "Not Available", "Invisible")

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Change Status", Modifier.fillMaxWidth())
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                LegacyText("Status: $selected")
                Spacer(Modifier.height(6.dp))
                LegacyText("Status Message: " + message.ifBlank { "(none)" })
            }
            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Back",
                modifier = Modifier.fillMaxWidth().clickable { showOptions = true }
            )
        }
    }

    if (showOptions) {
        Dialog(onDismissRequest = { showOptions = false }) {
            Column(Modifier.background(ReptilianTheme.Surface)) {
                LegacyText(
                    "Status Message..",
                    Modifier.fillMaxWidth().clickable {
                        showOptions = false
                        showStatusMessage = true
                    }.padding(horizontal = 18.dp, vertical = 9.dp)
                )
                states.forEach { state ->
                    LegacyText(
                        state,
                        Modifier.fillMaxWidth().clickable {
                            selected = state
                            showOptions = false
                        }.padding(horizontal = 18.dp, vertical = 9.dp)
                    )
                }
            }
        }
    }

    if (showStatusMessage) {
        Dialog(onDismissRequest = { showStatusMessage = false }) {
            Column(
                Modifier
                    .background(ReptilianTheme.Surface)
                    .padding(10.dp)
            ) {
                LegacyText("Your status message")
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 4
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = { showStatusMessage = false }) {
                        LegacyText("OK")
                    }
                    TextButton(onClick = { showStatusMessage = false }) {
                        LegacyText("Cancel")
                    }
                }
            }
        }
    }
}
