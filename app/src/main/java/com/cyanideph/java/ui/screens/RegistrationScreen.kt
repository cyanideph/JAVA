package com.cyanideph.java.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

/** Exact first-stage field model from com.kolipri.kalypte.au register flow. */
@Composable
fun RegistrationScreen(onBack: () -> Unit) {
    val fields = listOf(
        LegacyFormField("firstname", "First Name", "Your first name", "Please enter your first name as it will appear on your profile.", 60),
        LegacyFormField("lastname", "Last Name", "Your last name", "Please enter your last (family) name as it will appear on your profile.", 60),
        LegacyFormField("email", "Email Address", "Your email address", "Please enter your current email address. This will be used to send your password should you forget it.", 700),
        LegacyFormField("password", "Password", "Choose your password", "Please enter your desired password. Password should be at least 6 characters in length.", 30, true, 65536),
        LegacyFormField("password2", "Re-enter password", "Password verification", "Please re-enter your desired password for verification", 30, true, 65536),
        LegacyFormField("credit", "Credit", "Credit", "Optional.  Enter the Userid of a person who helped you register.", 12)
    )
    var values by remember { mutableStateOf(emptyMap<String, String>()) }
    var error by remember { mutableStateOf<String?>(null) }

    fun validName(value: String) = value.all { it.isLetterOrDigit() || it.isWhitespace() }
    fun validPassword(value: String) = value.all { it.isLetterOrDigit() || it.isWhitespace() }
    fun validEmail(value: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()

    fun submit() {
        val first = values["firstname"].orEmpty()
        val last = values["lastname"].orEmpty()
        val email = values["email"].orEmpty()
        val pass = values["password"].orEmpty()
        val pass2 = values["password2"].orEmpty()
        error = when {
            first.isEmpty() -> "Missing value for 'First Name'."
            !validName(first) -> "Your firstname must not contain special symbols."
            last.isEmpty() -> "Missing value for 'Last Name'."
            !validName(last) -> "Your lastname must not contain special symbols."
            email.isEmpty() -> "Missing value for 'Email Address'."
            email.length < 3 || !validEmail(email) -> "Invalid entry for 'Email Address'."
            pass.isEmpty() || pass.length < 6 -> "Password must be at least 6 characters in length."
            !validPassword(pass) -> "Your password must not contain special symbols."
            pass2.isEmpty() -> "Missing value for 'Re-enter Password'."
            pass != pass2 -> "Passwords don't match."
            else -> null
        }
    }

    LegacyBackground(Modifier.fillMaxSize(), ReptilianTheme.Surface) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Register account", Modifier.fillMaxWidth())
            LegacyFormList(
                fields = fields,
                values = values,
                onValueChange = { key, value -> values = values + (key to value) },
                modifier = Modifier.weight(1f).fillMaxWidth().padding(8.dp)
            )
            LegacyFunctionBar(
                leftLabel = "Register",
                rightLabel = "Cancel",
                modifier = Modifier.fillMaxWidth().clickable { submit() }
            )
        }
    }

    error?.let { message ->
        LegacyDialogMessage(
            text = "* Incorrect data\\n\\nPlease make sure to fill out all fields as instructed:\\n\\n$message",
            onDismiss = { error = null }
        )
    }
}
