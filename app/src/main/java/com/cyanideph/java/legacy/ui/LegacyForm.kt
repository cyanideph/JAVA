package com.cyanideph.java.legacy.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

data class LegacyFormField(
    val key: String,
    val label: String,
    val description: String,
    val help: String,
    val maxLength: Int = 700,
    val password: Boolean = false
)

@Composable
fun LegacyFormList(
    fields: List<LegacyFormField>,
    values: Map<String, String>,
    onValueChange: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var editing by remember { mutableStateOf<String?>(null) }
    LegacyFrame(modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth()) {
            fields.forEach { field ->
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clickable { editing = field.key }
                        .padding(horizontal = 6.dp, vertical = 5.dp)
                ) {
                    LegacyText(field.label)
                    val shown = values[field.key].orEmpty()
                    LegacyText(if (field.password) "*".repeat(shown.length) else shown)
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
    Dialog(onDismissRequest = onCancel) {
        LegacyFrame(Modifier.fillMaxWidth().padding(16.dp)) {
            Column(Modifier.fillMaxWidth().padding(10.dp)) {
                LegacyText(field.description)
                OutlinedTextField(
                    value = draft,
                    onValueChange = { draft = it.take(field.maxLength) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
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
