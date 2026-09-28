package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

/** Java parity: an.java case 101 -> connections; dd.java owns the Login/Cancel form. */
@Composable
fun InstantMessagingConnectionsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var yahooConnected by remember { mutableStateOf(false) }
    var msnConnected by remember { mutableStateOf(false) }
    var selected by remember { mutableIntStateOf(0) }
    var loginProvider by remember { mutableStateOf<String?>(null) }
    val rows = listOf(Triple("Yahoo", "yahoo-online", yahooConnected), Triple("MSN", "msn-online", msnConnected))
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Connections", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                rows.forEachIndexed { index, row ->
                    val label = if (row.third) "Disconnect from " + row.first else "Connect to " + row.first
                    Row(Modifier.fillMaxWidth().clickable {
                        selected = index
                        if (row.third) { if (index == 0) yahooConnected = false else msnConnected = false }
                        else loginProvider = row.first
                    }.background(if (index == selected) ReptilianTheme.MenuSelected else ReptilianTheme.Surface).padding(horizontal = 8.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
                        val bitmap = LegacyAssets.rememberBitmap(context, "themes/default/" + row.second + ".png")
                        Image(bitmap, contentDescription = label, Modifier.size(24.dp))
                        Spacer(Modifier.width(8.dp))
                        LegacyText(label)
                    }
                }
            }
            LegacyFunctionBar(leftLabel = "Select", rightLabel = "Back", modifier = Modifier.fillMaxWidth(), onLeftClick = { if (rows[selected].third) { if (selected == 0) yahooConnected = false else msnConnected = false } else loginProvider = rows[selected].first }, onRightClick = onBack)
        }
    }
    loginProvider?.let { provider ->
        ConnectionLoginDialog(provider, { if (provider == "Yahoo") yahooConnected = true else msnConnected = true; loginProvider = null }, { loginProvider = null })
    }
}

@Composable
private fun ConnectionLoginDialog(provider: String, onLogin: () -> Unit, onCancel: () -> Unit) {
    var username by remember(provider) { mutableStateOf("") }
    var password by remember(provider) { mutableStateOf("") }
    LegacyAdaptiveDialog(onDismissRequest = onCancel) {
        LegacyFrame(Modifier.fillMaxWidth().padding(16.dp)) {
            Column(Modifier.fillMaxWidth().padding(10.dp)) {
                LegacyText(if (provider == "Yahoo") "* Connect to Yahoo\n" else "* Connect to MSN\n")
            LegacyFormList(
                fields = listOf(
                    LegacyFormField(
                        "username",
                        if (provider == "Yahoo") "Yahoo ID" else "MSN ID",
                        "Your " + provider + " ID",
                        "* " + provider + " ID\n\nYour " + provider + " ID " + if (provider == "Yahoo") "(eg. abs)" else "(eg. abs@hotmail.com)",
                        50
                    ),
                    LegacyFormField(
                        "password",
                        "Password",
                        "Your " + provider + " Password",
                        "* " + provider + " password\n\nPlease enter the password for your " + provider + " account here.",
                        50,
                        password = true,
                        inputFlags = 65536
                    )
                ),
                values = mapOf("username" to username, "password" to password),
                onValueChange = { key, value -> if (key == "username") username = value else password = value },
                modifier = Modifier.fillMaxWidth()
            )
                LegacyFunctionBar(
                    leftLabel = "Login",
                    rightLabel = "Cancel",
                    modifier = Modifier.fillMaxWidth(),
                    onLeftClick = onLogin,
                    onRightClick = onCancel
                )
            }
        }
    }
}
