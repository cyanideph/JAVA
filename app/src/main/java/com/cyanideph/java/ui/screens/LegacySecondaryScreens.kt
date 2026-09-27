package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

private const val legacyBatteryText = """* Battery Saving Mode

To reduce your battery consumption you can:

- Switch your phone to 2G:
Settings -> Phone -> Network …then select GSM.

3G or UMTS is required for video calls, VoIP calls and high speed internet browsing.

- Ensure your Bluetooth is turned off:
Settings -> Connection -> Bluetooth

You can also try turning off WiFi when not in use, lowering your screen’s brightness, not using animated wallpapers.

Contact your local Smart Wireless Center for more information or help on changing the settings of your phone."""

private const val legacyOfflineText = """* Offline Settings

Allows you to set how your messages will be delivered when UZZAP is off:

Choose " Available SMS " to receive messages via SMS when Uzzap is off - Only available for some networks.

Choose " Email " to have your messages forwarded to Email when off.

If no options are selected, your messages will be stored for up to 14 days until you next Login to Uzzap."""

@Composable
fun BatterySavingScreen(onBack: () -> Unit, onMenu: () -> Unit) {
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Battery Saving Mode", Modifier.fillMaxWidth())
            LegacyText(legacyBatteryText, Modifier.weight(1f).fillMaxWidth().padding(6.dp))
            LegacyFunctionBar(
                leftLabel = "Close Tab",
                rightLabel = "Menu",
                modifier = Modifier.fillMaxWidth(),
                onLeftClick = onBack,
                onRightClick = onMenu
            )
        }
    }
}

@Composable
fun OfflineSettingsScreen(onBack: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = remember { context.getSharedPreferences("legacy_settings", android.content.Context.MODE_PRIVATE) }
    var route by remember { mutableStateOf(prefs.getString("offline.route", "") ?: "") }
    var showOptions by remember { mutableStateOf(false) }
    var showHelp by remember { mutableStateOf(false) }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Offline Settings", Modifier.fillMaxWidth())
            LegacyText(
                "* Offline Settings

Choose how your messages will be delivered when Uzzap is off.
",
                Modifier.fillMaxWidth().padding(6.dp)
            )
            listOf("sms" to "Available SMS", "email" to "Email").forEach { (value, label) ->
                LegacyText(
                    label,
                    Modifier.fillMaxWidth()
                        .background(if (route == value) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                        .clickable { route = value }
                        .padding(horizontal = 8.dp, vertical = 7.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(
                leftLabel = "Options",
                rightLabel = "Save",
                modifier = Modifier.fillMaxWidth(),
                onLeftClick = { showOptions = true },
                onRightClick = {
                    prefs.edit().putString("offline.route", route).apply()
                    onBack()
                }
            )
        }
    }

    if (showOptions) {
        Dialog(onDismissRequest = { showOptions = false }) {
            LegacyFrame(Modifier.fillMaxWidth().padding(16.dp)) {
                Column(Modifier.fillMaxWidth()) {
                    listOf("Help", "Close Tab").forEach { action ->
                        LegacyText(
                            action,
                            Modifier.fillMaxWidth().clickable {
                                showOptions = false
                                if (action == "Help") showHelp = true else onBack()
                            }.padding(horizontal = 8.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }
    }

    if (showHelp) {
        LegacyDialogMessage(
            "* Offline Settings

Allows you to set how your messages will be delivered when UZZAP is off:

Choose " Available SMS " to receive messages via SMS when Uzzap is off - Only available for some networks.

Choose " Email " to have your messages forwarded to Email when off.

If no options are selected, your messages will be stored for up to 14 days until you next Login to Uzzap."
        ) { showHelp = false }
    }
}

@Composable
fun PurchaseHistoryScreen(onBack: () -> Unit) {
    var hasHistory by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }
    var showOptions by remember { mutableStateOf(false) }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Purchase History", Modifier.fillMaxWidth())
            if (loading) {
                LegacyText("  Fetching your last 14 transactions.Please wait..\n", Modifier.padding(6.dp))
            } else if (hasHistory) {
                LegacyText("* Your last 0 transactions\n", Modifier.padding(6.dp))
            }
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(
                leftLabel = if (hasHistory) "Options" else "Refresh",
                rightLabel = if (hasHistory) "Scroll >" else "Close",
                modifier = Modifier.fillMaxWidth(),
                onLeftClick = {
                    if (hasHistory) showOptions = true else loading = true
                },
                onRightClick = onBack
            )
        }
    }

    if (showOptions) {
        Dialog(onDismissRequest = { showOptions = false }) {
            LegacyFrame(Modifier.fillMaxWidth().padding(16.dp)) {
                Column(Modifier.fillMaxWidth()) {
                    listOf("Refresh", "Close").forEach { action ->
                        LegacyText(
                            action,
                            Modifier.fillMaxWidth()
                                .clickable {
                                    showOptions = false
                                    if (action == "Refresh") loading = true else onBack()
                                }
                                .padding(horizontal = 8.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChangeMobileScreen(onBack: () -> Unit) {
    var number by remember { mutableStateOf("") }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Enter new number", Modifier.fillMaxWidth())
            LegacyFormList(
                listOf(LegacyFormField("mobile","Mobile number","Your mobile number",
                    "Please enter your mobile phone number in full international format including country code (for example +63918_____)",
                    50,inputFlags=3)),
                mapOf("mobile" to number),
                { _, value -> number = value },
                Modifier.weight(1f).fillMaxWidth().padding(6.dp)
            )
            LegacyFunctionBar("OK","Cancel",Modifier.fillMaxWidth(), onLeftClick = { }, onRightClick = onBack)
        }
    }
}

@Composable
fun ChatInviteScreen(room: String, onBack: () -> Unit, onMenu: () -> Unit) {
    var accepted by remember { mutableStateOf(false) }
    var showOptions by remember { mutableStateOf(false) }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar(if (accepted) "Chat - $room" else "Chat Invite - $room", Modifier.fillMaxWidth())
            if (!accepted) {
                LegacyText(
                    "You have been invited to join the Chatroom '$room'. Do you accept this invitation?\n",
                    Modifier.padding(6.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(
                leftLabel = if (accepted) "Options" else "Options ",
                rightLabel = "Menu",
                modifier = Modifier.fillMaxWidth(),
                onLeftClick = { showOptions = true },
                onRightClick = onMenu
            )
        }
    }

    if (showOptions && !accepted) {
        Dialog(onDismissRequest = { showOptions = false }) {
            LegacyFrame(Modifier.fillMaxWidth().padding(16.dp)) {
                Column(Modifier.fillMaxWidth()) {
                    listOf("Yes", "No", "Close Tab").forEach { action ->
                        LegacyText(
                            action,
                            Modifier.fillMaxWidth()
                                .clickable {
                                    showOptions = false
                                    when (action) {
                                        "Yes" -> accepted = true
                                        "No", "Close Tab" -> onBack()
                                    }
                                }
                                .padding(horizontal = 8.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun StoredMessageScreen(
    onBack: () -> Unit,
    sender: String? = null,
    messageType: String? = null
) {
    var showOptions by remember { mutableStateOf(false) }
    val currentUser: String? = null
    val options = buildList {
        if (sender != null && sender != currentUser) add("Send Reply")
        if (messageType == "im") add("Reply All")
        add("Forward Message")
        add("Close Tab")
    }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Stored Message", Modifier.fillMaxWidth())
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(
                "Options","Buddies",Modifier.fillMaxWidth(),
                onLeftClick = { showOptions = true },
                onRightClick = onBack
            )
        }
    }
    if (showOptions) {
        Dialog(onDismissRequest = { showOptions = false }) {
            LegacyFrame(Modifier.fillMaxWidth().padding(16.dp)) {
                Column(Modifier.fillMaxWidth()) {
                    options.forEach { action ->
                        LegacyText(
                            action,
                            Modifier.fillMaxWidth().clickable {
                                showOptions = false
                                if (action == "Close Tab") onBack()
                            }.padding(horizontal = 8.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LegacyTextScreen(title: String, resource: String, left: String, right: String) {
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar(title, Modifier.fillMaxWidth())
            if (resource.isNotEmpty()) LegacyText(resource, Modifier.padding(6.dp))
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(left,right,Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun BuddyMatchingScreen(onBack: () -> Unit) {
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Buddy Matching", Modifier.fillMaxWidth())
            LegacyText("Automatic Buddy Matching", Modifier.padding(6.dp))
            LegacyText("Not supported on this device.", Modifier.padding(6.dp))
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(rightLabel="Close",modifier=Modifier.fillMaxWidth(),onRightClick=onBack)
        }
    }
}

@Composable
fun AddInviteBuddiesScreen(onBack: () -> Unit, onBuddies: () -> Unit, onAddOther: () -> Unit = {}) {
    var selected by remember { mutableIntStateOf(0) }
    val options=listOf("Invite Friends to Uzzap","Add Buddy by Mobile","Add Buddy by User ID","Add Other Contact")
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Add or Invite Buddies",Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                options.forEachIndexed{i,label->
                    LegacyText(label,Modifier.fillMaxWidth().clickable{selected=i}
                        .background(if(i==selected)ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                        .padding(8.dp))
                }
            }
            LegacyFunctionBar(
                "Select","Close",Modifier.fillMaxWidth(),
                onLeftClick={
                    when(selected){1,2->onBuddies();3->onAddOther();else->onBack()}
                },
                onRightClick=onBack
            )
        }
    }
}