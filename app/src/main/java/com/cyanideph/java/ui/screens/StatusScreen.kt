package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

@Composable
fun StatusScreen(onBack: () -> Unit, onSettings: () -> Unit = {}) {
    val context = LocalContext.current
    var selected by remember { mutableStateOf("Available") }
    var message by remember { mutableStateOf("") }
    var showStatusMessage by remember { mutableStateOf(false) }

    data class Item(val label: String, val asset: String, val action: () -> Unit)
    val items = listOf(
        Item("Status Message..", "statusmessage") { showStatusMessage = true },
        Item("Available", "online") { selected = "Available" },
        Item("Not Available", "notavailable") { selected = "Not Available" },
        Item("Invisible", "offline") { selected = "Invisible" },
        Item("Settings..", "settings") { onSettings() }
    )

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Change Status", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                items.forEach { item ->
                    Row(
                        Modifier.fillMaxWidth().clickable { item.action() }
                            .background(if (item.label == selected) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                            .padding(horizontal = 8.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            LegacyAssets.rememberBitmap(context, "themes/default/" + item.asset + ".png"),
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        LegacyText(item.label)
                    }
                }
            }
            LegacyFunctionBar(leftLabel = "Options", rightLabel = "Back", modifier = Modifier.fillMaxWidth(), onRightClick = onBack)
        }
    }

    if (showStatusMessage) {
        LegacyAdaptiveDialog(onDismissRequest = { showStatusMessage = false }) {
            LegacyFrame(Modifier.fillMaxWidth().padding(16.dp)) {
                Column(Modifier.fillMaxWidth().padding(10.dp)) {
                    LegacyText("Your status message")
                    LegacyFormList(
                        fields = listOf(
                            LegacyFormField("message", "Status Message", "Status Message", "Enter your status message.", 100)
                        ),
                        values = mapOf("message" to message),
                        onValueChange = { _, value -> message = value },
                        modifier = Modifier.fillMaxWidth()
                    )
                    LegacyFunctionBar(modifier = Modifier.fillMaxWidth(), leftLabel = "OK", rightLabel = "Cancel", onLeftClick = { showStatusMessage = false }, onRightClick = { showStatusMessage = false })
                }
            }
        }
    }
}
