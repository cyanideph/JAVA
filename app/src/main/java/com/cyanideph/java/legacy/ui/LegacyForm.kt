package com.cyanideph.java.legacy.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.theme.ReptilianTheme


data class LegacyFormField(
    val key: String,
    val label: String,
    val description: String,
    val help: String,
    val maxLength: Int = 700,
    val password: Boolean = false,
    val inputFlags: Int = 0
)

@Composable
fun LegacyFormList(
    fields: List<LegacyFormField>,
    values: Map<String, String>,
    onValueChange: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var editing by remember { mutableStateOf<String?>(null) }
    val metrics = legacyVisualMetrics()
    LegacyFrame(modifier.fillMaxWidth()) {
        // Keep form content inside the legacy frame's drawable border.
        // Without this inner inset, the first glyph of each label can render
        // underneath the left frame edge on narrow/legacy-scaled viewports.
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = metrics.space6)
        ) {
            fields.forEach { field ->
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clickable { editing = field.key }
                        .padding(horizontal = 6.dp, vertical = 5.dp)
                ) {
                    LegacyText(field.label)
                    val shown = values[field.key].orEmpty()
                    LegacyText(shown)
                }
            }
        }
    }
    editing?.let { key ->
        fields.firstOrNull { it.key == key }?.let { field ->
            LegacyFieldEditor(
                field = field,
                value = values[field.key].orEmpty(),
                onCommit = { onValueChange(field.key, it); editing = null },
                onCancel = { editing = null }
            )
        }
    }
}

@Composable
private fun LegacyFieldEditor(
    field: LegacyFormField,
    value: String,
    onCommit: (String) -> Unit,
    onCancel: () -> Unit
) {
    var draft by remember(field.key, value) { mutableStateOf(value) }
    val constraint = field.inputFlags and 0xFFFF
    val isPassword = field.password || field.inputFlags == 65536
    val keyboardType = when (constraint) {
        2 -> KeyboardType.Number
        3 -> KeyboardType.Phone
        5 -> KeyboardType.Decimal
        1 -> KeyboardType.Email
        4 -> KeyboardType.Uri
        else -> if (isPassword) KeyboardType.Password else KeyboardType.Text
    }
    fun acceptInput(input: String): String = when (constraint) {
        2 -> input.filter { it.isDigit() }
        3 -> input.filter { it.isDigit() || it == '+' || it == '*' || it == '#' || it == ' ' || it == '-' || it == '(' || it == ')' }
        5 -> input.filter { it.isDigit() || it == '.' || it == '-' }
        else -> input
    }.take(field.maxLength)
    LegacyAdaptiveDialog(onDismissRequest = onCancel) {
        LegacyFrame(
            Modifier
                .fillMaxWidth()
                .legacyAdaptiveDialogWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            Column(Modifier.fillMaxWidth().padding(10.dp)) {
                LegacyText(field.description)
                if (field.help.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    LegacyText(field.help)
                }
                Spacer(Modifier.height(6.dp))
                BasicTextField(
                    value = draft,
                    onValueChange = { draft = acceptInput(it) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ReptilianTheme.EditorBackground)
                        .border(BorderStroke(1.dp, ReptilianTheme.EditorBorder))
                        .padding(horizontal = 4.dp, vertical = 3.dp),
                    decorationBox = { innerTextField ->
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                            innerTextField()
                        }
                    }
                )
                Spacer(Modifier.height(6.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    LegacyText("OK", Modifier.clickable { onCommit(draft) }.padding(6.dp))
                    LegacyText("Cancel", Modifier.clickable(onClick = onCancel).padding(6.dp))
                }
            }
        }
    }
}


@Composable
fun LegacyDialogMessage(
    text: String,
    onDismiss: () -> Unit
) {
    LegacyAdaptiveDialog(onDismissRequest = onDismiss) {
        LegacyFrame(Modifier.fillMaxWidth().padding(16.dp)) {
            Column(Modifier.fillMaxWidth().padding(10.dp)) {
                LegacyText(text)
                LegacyFunctionBar(
                    leftLabel = "",
                    rightLabel = "OK",
                    modifier = Modifier.fillMaxWidth().clickable(onClick = onDismiss)
                )
            }
        }
    }
}
