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
fun SettingsScreen(onBack: () -> Unit) {
    var showOptions by remember { mutableStateOf(false) }
    val options = listOf(
        "Edit My Profile",
        "Change Status",
        "Change Password",
        "Change Mobile Number",
        "Offline Settings",
        "Chatroom Tones"
    )

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Settings", Modifier.fillMaxWidth())
            Column(
                Modifier.weight(1f).fillMaxWidth()
            ) {
                options.forEach { label ->
                    LegacyText(
                        label,
                        Modifier
                            .fillMaxWidth()
                            .background(ReptilianTheme.Surface)
                            .clickable { showOptions = true }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                }
            }
            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Menu",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showOptions = true }
            )
        }
    }

    if (showOptions) {
        Dialog(onDismissRequest = { showOptions = false }) {
            Column(Modifier.background(ReptilianTheme.Surface)) {
                options.forEach { label ->
                    LegacyText(
                        label,
                        Modifier.fillMaxWidth()
                            .clickable { showOptions = false }
                            .padding(horizontal = 18.dp, vertical = 9.dp)
                    )
                }
                LegacyText(
                    "Close",
                    Modifier.fillMaxWidth()
                        .clickable {
                            showOptions = false
                            onBack()
                        }
                        .padding(horizontal = 18.dp, vertical = 9.dp)
                )
            }
        }
    }
}
