package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.background
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
import com.cyanideph.java.ui.model.Buddy

private val legacyGroups = listOf(
    "most_frequent" to "Most Frequent",
    "buddies" to "Buddies",
    "chatterbox" to "Chatterbox",
    "action_required" to "Action Required",
    "other_contacts" to "Other Contacts"
)

@Composable
fun BuddyListScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val density = LocalDensity.current
    var selectedGroup by remember { mutableIntStateOf(1) }
    var selectedBuddy by remember { mutableIntStateOf(-1) }\n    var showOptions by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    val buddies = listOf(
        Buddy("cy", "cy", "Available", "buddies"),
        Buddy("friend1", "Friend 1", "Available", "buddies"),
        Buddy("friend2", "Friend 2", "Not Available", "buddies"),
        Buddy("chatter", "Chatterbox", "Invisible", "chatterbox")
    )

    val visible = buddies.filter { it.group == legacyGroups[selectedGroup].first }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("EM - Buddy List", Modifier.fillMaxWidth())

            Row(
                Modifier
                    .fillMaxWidth()
                    .background(ReptilianTheme.Surface)
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LegacyText(legacyGroups[selectedGroup].second)
                LegacyText("‹  ›", Modifier.clickable {
                    selectedGroup = (selectedGroup + 1) % legacyGroups.size
                })
            }

            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 2.dp)
            ) {
                items(visible) { buddy ->
                    val index = visible.indexOf(buddy)
                    val selected = index == selectedBuddy
                    BuddyRow(
                        buddy = buddy,
                        selected = selected,
                        onClick = { selectedBuddy = index }
                    )
                }
            }

            Row(Modifier.fillMaxWidth()) {\n                Box(Modifier.weight(1f).clickable { showOptions = true }) {\n                    LegacyText("Options", Modifier.padding(8.dp))\n                }\n                Box(Modifier.weight(1f).clickable { onBack() }) {\n                    LegacyText("Menu", Modifier.padding(8.dp))\n                }\n            }\n            LegacyFunctionBar(Modifier.fillMaxWidth())\n            if (showOptions) {\n                BuddyOptionsPopup(\n                    hasBuddy = selectedBuddy >= 0,\n                    onDismiss = { showOptions = false },\n                    onAdd = { showOptions = false },\n                    onHelp = { showOptions = false }\n                )\n            }
        }
    }
}

@Composable
@Composable
private fun BuddyOptionsPopup(
    hasBuddy: Boolean,
    onDismiss: () -> Unit,
    onAdd: () -> Unit,
    onHelp: () -> Unit
) {
    val options = buildList {
        if (hasBuddy) add("Contact")
        if (!hasBuddy) add("Send Group Message")
        if (!hasBuddy) add("Manage Groups")
        add("New Group")
        add("Add/Invite Buddies")
        add("Clear Message History")
        add("Uzzap Help")
    }
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .wrapContentWidth()
                .background(ReptilianTheme.Surface)
        ) {
            options.forEach { label ->
                LegacyText(
                    label,
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (label) {
                                "Add/Invite Buddies" -> onAdd()
                                "Uzzap Help" -> onHelp()
                                else -> onDismiss()
                            }
                        }
                        .padding(horizontal = 18.dp, vertical = 9.dp)
                )
            }
        }
    }
}

private fun BuddyRow(
    buddy: Buddy,
    selected: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val statusAsset = when (buddy.status) {
        "Available" -> "online"
        "Not Available" -> "notavailable"
        else -> "offline"
    }
    val icon = LegacyAssets.rememberBitmap(context, statusAsset)
    val bg = if (selected) ReptilianTheme.MenuSelected else ReptilianTheme.Surface

    Row(
        Modifier
            .fillMaxWidth()
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            icon,
            contentDescription = buddy.status,
            Modifier.size(
                with(density) { icon.width.toDp() },
                with(density) { icon.height.toDp() }
            ),
            contentScale = ContentScale.None
        )
        Spacer(Modifier.width(6.dp))
        LegacyText(buddy.displayName)
    }
}
