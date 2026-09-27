package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

@Composable
fun BatterySavingScreen(onBack: () -> Unit) {
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Battery Saving Mode", Modifier.fillMaxWidth())
            LegacyText(legacyBatteryText, Modifier.weight(1f).fillMaxWidth().padding(6.dp))
            LegacyFunctionBar("","",Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun OfflineSettingsScreen(onBack: () -> Unit) {
    var selected by remember { mutableIntStateOf(0) }
    val options = listOf("Available SMS", "Email")
    val description = """* Offline Settings

Allows you to set how your messages will be delivered when UZZAP is off:

Choose " Available SMS " to receive messages via SMS when Uzzap is off - Only available for some networks.

Choose " Email " to have your messages forwarded to Email when off.

If no options are selected, your messages will be stored for up to 14 days until you next Login to Uzzap."""

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Offline Settings", Modifier.fillMaxWidth())
            LegacyText(description, Modifier.weight(1f).fillMaxWidth().padding(6.dp))
            Column(Modifier.fillMaxWidth()) {
                options.forEachIndexed { index, label ->
                    LegacyText(
                        label,
                        Modifier.fillMaxWidth()
                            .background(if (selected == index) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                            .clickable { selected = index }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                    )
                }
            }
            LegacyFunctionBar(
                leftLabel = "Select",
                rightLabel = "Close Tab",
                modifier = Modifier.fillMaxWidth().clickable { onBack() }
            )
        }
    }
}

@Composable
fun PurchaseHistoryScreen(onBack: () -> Unit) {
    var hasHistory by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var showOptions by remember { mutableStateOf(false) }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Purchase History", Modifier.fillMaxWidth())
            if (loading) LegacyText("  Fetching your last 14 transactions.Please wait..\n", Modifier.padding(6.dp))
            else if (hasHistory) LegacyText("* Your last transactions\n", Modifier.padding(6.dp))
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(
                if (hasHistory) "Options" else "Refresh",
                if (hasHistory) "Scroll >" else "Close",
                Modifier.fillMaxWidth().clickable {
                    if (hasHistory) showOptions = true else loading = true
                }
            )
        }
    }
    if (showOptions) LegacyDialogMessage("Refresh\n\nClose") { showOptions = false }
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
                    13,inputFlags=3)),
                mapOf("mobile" to number),
                { _, value -> number = value },
                Modifier.weight(1f).fillMaxWidth().padding(6.dp)
            )
            LegacyFunctionBar("OK","Cancel",Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun ChatInviteScreen(room: String, onBack: () -> Unit) {
    var accepted by remember { mutableStateOf(false) }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Chat Invite - $room", Modifier.fillMaxWidth())
            if (!accepted) {
                LegacyText("You have been invited to join the Chatroom '$room'. Do you accept this invitation?\n", Modifier.padding(6.dp))
                Spacer(Modifier.weight(1f))
                LegacyFunctionBar("Yes","No",Modifier.fillMaxWidth().clickable { accepted = true })
            } else {
                Spacer(Modifier.weight(1f))
                LegacyFunctionBar("Options","Menu",Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun StoredMessageScreen(onBack: () -> Unit) {
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Stored Message", Modifier.fillMaxWidth())
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar("Options","Buddies",Modifier.fillMaxWidth())
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
            LegacyFunctionBar(rightLabel="Close",modifier=Modifier.fillMaxWidth().clickable{onBack()})
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
            LegacyFunctionBar("Select","Close",Modifier.fillMaxWidth().clickable{
                when(selected){1,2->onBuddies();3->onAddOther();else->onBack()}
            })
        }
    }
}