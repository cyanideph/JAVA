package com.cyanideph.java.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

@Composable
fun ChangePasswordScreen(onBack: () -> Unit) {
    var old by remember { mutableStateOf("") }
    var new1 by remember { mutableStateOf("") }
    var new2 by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val fields = listOf(
        LegacyFormField("old", "Old password", "Your old password", "", 30, password = true, inputFlags = 65536),
        LegacyFormField("new1", "New password", "Enter your new password", "", 30, password = true, inputFlags = 65536),
        LegacyFormField("new2", "Verification", "Re-type new password", "", 30, password = true, inputFlags = 65536)
    )
    fun values() = mapOf("old" to old, "new1" to new1, "new2" to new2)

    fun update() {
        error = when {
            old.isEmpty() || new1.isEmpty() || new2.isEmpty() -> "Please fill in all input fields."
            new1.any { !it.isLetterOrDigit() } -> "Password must not contain special symbols"
            new1 != new2 -> "Passwords do not match."
            new1.length < 6 -> "Password is too short"
            else -> null
        }
        if (error == null) {
            // Transport/account update is intentionally not faked here.
            error = "Password validation complete. Account update requires the legacy service transport."
        }
    }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Change password", Modifier.fillMaxWidth())
            LegacyFormList(
                fields = fields,
                values = values(),
                onValueChange = { key, value ->
                    when (key) {
                        "old" -> old = value
                        "new1" -> new1 = value
                        "new2" -> new2 = value
                    }
                },
                modifier = Modifier.weight(1f).fillMaxWidth().padding(6.dp)
            )
            LegacyFunctionBar(
                leftLabel = "Update",
                rightLabel = "Cancel",
                modifier = Modifier.fillMaxWidth(),
                onLeftClick = { update() },
                onRightClick = onBack
            )
        }
    }
    error?.let { message ->
        LegacyDialogMessage(message) { error = null }
    }
}

@Composable
fun ChatroomTonesScreen(onBack: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = remember { context.getSharedPreferences("legacy_settings", android.content.Context.MODE_PRIVATE) }
    var enabled by remember { mutableStateOf(prefs.getString("chatroom.alert", "yes") != "no") }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Chatroom Tones", Modifier.fillMaxWidth())
            LegacyText(
                "Choose if you wish an alert tone to sound for chatroom messages.",
                Modifier.fillMaxWidth().padding(8.dp)
            )
            LegacyText(
                "Chatroom Tones    " + if (enabled) "yes" else "no",
                Modifier.fillMaxWidth()
                    .background(ReptilianTheme.MenuSelected)
                    .clickable { enabled = !enabled }
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            )
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(
                leftLabel = "",
                rightLabel = "Save",
                modifier = Modifier.fillMaxWidth(),
                onRightClick = {
                    prefs.edit().putString("chatroom.alert", if (enabled) "yes" else "no").apply()
                    onBack()
                }
            )
        }
    }
}
@Composable
fun SubscriptionMenuScreen(onBack: () -> Unit, onPurchaseHistory: () -> Unit) {
    val options = listOf("Current Billing Status", "Purchase a Package", "Auto-Renew", "View Purchase History")
    var selected by remember { mutableIntStateOf(0) }
    var processing by remember { mutableStateOf(false) }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Subscription Menu", Modifier.fillMaxWidth())
            LegacyText("* Select subscription option:", Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp))
            options.forEachIndexed { index, label ->
                LegacyText(
                    label,
                    Modifier.fillMaxWidth()
                        .background(if (selected == index) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                        .clickable { selected = index }
                        .padding(horizontal = 8.dp, vertical = 7.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(
                leftLabel = "Select",
                rightLabel = "Cancel",
                modifier = Modifier.fillMaxWidth(),
                onLeftClick = {
                    if (options[selected] == "View Purchase History") onPurchaseHistory() else processing = true
                },
                onRightClick = onBack
            )
        }
    }
    if (processing) LegacyDialogMessage("Your request is being processed. Please wait...") { processing = false }
}
