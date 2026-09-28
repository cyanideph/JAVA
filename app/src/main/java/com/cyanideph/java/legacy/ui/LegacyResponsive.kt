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

/**
 * Responsive sizing primitives for the legacy Uzzap UI.
 *
 * Legacy bitmap dimensions remain intrinsic where pixel parity matters.
 * Container dimensions, dialogs and popups adapt to the available window.
 */
private const val RESPONSIVE_WIDTH_FRACTION = 0.94f

fun Modifier.legacyAdaptiveDialogWidth() =
    fillMaxWidth(RESPONSIVE_WIDTH_FRACTION).widthIn(max = 640.dp)

fun Modifier.legacyAdaptivePopupWidth() =
    fillMaxWidth(RESPONSIVE_WIDTH_FRACTION).widthIn(max = 360.dp)

fun Modifier.legacyAdaptiveWidth(maxWidth: Dp) =
    fillMaxWidth(RESPONSIVE_WIDTH_FRACTION).widthIn(max = maxWidth)

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
        Box(
            Modifier
                .fillMaxWidth()
                .legacyAdaptiveWidth(maxWidth)
        ) {
            content()
        }
    }
}
