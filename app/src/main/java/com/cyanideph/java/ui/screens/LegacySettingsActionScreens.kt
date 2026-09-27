package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

@Composable
fun ChangePasswordScreen(onBack: () -> Unit) {
    var old by remember { mutableStateOf("") }
    var new1 by remember { mutableStateOf("") }
    var new2 by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    fun update() {
        error = when {
            old.isBlank() || new1.isBlank() || new2.isBlank() -> "Please fill in all input fields."
            new1.any { !it.isLetterOrDigit() } -> "Password must not contain special symbols"
            new1 != new2 -> "Passwords do not match."
            new1.length < 6 -> "Password is too short"
            else -> null
        }
        if (error == null) onBack()
    }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Change password", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth().padding(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                LegacyPasswordField("Old password", old) { old = it }
                LegacyPasswordField("New password", new1) { new1 = it }
                LegacyPasswordField("Verification", new2) { new2 = it }
            }
            LegacyFunctionBar(leftLabel = "Update", rightLabel = "Cancel", modifier = Modifier.clickable { update() })
        }
    }
    if (error != null) AlertDialog(onDismissRequest = { error = null }, text = { LegacyText(error.orEmpty()) }, confirmButton = { TextButton({ error = null }) { LegacyText("OK") } })
}

@Composable
fun ChatroomTonesScreen(onBack: () -> Unit) {
    var enabled by remember { mutableStateOf(true) }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Chatroom Tones", Modifier.fillMaxWidth())
            LegacyText("Choose if you wish an alert tone to sound for chatroom messages.", Modifier.padding(8.dp))
            Row(Modifier.fillMaxWidth().padding(8.dp)) {
                Switch(checked = enabled, onCheckedChange = { enabled = it })
                LegacyText("Chatroom Tones", Modifier.padding(start = 8.dp))
            }
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(leftLabel = "", rightLabel = "Save", modifier = Modifier.clickable { onBack() })
        }
    }
}

@Composable
fun SubscriptionMenuScreen(onBack: () -> Unit, onPurchaseHistory: () -> Unit) {
    val options = listOf("Current Billing Status", "Purchase a Package", "Auto-Renew", "View Purchase History")
    var selected by remember { mutableIntStateOf(0) }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Subscription Menu", Modifier.fillMaxWidth())
            options.forEachIndexed { index, label ->
                LegacyText(label, Modifier.fillMaxWidth().background(if (selected == index) ReptilianTheme.MenuSelected else ReptilianTheme.Surface).clickable { selected = index }.padding(horizontal = 8.dp, vertical = 7.dp))
            }
            Spacer(Modifier.weight(1f))
            LegacyFunctionBar(leftLabel = "Select", rightLabel = "Cancel", modifier = Modifier.clickable {
                if (options[selected] == "View Purchase History") onPurchaseHistory() else onBack()
            })
        }
    }
}

@Composable
private fun LegacyPasswordField(label: String, value: String, onChange: (String) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        LegacyText(label)
        OutlinedTextField(value, onChange, Modifier.fillMaxWidth(), singleLine = true, visualTransformation = PasswordVisualTransformation())
    }
}
