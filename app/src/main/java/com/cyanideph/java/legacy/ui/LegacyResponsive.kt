package com.cyanideph.java.legacy.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

fun Modifier.legacyAdaptiveDialogWidth() = widthIn(max = 640.dp)

fun Modifier.legacyAdaptivePopupWidth() = widthIn(max = 360.dp)

@Composable
fun LegacyAdaptiveDialog(
    onDismissRequest: () -> Unit,
    maxWidth: Dp = 640.dp,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(Modifier.fillMaxWidth().widthIn(max = maxWidth)) {
            content()
        }
    }
}
