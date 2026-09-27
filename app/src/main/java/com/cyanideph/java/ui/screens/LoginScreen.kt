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
    var page by remember { mutableStateOf("landing") }

    when (page) {
        "login" -> LegacyNetworkLoginScreen(
            onCancel = { page = "landing" },
            onLogin = onLogin
        )
        "register" -> LegacyRegisterAccountScreen(
            onCancel = { page = "landing" }
        )
        "forgot-password" -> LegacyForgotPasswordScreen(
            onCancel = { page = "landing" }
        )
        else -> LegacyLoginLandingScreen(
            onLogin = { page = "login" },
            onRegister = { page = "register" },
            onForgotPassword = { page = "forgot-password" }
        )
    }
}

@Composable
private fun LegacyLoginLandingScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onForgotPassword: () -> Unit = {}
) {
    val context = LocalContext.current

    val items = listOf(
        "Login to Network" to "login",
        "Register a New Account" to "register",
        "Forgotten Password" to "forgot-password",
        "Help" to "disabled",
        "About Uzzap" to "disabled",
        "Exit Application" to "disabled"
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
                items.forEach { (label, action) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = action != "disabled") {
                                if (action == "login") onLogin()
                                if (action == "register") onRegister()
                                if (action == "forgot-password") onForgotPassword()
                            }
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
private fun LegacyRegisterAccountScreen(onCancel: () -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var credit by remember { mutableStateOf("") }

    LegacyBackground(
        modifier = Modifier.fillMaxSize(),
        color = ReptilianTheme.Surface
    ) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Register account", Modifier.fillMaxWidth())

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                LegacyText("First Name")
                LegacyText("Your first name")
                OutlinedTextField(firstName, { firstName = it.take(60) }, Modifier.fillMaxWidth(), singleLine = true)

                Spacer(Modifier.height(6.dp))
                LegacyText("Last Name")
                LegacyText("Your last name")
                OutlinedTextField(lastName, { lastName = it.take(60) }, Modifier.fillMaxWidth(), singleLine = true)

                Spacer(Modifier.height(6.dp))
                LegacyText("Email Address")
                LegacyText("Your email address")
                OutlinedTextField(email, { email = it }, Modifier.fillMaxWidth(), singleLine = true)

                Spacer(Modifier.height(6.dp))
                LegacyText("Password")
                LegacyText("Choose your password")
                OutlinedTextField(password, { password = it.take(30) }, Modifier.fillMaxWidth(), singleLine = true)

                Spacer(Modifier.height(6.dp))
                LegacyText("Re-enter password")
                LegacyText("Password verification")
                OutlinedTextField(password2, { password2 = it.take(30) }, Modifier.fillMaxWidth(), singleLine = true)

                Spacer(Modifier.height(6.dp))
                LegacyText("Credit")
                LegacyText("Credit")
                OutlinedTextField(credit, { credit = it.take(12) }, Modifier.fillMaxWidth(), singleLine = true)
            }

            LegacyFunctionBar(
                leftLabel = "Register",
                rightLabel = "Cancel",
                modifier = Modifier.fillMaxWidth().clickable { onCancel() }
            )
        }
    }
}


@Composable
private fun LegacyForgotPasswordScreen(onCancel: () -> Unit) {
    var value by remember { mutableStateOf("") }

    LegacyBackground(
        modifier = Modifier.fillMaxSize(),
        color = ReptilianTheme.Surface
    ) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar(
                "Forgotten Password",
                Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                LegacyText("User ID / Mobile Number")
                LegacyText("User ID/Mobile Number")
                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it.take(30) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                LegacyText(
                    "Your User ID and Password will be sent to the Email address on your account."
                )
            }

            LegacyFunctionBar(
                leftLabel = "OK",
                rightLabel = "Cancel",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCancel() }
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
