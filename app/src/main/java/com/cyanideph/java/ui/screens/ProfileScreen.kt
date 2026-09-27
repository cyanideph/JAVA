package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    var nickname by remember { mutableStateOf("") }
    var first by remember { mutableStateOf("") }
    var last by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    fun validate(): Boolean {
        when {
            last.isBlank() -> { error = "* Lastname is too short."; return false }
            first.isBlank() -> { error = "* Firstname is too short."; return false }
            email.isBlank() -> { error = "* Incorrect data\n\nPlease make sure to fill out all fields as instructed:\n\nEmail is too short."; return false }
            email.length < 3 -> { error = "* Incorrect data\n\nPlease make sure to fill out all fields as instructed:\n\nEmail address has an invalid Entry"; return false }
            !email.contains("@") -> { error = "* Incorrect data\n\nPlease make sure to fill out all fields as instructed:\n\nEmail address has an invalid Entry"; return false }
        }
        return true
    }

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Update profile", Modifier.fillMaxWidth())
            Column(
                Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()).padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LegacyProfileField("Nickname", nickname, { nickname = it })
                LegacyProfileField("First name", first, { first = it })
                LegacyProfileField("Last name", last, { last = it })
                LegacyProfileField("Email address", email, { email = it })
            }
            LegacyFunctionBar(
                leftLabel = "Update",
                rightLabel = "Cancel",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (error != null) {
        AlertDialog(
            onDismissRequest = { error = null },
            title = { LegacyText("Error") },
            text = { LegacyText(error.orEmpty()) },
            confirmButton = {
                TextButton(onClick = { error = null }) { LegacyText("OK") }
            }
        )
    }
}

@Composable
private fun LegacyProfileField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        LegacyText(label)
        BasicTextField(
            value = value,
            onValueChange = { if (it.length <= 50) onValueChange(it) },
            singleLine = true,
            cursorBrush = SolidColor(ReptilianTheme.Text),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = ReptilianTheme.Text,
                fontSize = ReptilianTheme.standardSize
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(ReptilianTheme.panelBackground)
                .border(1.dp, ReptilianTheme.Text)
                .padding(horizontal = 3.dp, vertical = 5.dp)
        )
    }
}
