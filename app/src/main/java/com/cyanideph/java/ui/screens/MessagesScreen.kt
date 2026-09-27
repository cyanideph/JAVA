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
import com.cyanideph.java.ui.model.Buddy
import com.cyanideph.java.ui.model.LegacyBuddyRepository
import com.cyanideph.java.ui.model.Message

private data class LegacyMessageTab(val title: String)

private fun legacyMessageContacts(messageType: String): List<Buddy> = when (messageType) {
    "yahoo" -> LegacyBuddyRepository.buddies.filter { it.yahooId != null }
    "msn" -> LegacyBuddyRepository.buddies.filter { it.msnId != null }
    "email" -> LegacyBuddyRepository.buddies.filter { it.email != null }
    "sms", "smsr", "random" -> LegacyBuddyRepository.buddies.filter { it.mobile != null }
    else -> LegacyBuddyRepository.buddies
}

private fun legacyMessengerTitle(messageType: String, recipient: String): String {
    val display = recipient.ifBlank { "Friend 1" }
    return when (messageType) {
        "__abm__" -> "New buddies"
        "chatroom" -> "Chat Room"
        "sms", "smsr" -> "SMS - $display"
        "email" -> "Email - $display"
        "im" -> "EM - $display"
        "yahoo" -> "Y! - $display"
        "msn" -> "MSN - $display"
        else -> "EM - $display"
    }
}


@Composable
fun MessagesScreen(
    onBack: () -> Unit,
    onViewHistory: () -> Unit = {},
    onReceivedContacts: () -> Unit = {},
    onProfile: () -> Unit = {},
    isBuddyInvite: Boolean = false,
    onAcceptBuddyInvite: () -> Unit = {},
    onRejectBuddyInvite: () -> Unit = {}
) {
    val tabs = remember { listOf(LegacyMessageTab("Buddy List"), LegacyMessageTab("Message"), LegacyMessageTab("Chat")) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var showOptions by remember { mutableStateOf(false) }
    var showMessengerOptions by remember { mutableStateOf(false) }
    var messageText by remember { mutableStateOf("") }
    var recipient by remember { mutableStateOf("") }
    var cc by remember { mutableStateOf("") }
    var hideRecipients by remember { mutableStateOf(false) }
    var showEditor by remember { mutableStateOf(false) }
    var recipientMode by remember { mutableStateOf(false) }
    var showEmoticons by remember { mutableStateOf(false) }
    var recipientPickerMode by remember { mutableStateOf("to") }
    var messageType by remember { mutableStateOf(if (selectedTab == 2) "chatroom" else "im") }
    val editorLimit = if (messageType == "chatroom") 160 else 700
    val recipientCount = recipient.split(",").map { it.trim() }.count { it.isNotBlank() && !it.equals(",,,,", ignoreCase = false) }

    val messages = remember { listOf(
        Message("cy", "Welcome to Uzzap", "now", false),
        Message("Friend 1", "Hello!", "now", false)
    ) }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar(
                legacyMessengerTitle(messageType, recipient),
                Modifier.fillMaxWidth()
            )
            LegacyTabStrip(
                tabs = tabs.map { it.title },
                selected = selectedTab,
                onSelected = { selectedTab = it; messageType = if (it == 2) "chatroom" else "im" },
                modifier = Modifier.fillMaxWidth()
            )
            if (isBuddyInvite) {
                LegacyText(
                    "[This user has invited you to their buddy list. To accept the invitation, choose "Accept Buddy Invite" from the options menu]\nYou can chat with the user in this window before you accept to confirm who they are.]",
                    Modifier.padding(horizontal = 5.dp, vertical = 4.dp)
                )
            }
            LegacyComposerPreview(messageText, messageType, recipient, cc, hideRecipients)
            LegacyMessageList(messages, Modifier.weight(1f))
            Row(Modifier.fillMaxWidth()) {
                Box(Modifier.weight(1f).clickable { showOptions = true }) {
                    LegacyText("Options", Modifier.padding(8.dp))
                }
                Box(Modifier.weight(1f).clickable { showMessengerOptions = true }) {
                    LegacyText("Buddies", Modifier.padding(8.dp))
                }
            }
            LegacyFunctionBar(Modifier.fillMaxWidth())
        }
    }

    if (showOptions) {
        LegacyMessageOptions(
            onDismiss = { showOptions = false },
            hasRecipient = recipient.isNotBlank(),
            hasMultipleRecipients = recipientCount > 1,
            onEdit = { showOptions = false; showEditor = true },
            onRecipient = { showOptions = false; recipientPickerMode = "to"; recipientMode = true },
            messageType = messageType,
            onToggleRecipients = { hideRecipients = !hideRecipients; showOptions = false },
            onCcRecipient = { showOptions = false; recipientPickerMode = "cc"; recipientMode = true },
            onEmoticon = { showOptions = false; showEmoticons = true }
        )
    }
    if (showEditor) {
        LegacyEditorDialog(messageText, { messageText = it.take(editorLimit) }, editorLimit) { showEditor = false }
    }
    if (recipientMode) {
        LegacyRecipientDialog(
            contacts = legacyMessageContacts(messageType),
            onPick = { names ->
                if (recipientPickerMode == "cc") cc = names else recipient = names
                recipientMode = false
            },
            onDismiss = { recipientMode = false }
        )
    }
    if (showEmoticons) {
        LegacyEmoticonDialog(
            onPick = { messageText = (messageText + it).take(editorLimit); showEmoticons = false },
            onDismiss = { showEmoticons = false }
        )
    }
    if (showMessengerOptions) {
        LegacyMessengerOptions(
            messageType = messageType,
            hasRecipient = recipient.isNotBlank(),
            isBuddyInvite = isBuddyInvite,
            onSendNewMessage = { showMessengerOptions = false; showEditor = true },
            onAcceptBuddyInvite = { showMessengerOptions = false; onAcceptBuddyInvite() },
            onRejectBuddyInvite = { showMessengerOptions = false; onRejectBuddyInvite(); onBack() },
            onReplyAll = {
                val recipients = recipient.split(",").map { it.trim() }.filter { it.isNotBlank() && it != ",,,," }
                recipient = recipients.drop(1).take(3).joinToString(", ") + if (recipients.size > 4) ",,,," else ""
                showMessengerOptions = false
                showEditor = true
            },
            onViewHistory = { showMessengerOptions = false; onViewHistory() },
            onReceivedContacts = { showMessengerOptions = false; onReceivedContacts() },
            onProfile = { showMessengerOptions = false; onProfile() },
            onCloseTab = { showMessengerOptions = false; onBack() }
        )
    }
}



@Composable
private fun LegacyMessengerOptions(
    messageType: String,
    hasRecipient: Boolean,
    hasMultipleRecipients: Boolean,
    isBuddyInvite: Boolean,
    onSendNewMessage: () -> Unit,
    onAcceptBuddyInvite: () -> Unit,
    onRejectBuddyInvite: () -> Unit,
    onReplyAll: () -> Unit,
    onViewHistory: () -> Unit,
    onReceivedContacts: () -> Unit,
    onProfile: () -> Unit,
    onCloseTab: () -> Unit
) {
    val options = buildList<Pair<String, () -> Unit>> {
        if (isBuddyInvite) {
            add("Accept Buddy Invite" to onAcceptBuddyInvite)
            add("Reject Buddy Invite" to onRejectBuddyInvite)
            add("Send Message" to onSendNewMessage)
        } else {
            add("Send New Message" to onSendNewMessage)
            if (hasRecipient) {
                if (hasMultipleRecipients && messageType != "yahoo" && messageType != "msn") add("Reply All" to onReplyAll)
                add("View History" to onViewHistory)
                add("Received Contacts" to onReceivedContacts)
                add("Profile" to onProfile)
            }
        }
        add("Close Tab" to onCloseTab)
    }
    Dialog(onDismissRequest = onCloseTab) {
        Column(Modifier.background(ReptilianTheme.Surface)) {
            options.forEach { (label, action) ->
                LegacyText(label, Modifier.fillMaxWidth().clickable(onClick = action).padding(horizontal = 18.dp, vertical = 9.dp))
            }
        }
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
    hasRecipient: Boolean,
    messageType: String,
    onEdit: () -> Unit,
    onRecipient: () -> Unit,
    onToggleRecipients: () -> Unit,
    onCcRecipient: () -> Unit,
    onEmoticon: () -> Unit
) {
    val options = buildList<Pair<String, () -> Unit>> {
        add((if (hasRecipient) "Send Message" else "Set Recipient") to (if (hasRecipient) onDismiss else onRecipient))
        add("Edit Message" to onEdit)
        when (messageType) {
            "email" -> {
                add("Add Recipient" to onRecipient)
                add("Add Cc Recipient" to onCcRecipient)
                add("Show/Hide CC Recipients" to onToggleRecipients)
            }
            "im", "smsr", "yahoo", "sms", "msn", "random" -> {
                add("Add Recipient" to onRecipient)
                add("Show/Hide Recipients" to onToggleRecipients)
            }
        }
        add("Add Emoticon" to onEmoticon)
    }
    Dialog(onDismissRequest = onDismiss) {
        Column(Modifier.background(ReptilianTheme.Surface)) {
            options.forEach { (label, action) ->
                LegacyText(
                    label,
                    Modifier
                        .fillMaxWidth()
                        .clickable(onClick = action)
                        .padding(horizontal = 18.dp, vertical = 9.dp)
                )
            }
        }
    }
}

@Composable
private fun LegacyEditorDialog(value: String, onValue: (String) -> Unit, maxLength: Int, onClose: () -> Unit) {
    Dialog(onDismissRequest = onClose) {
        Column(Modifier.background(ReptilianTheme.Surface).padding(10.dp)) {
            LegacyText("Type your message")
            OutlinedTextField(value, onValue, Modifier.fillMaxWidth(), maxLines = 8)
            LegacyText("Maximum $maxLength characters")
            Row {
                LegacyText("OK", Modifier.clickable(onClick = onClose).padding(10.dp))
                LegacyText("Cancel", Modifier.clickable(onClick = onClose).padding(10.dp))
            }
        }
    }
}

private data class LegacyContact(val name: String, val statusAsset: String)

@Composable
private fun LegacyRecipientDialog(
    contacts: List<Buddy>,
    onPick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<Set<String>>(emptySet()) }
    val sorted = remember(contacts) { contacts.sortedBy { it.displayName.lowercase() } }
    val filtered = sorted.filter { it.displayName.contains(query.trim(), ignoreCase = true) }

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
                LegacyText("Select All", Modifier.clickable {
                    selected = filtered.map { it.id }.toSet()
                }.padding(8.dp))
                LegacyText("Clear", Modifier.clickable {
                    selected = emptySet()
                }.padding(8.dp))
            }
            filtered.forEach { contact ->
                val checked = contact.id in selected
                val statusAsset = when (contact.status) {
                    "Available", "freeforchat" -> "online"
                    "Not Available" -> "notavailable"
                    else -> "offline"
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            selected = if (checked) selected - contact.id else selected + contact.id
                        }
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val bitmap = LegacyAssets.rememberBitmap(LocalContext.current, statusAsset)
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
                    LegacyCheckbox(checked, onCheckedChange = { checkedNow ->
                        selected = if (checkedNow) selected + contact.id else selected - contact.id
                    })
                    Spacer(Modifier.width(5.dp))
                    LegacyText(contact.displayName)
                }
            }
            Row {
                LegacyText("OK", Modifier.clickable {
                    val selectedNames = sorted
                        .filter { it.id in selected }
                        .map { it.displayName }
                    if (selectedNames.isNotEmpty()) {
                        val visible = selectedNames.take(3).joinToString(", ")
                        val formatted = if (selectedNames.size > 3) "$visible,,,," else visible
                        onPick(formatted)
                    } else onDismiss()
                }.padding(10.dp))
                LegacyText("Cancel", Modifier.clickable(onClick = onDismiss).padding(10.dp))
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
private fun LegacyComposerPreview(message: String, messageType: String, recipient: String, cc: String, hidden: Boolean) {
    val rawRecipient = recipient.trim()
    val displayRecipient = when {
        rawRecipient.isBlank() -> "(no recipient set)"
        messageType == "chatroom" -> "[Chatroom '$rawRecipient']"
        else -> rawRecipient
    }
    val to = if (hidden && rawRecipient.isNotBlank() && messageType != "email") "(hidden) $displayRecipient" else displayRecipient
    val preview = if (message.length <= 15) message else message.take(13) + ".."
    val ccLine = if (cc.isNotBlank() && messageType == "email") {
        "\n- Cc: " + if (hidden) "(hidden) $cc" else cc
    } else ""
    LegacyText("- To: $to$ccLine\n$preview\n", Modifier.padding(8.dp))
}
