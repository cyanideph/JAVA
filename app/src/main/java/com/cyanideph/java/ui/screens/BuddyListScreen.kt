package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.background
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
import com.cyanideph.java.ui.model.LegacyBuddyRepository

private val legacyGroups = listOf(
    "most_frequent" to "Most Frequent",
    "buddies" to "Buddies",
    "chatterbox" to "My Friends",
    "action_required" to "Action Required",
    "other_contacts" to "Other Contacts"
)

@Composable
fun BuddyListScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val density = LocalDensity.current
    var selectedGroup by remember { mutableIntStateOf(1) }
    var selectedBuddy by remember { mutableIntStateOf(-1) }
    var showOptions by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    val buddies = LegacyBuddyRepository.buddies

    val visible = buddies.filter { it.group == legacyGroups[selectedGroup].first }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Buddy List", Modifier.fillMaxWidth())

            LegacyText(
                legacyGroups[selectedGroup].second,
                Modifier
                    .fillMaxWidth()
                    .background(ReptilianTheme.Surface)
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )

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

            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Menu",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showOptions = true }
            )
            if (showOptions) {
                BuddyOptionsPopup(
                    hasBuddy = selectedBuddy >= 0,
                    isActionRequired = legacyGroups[selectedGroup].first == "action_required",
                    onDismiss = { showOptions = false },
                    onAdd = { showOptions = false },
                    onHelp = { showOptions = false }
                )
            }
        }
    }
}

@Composable
private fun BuddyOptionsPopup(
    hasBuddy: Boolean,
    isActionRequired: Boolean,
    buddyType: String? = "amazilia",
    hasEmail: Boolean = false,
    hasMobile: Boolean = false,
    hasYahoo: Boolean = false,
    hasMsn: Boolean = false,
    hasHistory: Boolean = false,
    hasAuthorizedContact: Boolean = hasBuddy,
    onDismiss: () -> Unit,
    onAdd: () -> Unit,
    onHelp: () -> Unit
) {
    val options = buildList {
        if (!hasAuthorizedContact) add("Authorize as Buddy")
        if (!hasBuddy) add("Request to Authorize")
        if (hasYahoo || hasMsn || (buddyType == "amazilia" && hasAuthorizedContact)) add("Send Message")
        if (hasEmail) add("Send Email")
        if (hasMobile) add("Send SMS")
        if (!hasBuddy) {
            if (hasYahoo) add("Invite via Yahoo")
            if (hasMsn) add("Invite via MSN")
            if (hasMobile) add("Invite via E-SMS")
            if (hasEmail) add("Invite via Email")
        }
        if (buddyType == "amazilia" && hasAuthorizedContact) {
            add("Send Contacts")
            add("Group Chat")
        }
        add("My Friends")
        add("Other Groups")
        add("Profile")
        if (buddyType == "amazilia") add("Remove Buddy")
        if (hasHistory) add("View History")
    }
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Column(Modifier.wrapContentWidth().background(ReptilianTheme.Surface)) {
            options.forEach { label ->
                LegacyText(label, Modifier.fillMaxWidth().clickable { onDismiss() }.padding(horizontal = 18.dp, vertical = 9.dp))
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
        "Available" -> "themes/default/online.png"
        "Not Available" -> "themes/default/notavailable.png"
        else -> "themes/default/offline.png"
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
