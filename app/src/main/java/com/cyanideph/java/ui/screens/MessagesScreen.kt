package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text\nimport androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*
import com.cyanideph.java.ui.model.Message

private data class LegacyMessageTab(
    val title: String,
    val indicator: String? = null
)

@Composable
fun MessagesScreen(onBack: () -> Unit) {
    val tabs = remember {
        listOf(
            LegacyMessageTab("Buddy List"),
            LegacyMessageTab("Message", "message"),
            LegacyMessageTab("Chat", "chat")
        )
    }
    var selectedTab by remember { mutableIntStateOf(0) }
    var showOptions by remember { mutableStateOf(false) }\n    var messageText by remember { mutableStateOf("") }\n    var recipient by remember { mutableStateOf("Friend 1") }\n    var cc by remember { mutableStateOf("") }\n    var hideRecipients by remember { mutableStateOf(false) }\n    var showEditor by remember { mutableStateOf(false) }\n    var showRecipients by remember { mutableStateOf(false) }\n    var showEmoticons by remember { mutableStateOf(false) }

    val messages = listOf(
        Message("cy", "Welcome to Uzzap", "now", false),
        Message("Friend 1", "Hello!", "now", false)
    )

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar(when (selectedTab) {\n                1 -> "EM - Friend 1"\n                2 -> "Chat Room"\n                else -> "Instant Messaging"\n            }, Modifier.fillMaxWidth())

            LegacyTabStrip(
                tabs = tabs.map { it.title },
                selected = selectedTab,
                onSelected = { selectedTab = it },
                modifier = Modifier.fillMaxWidth()
            )

            LegacyComposerPreview(messageText, recipient, cc, hideRecipients)\n\n            if (showEditor) {\n                LegacyEditorDialog(messageText, { messageText = it }, { showEditor = false })\n            }\n            if (showRecipients) {\n                LegacyRecipientDialog({ recipient = it; showRecipients = false })\n            }\n            if (showEmoticons) {\n                LegacyEmoticonDialog({ messageText += it; showEmoticons = false })\n            }\n\n            when (selectedTab) {
                0 -> LegacyMessageList(messages)
                1 -> LegacyMessageList(messages)
                else -> LegacyMessageList(messages)
            }

            Row(Modifier.fillMaxWidth()) {
                Box(
                    Modifier.weight(1f).clickable { showOptions = true }
                ) { LegacyText("Options", Modifier.padding(8.dp)) }
                Box(
                    Modifier.weight(1f).clickable { onBack() }
                ) { LegacyText("Menu", Modifier.padding(8.dp)) }
            }
            LegacyFunctionBar(Modifier.fillMaxWidth())
        }
    }

    if (showOptions) {
        LegacyMessageOptions(onDismiss = { showOptions = false })
    }
}

@Composable
private fun LegacyMessageList(messages: List<Message>) {
    LazyColumn(
        Modifier
            .weight(1f, fill = true)
            .padding(horizontal = 4.dp),
        contentPadding = PaddingValues(vertical = 3.dp)
    ) {
        items(messages) { message ->
            MessageRow(message)
        }
    }
}

@Composable
private fun MessageRow(message: Message) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val iconPath = if (message.outgoing) "sending-message-icon" else "message"
    val icon = LegacyAssets.rememberBitmap(context, iconPath)

    Row(
        Modifier
            .fillMaxWidth()
            .background(ReptilianTheme.Surface)
            .padding(horizontal = 5.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            icon,
            contentDescription = null,
            Modifier.size(
                with(density) { icon.width.toDp() },
                with(density) { icon.height.toDp() }
            ),
            contentScale = ContentScale.None
        )
        Spacer(Modifier.width(5.dp))
        Column(Modifier.weight(1f)) {
            LegacyText(message.sender)
            LegacyText(message.body)
        }
        LegacyText(message.time)
    }
}

@Composable
private fun LegacyMessageOptions(onDismiss: () -> Unit) {
    val options = listOf(
        "Send Message",
        "Edit Message",
        "Add Recipient",
        "Show/Hide Recipients",
        "Add Emoticon"
    )
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Column(Modifier.background(ReptilianTheme.Surface)) {
            options.forEach { option ->
                LegacyText(
                    option,
                    Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onDismiss)
                        .padding(horizontal = 18.dp, vertical = 9.dp)
                )
            }
        }
    }
}


@Composable
private fun LegacyComposerPreview(
    message: String,
    recipient: String,
    cc: String,
    hidden: Boolean
) {
    val to = if (recipient.isBlank()) "(no recipient set)" else recipient
    val ccLine = if (cc.isNotBlank() && !hidden) "\n- Cc: $cc" else ""
    val preview = if (message.length <= 15) message else message.take(13) + ".."
    LegacyText(
        "- To: $to$ccLine\n$preview",
        Modifier.padding(8.dp)
    )
}@Composable
private fun LegacyMessageOptions(onDismiss: () -> Unit) {
    val options = listOf(
        "Send Message",
        "Edit Message",
        "Set Recipient",
        "Add Recipient",
        "Add Cc Recipient",
        "Show/Hide Recipients",
        "Add Emoticon"
    )
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Column(Modifier.background(ReptilianTheme.Surface)) {
            options.forEach { option ->
                LegacyText(option, Modifier.fillMaxWidth().clickable(onClick = onDismiss).padding(horizontal = 18.dp, vertical = 9.dp))
            }
        }
    }
}

@Composable
private fun LegacyEditorDialog(value: String, onValue: (String) -> Unit, onClose: () -> Unit) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onClose) {
        Column(Modifier.background(ReptilianTheme.Surface).padding(10.dp)) {
            LegacyText("Type your message")
            OutlinedTextField(value = value, onValueChange = onValue, modifier = Modifier.fillMaxWidth(), maxLines = 8)
            Row { LegacyText("OK", Modifier.clickable(onClick = onClose).padding(10.dp)); LegacyText("Cancel", Modifier.clickable(onClick = onClose).padding(10.dp)) }
        }
    }
}

@Composable
private fun LegacyRecipientDialog(onPick: (String) -> Unit) {
    val recipients = listOf("cy", "Friend 1", "Friend 2")
    androidx.compose.ui.window.Dialog(onDismissRequest = { }) {
        Column(Modifier.background(ReptilianTheme.Surface)) {
            recipients.forEach { name -> LegacyText(name, Modifier.fillMaxWidth().clickable { onPick(name) }.padding(10.dp)) }
        }
    }
}

@Composable
private fun LegacyEmoticonDialog(onPick: (String) -> Unit) {
    val emoticons = listOf(":)", ";)", ":(", ":|", ":Oo", ">,")
    androidx.compose.ui.window.Dialog(onDismissRequest = { }) {
        Column(Modifier.background(ReptilianTheme.Surface)) {
            emoticons.forEach { value -> LegacyText(value, Modifier.fillMaxWidth().clickable { onPick(value) }.padding(8.dp)) }
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
