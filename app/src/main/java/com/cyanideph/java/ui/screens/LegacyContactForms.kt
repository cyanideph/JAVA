package com.cyanideph.java.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.ui.*

private fun emailInvalid(value: String): Boolean =
    value.isNotEmpty() && !Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$").matches(value)

@Composable
fun AddOtherContactScreen(onBack: () -> Unit) {
    var nickname by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val fields = listOf(
        LegacyFormField("custom-displayname", "Nickname", "Nickname", "* Nickname\n\nThis is the name that you will see in your buddy list for this contact.", 50),
        LegacyFormField("custom-mobile", "Mobile number", "Mobile number", "* Mobile number\n\nThis is the mobile number where SMS messages for this contact will be sent.", 13, inputFlags = 3),
        LegacyFormField("custom-email", "Email Address", "Email Address", "* Custom email\n\nThis is the email address where email messages for this contact will be sent", 50)
    )

    fun save() {
        error = when {
            nickname.any { !it.isLetterOrDigit() && !it.isWhitespace() } -> "* Nickname must not contain special symbols"
            mobile.isBlank() -> "* Mobile number is required. Please fill in."
            email.length in 1..2 || emailInvalid(email) ->
                "* Incorrect data\n\nPlease make sure to fill out all fields as instructed:\n\nEmail address has an invalid Entry"
            else -> "Contact validation complete. Legacy service transport is required to save the contact."
        }
    }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Add Other Contact", Modifier.fillMaxWidth())
            LegacyFormList(
                fields,
                mapOf("custom-displayname" to nickname, "custom-mobile" to mobile, "custom-email" to email),
                { key, value ->
                    when (key) {
                        "custom-displayname" -> nickname = value
                        "custom-mobile" -> mobile = value
                        "custom-email" -> email = value
                    }
                },
                Modifier.weight(1f).fillMaxWidth().padding(6.dp)
            )
            LegacyFunctionBar(leftLabel = "OK", rightLabel = "Cancel", modifier = Modifier.fillMaxWidth(), onLeftClick = { save() }, onRightClick = onBack)
        }
    }
    error?.let { LegacyDialogMessage(it) { error = null } }
}

@Composable
fun EditBuddyScreen(onBack: () -> Unit) {
    var nickname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val fields = listOf(
        LegacyFormField("custom-displayname", "Nickname", "Nickname", "", 50),
        LegacyFormField("custom-email", "Email address", "Email", "", 50)
    )
    fun save() {
        error = when {
            nickname.any { !it.isLetterOrDigit() && !it.isWhitespace() } ->
                "* Nickname must not contain special symbols"
            email.length in 1..2 || emailInvalid(email) ->
                "* Email address has an invalid Entry"
            else -> "Contact validation complete. Legacy service transport is required to save the contact."
        }
    }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Edit Buddy", Modifier.fillMaxWidth())
            LegacyFormList(
                fields,
                mapOf("custom-displayname" to nickname, "custom-email" to email),
                { key, value -> if (key == "custom-displayname") nickname = value else email = value },
                Modifier.weight(1f).fillMaxWidth().padding(6.dp)
            )
            LegacyFunctionBar(leftLabel = "OK", rightLabel = "Cancel", modifier = Modifier.fillMaxWidth().clickable { save() })
        }
    }
    error?.let { LegacyDialogMessage(it) { error = null } }
}

@Composable
fun EditSmsBuddyScreen(onBack: () -> Unit) {
    var nickname by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val fields = listOf(
        LegacyFormField("custom-displayname", "Nickname", "Nickname", "* Nickname\n\nThis is the name that you will see in your buddy list for this contact.", 50),
        LegacyFormField("custom-mobile", "Mobile number", "Mobile number", "* Mobile number\n\nThis is the mobile number where SMS messages for this contact will be sent.", 13, inputFlags = 3),
        LegacyFormField("custom-email", "Email Address", "Email Address", "* Custom email\n\nThis is the email address where email messages for this contact will be sent", 50)
    )
    fun save() {
        error = when {
            nickname.any { !it.isLetterOrDigit() && !it.isWhitespace() } -> "* Nickname must not contain special symbols"
            mobile.isBlank() -> "* Mobile number is required. Please fill in."
            email.length in 1..2 || emailInvalid(email) ->
                "* Incorrect data\n\nPlease make sure to fill out all fields as instructed:\n\nEmail address has an invalid Entry"
            else -> "Contact validation complete. Legacy service transport is required to save the contact."
        }
    }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Edit Other Contact", Modifier.fillMaxWidth())
            LegacyFormList(
                fields,
                mapOf("custom-displayname" to nickname, "custom-mobile" to mobile, "custom-email" to email),
                { key, value ->
                    when (key) {
                        "custom-displayname" -> nickname = value
                        "custom-mobile" -> mobile = value
                        "custom-email" -> email = value
                    }
                },
                Modifier.weight(1f).fillMaxWidth().padding(6.dp)
            )
            LegacyFunctionBar(leftLabel = "OK", rightLabel = "Cancel", modifier = Modifier.fillMaxWidth().clickable { save() })
        }
    }
    error?.let { LegacyDialogMessage(it) { error = null } }
}

@Composable
fun ValidationScreen(mode: String, onBack: () -> Unit) {
    var mobile by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val mobileField = LegacyFormField(
        "mobile", "Your mobile number", "Your mobile number",
        "Please enter your mobile phone number in full international format including country code (for example +63918_____)",
        13, inputFlags = 3
    )
    val pinField = LegacyFormField("pin", "Enter your Pin", "Enter your Pin", "", 6)
    val fields = if (mode == "pin") listOf(pinField) else listOf(mobileField)
    fun submit() {
        if (mode == "mobile") {
            error = when {
                mobile.isBlank() -> "Missing value for mobile number."
                mobile.length < 10 -> "Mobile is in invalid format."
                else -> "Mobile validation complete. Legacy service transport is required for PIN delivery."
            }
        } else {
            error = if (pin.isBlank()) "Missing value for pin" else
                "PIN validation complete. Legacy service transport is required for registration."
        }
    }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar(if (mode == "mobile") "Please check your mobile number:" else "Enter your Pin \n\n or \"Exit\" to check your SMS Inbox", Modifier.fillMaxWidth())
            LegacyFormList(
                fields,
                if (mode == "mobile") mapOf("mobile" to mobile) else mapOf("pin" to pin),
                { _, value -> if (mode == "mobile") mobile = value else pin = value },
                Modifier.weight(1f).fillMaxWidth().padding(6.dp)
            )
            LegacyFunctionBar(
                leftLabel = "OK",
                rightLabel = if (mode == "mobile") "Cancel" else "Exit",
                modifier = Modifier.fillMaxWidth(), onLeftClick = { submit() }, onRightClick = onBack)
            )
        }
    }
    error?.let { LegacyDialogMessage(it) { error = null } }
}
