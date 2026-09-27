package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*
import com.cyanideph.java.ui.model.Buddy
import com.cyanideph.java.ui.model.LegacyBuddyRepository

private val legacyGroups = listOf(
    "most_frequent" to "Most Frequent",
    "buddies" to "All Buddies",
    "chatterbox" to "My Friends",
    "action_required" to "Pending Buddies",
    "other_contacts" to "Other Contacts"
)

@Composable
fun BuddyListScreen(onBack: () -> Unit, onAddInvite: () -> Unit = {}, onHelp: () -> Unit = {}) {
    val context = LocalContext.current
    val density = LocalDensity.current
    var selectedGroup by remember { mutableIntStateOf(1) }
    var selectedBuddy by remember { mutableIntStateOf(-1) }
    var search by remember { mutableStateOf("") }
    var showOptions by remember { mutableStateOf(false) }
    var showContactOptions by remember { mutableStateOf(false) }
    var showGroups by remember { mutableStateOf(false) }
    var showManageGroups by remember { mutableStateOf(false) }
    var showNewGroup by remember { mutableStateOf(false) }
    var newGroupName by remember { mutableStateOf("") }
    var groupSort by remember { mutableStateOf(mapOf("chatterbox" to 1)) }
    val listState = rememberLazyListState()

    val buddies = LegacyBuddyRepository.buddiesFor(context)

    val visible = buddies.filter { it.group == legacyGroups[selectedGroup].first && it.displayName.contains(search, ignoreCase = true) }.sortedBy { it.displayName.lowercase() }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Buddy List", Modifier.fillMaxWidth())

            Row(
                Modifier.fillMaxWidth().background(ReptilianTheme.Surface).padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = search,
                    onValueChange = { search = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    decorationBox = { inner -> if (search.isBlank()) LegacyText("Search") else inner() }
                )
                LegacyText(
                    legacyGroups[selectedGroup].second,
                    Modifier.clickable { showGroups = true }.padding(start = 10.dp)
                )
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
                        onClick = { selectedBuddy = index; showContactOptions = false }
                    )
                }
            }

            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Menu",
                modifier = Modifier.fillMaxWidth(),
                onLeftClick = { showOptions = true },
                onRightClick = onBack
            )
            if (showContactOptions && selectedBuddy >= 0) {
                BuddyContactOptionsPopup(
                    buddy = visible[selectedBuddy],
                    onDismiss = { showContactOptions = false }
                )
            }
            if (showOptions) {
                BuddyOptionsPopup(
                    hasBuddy = selectedBuddy >= 0,
                    isActionRequired = legacyGroups[selectedGroup].first == "action_required",
                    searchActive = search.isNotBlank(),
                    onDismiss = { showOptions = false },
                    onAdd = { showOptions = false; onAddInvite() },
                    onHelp = { showOptions = false; onHelp() },
                    onContact = { showOptions = false; showContactOptions = true },
                    onManageGroups = { showOptions = false; showManageGroups = true },
                    onNewGroup = { showOptions = false; newGroupName = ""; showNewGroup = true },
                    onClearSearch = { search = ""; selectedBuddy = -1; showOptions = false }
                )
            }
        }
            if (showGroups) {
                Dialog(onDismissRequest = { showGroups = false }) {
                    Column(Modifier.background(ReptilianTheme.Surface)) {
                        legacyGroups.forEachIndexed { index, group ->
                            LegacyText(
                                group.second,
                                Modifier.fillMaxWidth().clickable {
                                    selectedGroup = index
                                    selectedBuddy = -1
                                    showContactOptions = false
                                    showGroups = false
                                }.padding(horizontal = 18.dp, vertical = 9.dp)
                            )
                        }
                    }
                }
            }
    }
            if (showNewGroup) {
                Dialog(onDismissRequest = { showNewGroup = false }) {
                    Column(Modifier.background(ReptilianTheme.Surface).padding(12.dp)) {
                        LegacyText("New Group", Modifier.padding(bottom = 8.dp))
                        BasicTextField(value = newGroupName, onValueChange = { newGroupName = it.take(50) }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                        LegacyFunctionBar(modifier = Modifier.fillMaxWidth(), leftLabel = "OK", rightLabel = "Cancel", onLeftClick = { showNewGroup = false }, onRightClick = { showNewGroup = false })
                    }
                }
            }
            if (showManageGroups) {
                Dialog(onDismissRequest = { showManageGroups = false }) {
                    Column(Modifier.background(ReptilianTheme.Surface)) {
                        LegacyText("Rename Group", Modifier.fillMaxWidth().clickable { showManageGroups = false }.padding(horizontal = 18.dp, vertical = 9.dp))
                        val group = legacyGroups[selectedGroup].first
                        if (group != "buddies" && group != "action_required" && group != "other_contacts") {
                            LegacyText("Manage Buddies in Group", Modifier.fillMaxWidth().clickable { showManageGroups = false }.padding(horizontal = 18.dp, vertical = 9.dp))
                        }
                        if (group != "buddies" && group != "action_required" && group != "most_frequent" && group != "chatterbox" && group != "other_contacts") {
                            LegacyText("Delete Group", Modifier.fillMaxWidth().clickable { showManageGroups = false }.padding(horizontal = 18.dp, vertical = 9.dp))
                        }
                        if (group != "buddies" && group != "action_required" && group != "other_contacts") {
                            if ((groupSort[group] ?: 1) == 0) {
                                LegacyText("Change Sort to Alphabetical", Modifier.fillMaxWidth().clickable {
                                    groupSort = groupSort + (group to 1)
                                    showManageGroups = false
                                }.padding(horizontal = 18.dp, vertical = 9.dp))
                            } else {
                                LegacyText("Change Sort to Online First", Modifier.fillMaxWidth().clickable {
                                    groupSort = groupSort + (group to 0)
                                    showManageGroups = false
                                }.padding(horizontal = 18.dp, vertical = 9.dp))
                            }
                        }
                        LegacyText("Return Groups to Defaults", Modifier.fillMaxWidth().clickable { showManageGroups = false }.padding(horizontal = 18.dp, vertical = 9.dp))
                    }
                }
            }
    }
}

@Composable
private fun BuddyOptionsPopup(
    hasBuddy: Boolean,
    isActionRequired: Boolean,
    searchActive: Boolean,
    buddyType: String? = "amazilia",
    hasEmail: Boolean = false,
    hasMobile: Boolean = false,
    hasYahoo: Boolean = false,
    hasMsn: Boolean = false,
    hasHistory: Boolean = false,
    hasAuthorizedContact: Boolean = hasBuddy,
    onDismiss: () -> Unit,
    onAdd: () -> Unit,
    onHelp: () -> Unit,
    onContact: () -> Unit,
    onManageGroups: () -> Unit,
    onNewGroup: () -> Unit,
    onClearSearch: () -> Unit
) {
    val options = buildList {
        if (hasBuddy) add("Contact")
        if (!hasBuddy && !isActionRequired) add("Send Group Message")
        if (!hasBuddy) add("Manage Groups")
        add("New Group")
        add("Add/Invite Buddies")
        add("Clear Message History")
        if (searchActive) add("Clear Search Bar")
        add("Uzzap Help")
    }
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Column(Modifier.wrapContentWidth().background(ReptilianTheme.Surface)) {
            options.forEach { label ->
                LegacyText(
                    label,
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (label) {
                                "Contact" -> onContact()
                                "Add/Invite Buddies" -> onAdd()
                                "Manage Groups" -> onManageGroups()
                                "New Group" -> onNewGroup()
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

@Composable
private fun BuddyContactOptionsPopup(
    buddy: Buddy,
    onDismiss: () -> Unit
) {
    val eligible = buddy.authorized && buddy.status != "unknown"
    val usernamePresent = buddy.type == "amazilia" || (buddy.type != "yahoo" && buddy.type != "msn" && !buddy.isOtherContact)
    val options = buildList {
        if (usernamePresent && !buddy.authorized) add("Authorize as Buddy")
        if (usernamePresent && buddy.status == "unknown") add("Request to Authorize")

        if (buddy.type == "yahoo" || buddy.type == "msn" || (buddy.type == "amazilia" && usernamePresent && eligible)) {
            add("Send Message")
        }

        if (buddy.email != null && eligible) add("Send Email")
        if (buddy.mobile != null && eligible) add("Send SMS")

        if (!usernamePresent) {
            val inviteCount = listOf(
                buddy.yahooId != null,
                buddy.msnId != null,
                buddy.mobile != null,
                buddy.email != null
            ).count { it }
            val qualified = inviteCount > 1
            if (buddy.yahooId != null) add(if (qualified) "Invite via Yahoo" else "Invite")
            if (buddy.msnId != null) add(if (qualified) "Invite via MSN" else "Invite")
            if (buddy.mobile != null) add(if (qualified) "Invite via E-SMS" else "Invite")
            if (buddy.email != null) add(if (qualified) "Invite via Email" else "Invite")
        }

        if (buddy.type == "amazilia" && usernamePresent && eligible) add("Send Contacts")
        if (buddy.type == "amazilia" && usernamePresent && eligible) add("Group Chat")

        if (!buddy.isOtherContact && (usernamePresent.not() || eligible)) {
            add("My Friends")
            add("Other Groups")
        }

        add("Profile")
        if (buddy.type == "amazilia") add("Remove Buddy")
        if (buddy.hasAmazCid && !buddy.isOtherContact && (usernamePresent.not() || eligible)) add("View History")
    }

    Dialog(onDismissRequest = onDismiss) {
        Column(Modifier.wrapContentWidth().background(ReptilianTheme.Surface)) {
            options.forEach { label ->
                LegacyText(
                    label,
                    Modifier.fillMaxWidth().clickable { onDismiss() }
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
