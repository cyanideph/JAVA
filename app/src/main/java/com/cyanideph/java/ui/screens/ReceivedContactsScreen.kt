package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*
import com.cyanideph.java.ui.model.LegacyBuddyRepository

@Composable
fun ReceivedContactsScreen(onBack: () -> Unit) {
    val contacts = LegacyBuddyRepository.receivedContacts
    var selected by remember { mutableStateOf<Set<String>>(emptySet()) }
    var showOptions by remember { mutableStateOf(false) }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Received Contacts", Modifier.fillMaxWidth())
            LazyColumn(
                Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 2.dp)
            ) {
                items(contacts) { contact ->
                    val checked = contact.id in selected
                    Row(
                        Modifier.fillMaxWidth()
                            .background(if (checked) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                            .clickable {
                                selected = if (checked) selected - contact.id else selected + contact.id
                            }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                    ) {
                        LegacyCheckbox(checked, onCheckedChange = {
                            selected = if (it) selected + contact.id else selected - contact.id
                        })
                        Spacer(Modifier.width(6.dp))
                        LegacyText(contact.displayName)
                    }
                }
            }
            Row(Modifier.fillMaxWidth()) {
                Box(Modifier.weight(1f).clickable { showOptions = true }) {
                    LegacyText("Options", Modifier.padding(8.dp))
                }
                Box(Modifier.weight(1f).clickable { onBack() }) {
                    LegacyText("Close", Modifier.padding(8.dp))
                }
            }
            LegacyFunctionBar(Modifier.fillMaxWidth())
        }
    }

    if (showOptions) {
        Dialog(onDismissRequest = { showOptions = false }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
            Column(Modifier.background(ReptilianTheme.Surface)) {
                LegacyText("Save selected contacts", Modifier.fillMaxWidth().clickable {
                    showOptions = false
                }.padding(horizontal = 18.dp, vertical = 9.dp))
                LegacyText("Discard all", Modifier.fillMaxWidth().clickable {
                    selected = emptySet()
                    showOptions = false
                }.padding(horizontal = 18.dp, vertical = 9.dp))
                LegacyText("Close", Modifier.fillMaxWidth().clickable {
                    showOptions = false
                }.padding(horizontal = 18.dp, vertical = 9.dp))
            }
        }
    }
}
