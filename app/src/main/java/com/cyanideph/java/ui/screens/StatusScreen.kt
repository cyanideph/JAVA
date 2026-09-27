package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

/**
 * Java parity:
 * an.java -> case 103 -> menu id "statuses":
 * 4 Status Message.. / statusmessage
 * 1 Available / online
 * 2 Not Available / notavailable
 * 3 Invisible / offline
 *
 * This is the main-menu status submenu. Settings/aa.java remains separate.
 */
@Composable
fun StatusScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var selected by remember { mutableStateOf("Available") }
    var message by remember { mutableStateOf("") }
    var showStatusMessage by remember { mutableStateOf(false) }

    data class Item(val label: String, val asset: String, val action: () -> Unit)
    val items = listOf(
        Item("Status Message..", "statusmessage") { showStatusMessage = true },
        Item("Available", "online") { selected = "Available" },
        Item("Not Available", "notavailable") { selected = "Not Available" },
        Item("Invisible", "offline") { selected = "Invisible" }
    )

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Change Status", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                items.forEach { item ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { item.action() }
                            .background(if (item.label == selected) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                            .padding(horizontal = 8.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val bitmap = LegacyAssets.rememberBitmap(
                            context,
                            "themes/default/" + item.asset + ".png"
                        )
                        Image(bitmap, contentDescription = item.label, Modifier.size(24.dp))
                        Spacer(Modifier.width(8.dp))
                        LegacyText(item.label)
                    }
                }
            }
            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Back",
                modifier = Modifier.fillMaxWidth().clickable { onBack() }
            )
        }
    }

    if (showStatusMessage) {
        Dialog(onDismissRequest = { showStatusMessage = false }) {
            Column(Modifier.background(ReptilianTheme.Surface).padding(10.dp)) {
                LegacyText("Your status message")
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it.take(100) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 4
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = { showStatusMessage = false }) { LegacyText("OK") }
                    TextButton(onClick = { showStatusMessage = false }) { LegacyText("Cancel") }
                }
            }
        }
    }
}
