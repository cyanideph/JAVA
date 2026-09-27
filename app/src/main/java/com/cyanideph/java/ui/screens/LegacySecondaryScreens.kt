package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

@Composable
fun BatterySavingScreen(onBack: () -> Unit) {
    LegacyTextScreen("Battery Saving Mode", "battery.txt", "Close Tab", "Menu")
}

@Composable
fun OfflineSettingsScreen(onBack: () -> Unit) {
    var route by remember { mutableStateOf("Email") }
    LegacySelectableScreen("Offline Settings", "Choose how your messages will be delivered when Uzzap is off.", listOf("Available SMS", "Email"), route, { route = it }, "Options", "Save")
}

@Composable
fun PurchaseHistoryScreen(onBack: () -> Unit) {
    var loading by remember { mutableStateOf(false) }
    val rows = remember { mutableStateListOf("Date/Time    Amount    Item") }
    Column(Modifier.fillMaxSize().background(ReptilianTheme.Surface)) {
        LegacyText("Purchase History", Modifier.padding(8.dp))
        LegacyText(if (loading) "  Fetching your last 14 transactions.Please wait.." else "* Your last " + (rows.size - 1) + " transactions", Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
        LazyColumn(Modifier.weight(1f)) { items(rows) { LegacyText(it, Modifier.fillMaxWidth().padding(8.dp)) } }
        LegacyFunctionBar(leftLabel = "Options", rightLabel = if (loading) "Close" else "Scroll >")
    }
}

@Composable
fun ChangeMobileScreen(onBack: () -> Unit) {
    var number by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().background(ReptilianTheme.Surface)) {
        LegacyText("Enter new number", Modifier.padding(8.dp))
        OutlinedTextField(number, { number = it }, Modifier.fillMaxWidth().padding(8.dp), singleLine = true)
        Spacer(Modifier.weight(1f))
        LegacyFunctionBar(leftLabel = "OK", rightLabel = "Cancel")
    }
}

@Composable
fun ChatInviteScreen(room: String, onBack: () -> Unit) {
    var accepted by remember { mutableStateOf(false) }
    if (!accepted) {
        Column(Modifier.fillMaxSize().background(ReptilianTheme.Surface)) {
            LegacyText("Chat Invite - " + room, Modifier.padding(8.dp))
            LegacyText("You have been invited to join the Chatroom '" + room + "'. Do you accept this invitation?\n", Modifier.padding(8.dp))
            Row(Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button({ accepted = true }) { Text("Yes") }
                Button(onBack) { Text("No") }
                Button(onBack) { Text("Close Tab") }
            }
        }
    } else {
        LegacyTextScreen("Chat - " + room, "", "Options", "Menu")
    }
}

@Composable
fun StoredMessageScreen(onBack: () -> Unit) {
    LegacyTextScreen("Stored Message", "", "Options", "Buddies")
}

@Composable
private fun LegacyTextScreen(title: String, resource: String, left: String, right: String) {
    Column(Modifier.fillMaxSize().background(ReptilianTheme.Surface)) {
        LegacyText(title, Modifier.padding(8.dp))
        if (resource.isNotEmpty()) LegacyText("[" + resource + "]", Modifier.padding(8.dp))
        Spacer(Modifier.weight(1f))
        LegacyFunctionBar(leftLabel = left, rightLabel = right)
    }
}

@Composable
private fun LegacySelectableScreen(title: String, description: String, options: List<String>, selected: String, onSelect: (String) -> Unit, left: String, right: String) {
    Column(Modifier.fillMaxSize().background(ReptilianTheme.Surface)) {
        LegacyText(title, Modifier.padding(8.dp))
        LegacyText(description, Modifier.padding(8.dp))
        options.forEach { option ->
            Row(Modifier.fillMaxWidth().clickable { onSelect(option) }.padding(8.dp)) {
                RadioButton(selected == option, { onSelect(option) })
                LegacyText(option, Modifier.padding(start = 8.dp))
            }
        }
        Spacer(Modifier.weight(1f))
        LegacyFunctionBar(leftLabel = left, rightLabel = right)
    }
}


@Composable
fun BuddyMatchingScreen(onBack: () -> Unit) {
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Buddy Matching", Modifier.fillMaxWidth())
            LegacyText("Automatic Buddy Matching", Modifier.padding(8.dp))
            LegacyText("Buddy matching is not supported on this device.", Modifier.padding(8.dp))
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(rightLabel = "Close", modifier = Modifier.clickable { onBack() })
        }
    }
}

@Composable
fun AddInviteBuddiesScreen(onBack: () -> Unit, onBuddies: () -> Unit, onAddOther: () -> Unit = {}) {
    var selected by remember { mutableIntStateOf(0) }
    val options = listOf("Invite Friends to Uzzap","Add Buddy by Mobile","Add Buddy by User ID","Add Other Contact")
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Add or Invite Buddies", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                options.forEachIndexed { i, label ->
                    LegacyText(label, Modifier.fillMaxWidth().clickable { selected=i }
                        .background(if(i==selected) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                        .padding(8.dp))
                }
            }
            LegacyFunctionBar(leftLabel = "Select", rightLabel = "Close",
                modifier = Modifier.fillMaxWidth().clickable {
                    when (selected) { 1, 2 -> onBuddies(); 3 -> onAddOther(); else -> onBack() }
                })
        }
    }
}
