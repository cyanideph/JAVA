package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
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
    var showOptions by remember { mutableStateOf(false) }

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

            when (selectedTab) {
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
