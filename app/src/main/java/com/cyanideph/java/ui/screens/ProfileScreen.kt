package com.cyanideph.java.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

/** Exact field model from com.kolipri.kalypte.dj (Update profile). */
@Composable
fun ProfileScreen(onBack: () -> Unit) {
    val fields = listOf(
        LegacyFormField("displayname", "Nickname", "Your nickname", "* Nickname\\n\\nThis is how other people will see you in the service. If empty, your first and last name are used as defaults.", 50),
        LegacyFormField("firstname", "First name", "Your first name", "* Your first name\\n\\nPlease enter your first name as it will appear on your profile.", 50),
        LegacyFormField("lastname", "Last name", "Your last name", "* Last name\\n\\nPlease enter your last(family) name as it will appear on your profile.", 50),
        LegacyFormField("email", "Email address", "Your email address", "* Your email address\\n\\nPlease enter your current email address.", 50)
    )
    var values by remember { mutableStateOf(emptyMap<String, String>()) }
    var error by remember { mutableStateOf<String?>(null) }

    fun validPlain(value: String) = value.all { it.isLetterOrDigit() || it.isWhitespace() }
    fun submit() {
        val nickname = values["displayname"].orEmpty()
        val first = values["firstname"].orEmpty()
        val last = values["lastname"].orEmpty()
        val email = values["email"].orEmpty()
        error = when {
            nickname.isNotEmpty() && !validPlain(nickname) -> "* Nickname must not contain special symbols"
            last.isEmpty() -> "* Lastname is too short."
            !validPlain(last) -> "* Lastname must not contain special symbols"
            first.isEmpty() -> "* Firstname is too short."
            !validPlain(first) -> "* Firstname must not contain special symbols"
            email.isEmpty() -> "* Incorrect data\\n\\nPlease make sure to fill out all fields as instructed:\\n\\nEmail is too short."
            email.length < 3 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "* Incorrect data\\n\\nPlease make sure to fill out all fields as instructed:\\n\\nEmail address has an invalid Entry"
            else -> null
        }
    }

    LegacyBackground(Modifier.fillMaxSize(), ReptilianTheme.Surface) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Update profile", Modifier.fillMaxWidth())
            LegacyFormList(fields, values, { key, value -> values = values + (key to value) }, Modifier.weight(1f).fillMaxWidth().padding(8.dp))
            LegacyFunctionBar("Update", "Cancel", Modifier.fillMaxWidth().clickable { submit() })
        }
    }
    error?.let { message -> LegacyDialogMessage(message) { error = null } }
}
