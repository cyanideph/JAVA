package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

/**
 * Java Uzzap parity for dg.java (startup menu) and k.java (primary login form).
 * Yahoo/MSN are deliberately absent here; they belong to IM -> Connections.
 */
@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var showForm by remember { mutableStateOf(false) }

    if (showForm) {
        LegacyNetworkLoginScreen(
            onCancel = { showForm = false },
            onLogin = onLogin
        )
    } else {
        LegacyLoginLandingScreen(onLogin = { showForm = true })
    }
}

@Composable
private fun LegacyLoginLandingScreen(onLogin: () -> Unit) {
    val context = LocalContext.current

    val items = listOf(
        "Login to Network" to true,
        "Register a New Account" to false,
        "Forgotten Password" to false,
        "Help" to false,
        "About Uzzap" to false,
        "Exit Application" to false
    )

    LegacyBackground(
        modifier = Modifier.fillMaxSize(),
        color = ReptilianTheme.Surface
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.34f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = LegacyAssets.rememberBitmap(
                        context,
                        "themes/default/logo-large.png"
                    ),
                    contentDescription = "Uzzap"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.66f)
            ) {
                items.forEach { (label, enabled) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = enabled) { onLogin() }
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            bitmap = LegacyAssets.rememberBitmap(
                                context,
                                "themes/default/small-arrow.png"
                            ),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(6.dp))
                        LegacyText(label)
                    }
                }
            }

            LegacyFunctionBar(
                leftLabel = "",
                rightLabel = "Exit",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun LegacyNetworkLoginScreen(
    onCancel: () -> Unit,
    onLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAutoLogin by remember { mutableStateOf(false) }

    if (showAutoLogin) {
        Dialog(onDismissRequest = { showAutoLogin = false }) {
            Column(
                modifier = Modifier
                    .background(ReptilianTheme.Surface)
                    .padding(10.dp)
            ) {
                LegacyText("Automatic Login")
                Spacer(Modifier.height(8.dp))
                LegacyText(
                    "Would you like to log in automatically with your username/password when the application is started?"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        showAutoLogin = false
                        onLogin()
                    }) { LegacyText("Yes") }
                    TextButton(onClick = {
                        showAutoLogin = false
                        onLogin()
                    }) { LegacyText("No") }
                }
            }
        }
    }

    LegacyBackground(
        modifier = Modifier.fillMaxSize(),
        color = ReptilianTheme.Surface
    ) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Login to network", Modifier.fillMaxWidth())

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                LegacyText("User ID")
                LegacyText("Your user ID")
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it.take(12) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(Modifier.height(10.dp))

                LegacyText("Password")
                LegacyText("Your password")
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it.take(31) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(Modifier.height(8.dp))
                LegacyText(
                    "* User ID\n\nUser ID you chose upon registering. User ID can be 6 to 12 characters, and can consist of numbers and letters."
                )
                Spacer(Modifier.height(6.dp))
                LegacyText(
                    "* Password\n\nEnter the personal password currently registered for your user account."
                )
            }

            LegacyFunctionBar(
                leftLabel = "Login",
                rightLabel = "Cancel",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (username.isNotBlank() && password.isNotBlank()) {
                            showAutoLogin = true
                        } else {
                            onCancel()
                        }
                    }
            )
        }
    }
}
