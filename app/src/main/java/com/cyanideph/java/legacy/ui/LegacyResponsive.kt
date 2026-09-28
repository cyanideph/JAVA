package com.cyanideph.java.legacy.ui

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Adaptive sizing helpers for the legacy Uzzap renderer.
 *
 * These helpers change available layout space, not the legacy artwork itself.
 * This keeps the original chrome/assets visually stable while preventing
 * dialogs/forms from becoming excessively wide on tablets or large phones.
 */
fun Modifier.legacyAdaptiveDialogWidth(
    horizontalPadding: Dp = 12.dp,
    maxWidth: Dp = 640.dp
): Modifier = this
    .widthIn(max = maxWidth)
    .then(Modifier)

fun BoxWithConstraintsScope.legacyAdaptiveContentWidth(
    maxWidth: Dp = 640.dp
): Dp = this.maxWidth.coerceAtMost(maxWidth)
