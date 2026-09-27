package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

@Composable
fun HistoryScreen(
    onBack: () -> Unit,
    contactName: String = "Friend 1"
) {
    val messages = listOf(
        "- $contactName (12:00): Message history is displayed here.",
        "- $contactName (12:05): Legacy history uses the message renderer.",
        "* $contactName (12:10): Older messages remain in chronological history."
    )
    var showOptions by remember { mutableStateOf(false) }
    var showMessage by remember { mutableStateOf(false) }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("History - $contactName", Modifier.fillMaxWidth())

            LazyColumn(
                Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(messages) { message ->
                    LegacyText(
                        message,
                        Modifier
                            .fillMaxWidth()
                            .background(ReptilianTheme.Surface)
                            .padding(horizontal = 4.dp, vertical = 3.dp)
                            .clickable { showMessage = true }
                    )
                }
            }

            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Buddies",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showOptions = true }
            )
        }
    }

    if (showOptions) {
        Dialog(onDismissRequest = { showOptions = false }) {
            Column(Modifier.background(ReptilianTheme.Surface)) {
                LegacyText(
                    "Open Message",
                    Modifier.fillMaxWidth().clickable {
                        showOptions = false
                        showMessage = true
                    }.padding(horizontal = 18.dp, vertical = 9.dp)
                )
                LegacyText(
                    "Send New Message",
                    Modifier.fillMaxWidth().clickable {
                        showOptions = false
                    }.padding(horizontal = 18.dp, vertical = 9.dp)
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

    if (showMessage) {
        AlertDialog(
            onDismissRequest = { showMessage = false },
            title = { LegacyText("Open Message") },
            text = { LegacyText(messages.firstOrNull().orEmpty()) },
            confirmButton = {
                TextButton(onClick = { showMessage = false }) { LegacyText("OK") }
            }
        )
    }
}
