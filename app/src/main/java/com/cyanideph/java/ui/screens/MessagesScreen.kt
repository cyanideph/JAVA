package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*
import com.cyanideph.java.ui.model.Message

private data class LegacyMessageTab(val title: String)

@Composable
fun MessagesScreen(onBack: () -> Unit) {
    val tabs = remember { listOf(LegacyMessageTab("Buddy List"), LegacyMessageTab("Message"), LegacyMessageTab("Chat")) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var showOptions by remember { mutableStateOf(false) }
    var messageText by remember { mutableStateOf("") }
    var recipient by remember { mutableStateOf("Friend 1") }
    var cc by remember { mutableStateOf("") }
    var hideRecipients by remember { mutableStateOf(false) }
    var showEditor by remember { mutableStateOf(false) }
    var recipientMode by remember { mutableStateOf(false) }
    var ccMode by remember { mutableStateOf(false) }
    var showEmoticons by remember { mutableStateOf(false) }

    val messages = remember { listOf(
        Message("cy", "Welcome to Uzzap", "now", false),
        Message("Friend 1", "Hello!", "now", false)
    ) }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar(
                when (selectedTab) {
                    1 -> "EM - Friend 1"
                    2 -> "Chat Room"
                    else -> "Instant Messaging"
                },
                Modifier.fillMaxWidth()
            )
            LegacyTabStrip(
                tabs = tabs.map { it.title },
                selected = selectedTab,
                onSelected = { selectedTab = it },
                modifier = Modifier.fillMaxWidth()
            )
            LegacyComposerPreview(messageText, recipient, cc, hideRecipients)
            LegacyMessageList(messages, Modifier.weight(1f))
            Row(Modifier.fillMaxWidth()) {
                Box(Modifier.weight(1f).clickable { showOptions = true }) {
                    LegacyText("Options", Modifier.padding(8.dp))
                }
                Box(Modifier.weight(1f).clickable { onBack() }) {
                    LegacyText("Menu", Modifier.padding(8.dp))
                }
            }
            LegacyFunctionBar(Modifier.fillMaxWidth())
        }
    }

    if (showOptions) {
        LegacyMessageOptions(
            onDismiss = { showOptions = false },
            onEdit = { showOptions = false; showEditor = true },
            onRecipient = { showOptions = false; recipientMode = true; ccMode = false },
''            onToggleRecipients = { hideRecipients = !hideRecipients; showOptions = false },
            onEmoticon = { showOptions = false; showEmoticons = true }
        )
    }
    if (showEditor) {
        LegacyEditorDialog(messageText, { messageText = it.take(700) }) { showEditor = false }
    }
    if (recipientMode || ccMode) {
        LegacyRecipientDialog(
            title = if (ccMode) "Cc Recipient" else "Recipient",
            onPick = {
                if (ccMode) cc = it else recipient = it
                recipientMode = false
                ccMode = false
            },
            onDismiss = { recipientMode = false; ccMode = false }
        )
    }
    if (showEmoticons) {
        LegacyEmoticonDialog(
            onPick = { messageText = (messageText + it).take(700); showEmoticons = false },
            onDismiss = { showEmoticons = false }
        )
    }
}

@Composable
private fun LegacyMessageList(messages: List<Message>, modifier: Modifier = Modifier) {
    LazyColumn(modifier.padding(horizontal = 4.dp), contentPadding = PaddingValues(vertical = 3.dp)) {
        items(messages) { MessageRow(it) }
    }
}

@Composable
private fun MessageRow(message: Message) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val icon = LegacyAssets.rememberBitmap(context, if (message.outgoing) "sending-message-icon" else "message")
    Row(
        Modifier.fillMaxWidth().background(ReptilianTheme.Surface).padding(horizontal = 5.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(icon, null, Modifier.size(with(density) { icon.width.toDp() }, with(density) { icon.height.toDp() }), contentScale = ContentScale.None)
        Spacer(Modifier.width(5.dp))
        Column(Modifier.weight(1f)) {
            LegacyText(message.sender)
            LegacyText(message.body)
        }
        LegacyText(message.time)
    }
}

@Composable
private fun LegacyMessageOptions(
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onRecipient: () -> Unit,
    onCc: () -> Unit,
    onToggleRecipients: () -> Unit,
    onEmoticon: () -> Unit
) {
    val options = listOf(
        "Send Message" to onDismiss,
        "Edit Message" to onEdit,
        "Set Recipient" to onRecipient,
        "Add Recipient" to onRecipient,
        "Show/Hide Recipients" to onToggleRecipients,
        "Add Emoticon" to onEmoticon
    )
    Dialog(onDismissRequest = onDismiss) {
        Column(Modifier.background(ReptilianTheme.Surface)) {
            options.forEach { (label, action) ->
                LegacyText(label, Modifier.fillMaxWidth().clickable(onClick = action).padding(horizontal = 18.dp, vertical = 9.dp))
            }
        }
    }
}

@Composable
private fun LegacyEditorDialog(value: String, onValue: (String) -> Unit, onClose: () -> Unit) {
    Dialog(onDismissRequest = onClose) {
        Column(Modifier.background(ReptilianTheme.Surface).padding(10.dp)) {
            LegacyText("Type your message")
            OutlinedTextField(value, onValue, Modifier.fillMaxWidth(), maxLines = 8)
            Row {
                LegacyText("OK", Modifier.clickable(onClick = onClose).padding(10.dp))
                LegacyText("Cancel", Modifier.clickable(onClick = onClose).padding(10.dp))
            }
        }
    }
}

@Composable
private data class LegacyContact(val name: String, val statusAsset: String)

@Composable
private fun LegacyRecipientDialog(
    onPick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val contacts = remember {
        listOf(
            LegacyContact("cy", "online"),
            LegacyContact("Friend 1", "online"),
            LegacyContact("Friend 2", "offline"),
            LegacyContact("Friend 3", "notavailable")
        ).sortedBy { it.name.lowercase() }
    }
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<Set<String>>(emptySet()) }
    val filtered = contacts.filter { it.name.contains(query.trim(), ignoreCase = true) }

    Dialog(onDismissRequest = onDismiss) {
        Column(Modifier.background(ReptilianTheme.Surface).padding(6.dp)) {
            LegacyText("Recipient", Modifier.padding(6.dp))
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Row {
                LegacyText(
                    "Select All",
                    Modifier.clickable { selected = filtered.map { it.name }.toSet() }.padding(8.dp)
                )
                LegacyText(
                    "Clear",
                    Modifier.clickable { selected = emptySet() }.padding(8.dp)
                )
            }
            filtered.forEach { contact ->
                val checked = contact.name in selected
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            selected = if (checked) selected - contact.name else selected + contact.name
                        }
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val bitmap = LegacyAssets.rememberBitmap(LocalContext.current, contact.statusAsset)
                    val density = LocalDensity.current
                    Image(
                        bitmap,
                        null,
                        Modifier.size(
                            with(density) { bitmap.width.toDp() },
                            with(density) { bitmap.height.toDp() }
                        ),
                        contentScale = ContentScale.None
                    )
                    Spacer(Modifier.width(5.dp))
                    LegacyCheckbox(checked)
                    Spacer(Modifier.width(5.dp))
                    LegacyText(contact.name)
                }
            }
            Row {
                LegacyText(
                    "OK",
                    Modifier.clickable {
                        if (selected.isNotEmpty()) onPick(selected.take(3).joinToString(", ")) else onDismiss()
                    }.padding(10.dp)
                )
                LegacyText(
                    "Cancel",
                    Modifier.clickable(onClick = onDismiss).padding(10.dp)
                )
            }
        }
    }
}

@Composable
private fun LegacyEmoticonDialog(onPick: (String) -> Unit, onDismiss: () -> Unit) {
    val entries = listOf(
        ":)" to "emoticon-smile", ";)" to "emoticon-wink", ":(" to "emoticon-sad", ":D" to "emoticon-laugh",
        ":e" to "emoticon-e", "(:)" to "emoticon-love", ">|" to "emoticon-angry", ":o" to "emoticon-surprise",
        ":>" to "emoticon-tongue", ">(|" to "emoticon-cry", ":DD" to "emoticon-11", "o/" to "emoticon-12",
        ":Oo" to "emoticon-13", ">," to "emoticon-14", ":|" to "emoticon-15", ":B," to "emoticon-16",
        ":OOo" to "emoticon-17", ":Zz." to "emoticon-18", "O:)" to "emoticon-19", "))(" to "emoticon-20",
        ">><)" to "emoticon-47", "<:D" to "emoticon-clown", "(cU)" to "emoticon-drink", "<:)" to "emoticon-party",
        "(+)" to "emoticon-sick", "@};-" to "emoticon-rose"
    )
    Dialog(onDismissRequest = onDismiss) {
        Column(Modifier.background(ReptilianTheme.Surface).padding(6.dp)) {
            entries.chunked(4).forEach { row ->
                Row {
                    row.forEach { (code, asset) ->
                        val bitmap = LegacyAssets.rememberBitmap(LocalContext.current, asset)
                        Image(bitmap, code, Modifier.padding(3.dp).clickable { onPick(code) }, contentScale = ContentScale.None)
                    }
                }
            }
        }
    }
}

@Composable
private fun LegacyComposerPreview(message: String, recipient: String, cc: String, hidden: Boolean) {
    val to = if (recipient.isBlank()) "(no recipient set)" else recipient
    val ccLine = if (cc.isNotBlank() && !hidden) "\n- Cc: $cc" else ""
    val preview = if (message.length <= 15) message else message.take(13) + ".."
    LegacyText("- To: $to$ccLine\n$preview", Modifier.padding(8.dp))
}
