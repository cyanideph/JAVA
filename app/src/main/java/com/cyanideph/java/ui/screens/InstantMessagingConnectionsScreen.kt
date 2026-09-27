package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

/**
 * Java parity trace:
 * an.java -> case 101 creates menu id "connections":
 *   100 = Connect/Disconnect to Yahoo
 *   101 = Connect/Disconnect to MSN
 *
 * dd.java owns the connection form after Connect:
 *   Yahoo ID / Password or MSN ID / Password
 *   Login / Cancel
 */
@Composable
fun InstantMessagingConnectionsScreen(onBack: () -> Unit) {
    var yahooConnected by remember { mutableStateOf(false) }
    var msnConnected by remember { mutableStateOf(false) }
    var loginProvider by remember { mutableStateOf<String?>(null) }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Instant Messaging", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                ConnectionRow(
                    if (yahooConnected) "Disconnect from Yahoo" else "Connect to Yahoo"
                ) {
                    if (yahooConnected) yahooConnected = false else loginProvider = "Yahoo"
                }
                ConnectionRow(
                    if (msnConnected) "Disconnect from MSN" else "Connect to MSN"
                ) {
                    if (msnConnected) msnConnected = false else loginProvider = "MSN"
                }
            }
            LegacyFunctionBar(
                leftLabel = "Select",
                rightLabel = "Back",
                modifier = Modifier.fillMaxWidth().clickable(onClick = onBack)
            )
        }
    }

    loginProvider?.let { provider ->
        ConnectionLoginDialog(
            provider = provider,
            onLogin = {
                if (provider == "Yahoo") yahooConnected = true else msnConnected = true
                loginProvider = null
            },
            onCancel = { loginProvider = null }
        )
    }
}

@Composable
private fun ConnectionRow(label: String, onClick: () -> Unit) {
    LegacyText(
        label,
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 7.dp)
    )
}

@Composable
private fun ConnectionLoginDialog(
    provider: String,
    onLogin: () -> Unit,
    onCancel: () -> Unit
) {
    var username by remember(provider) { mutableStateOf("") }
    var password by remember(provider) { mutableStateOf("") }

    Dialog(onDismissRequest = onCancel) {
        Column(
            Modifier
                .background(ReptilianTheme.Surface)
                .padding(10.dp)
        ) {
            LegacyText("* Connect to $provider")
            Spacer(Modifier.height(6.dp))
            LegacyText(if (provider == "Yahoo") "Your Yahoo! ID" else "Your MSN ID")
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(4.dp))
            LegacyText("Password")
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onLogin) { LegacyText("Login") }
                TextButton(onClick = onCancel) { LegacyText("Cancel") }
            }
        }
    }
}
