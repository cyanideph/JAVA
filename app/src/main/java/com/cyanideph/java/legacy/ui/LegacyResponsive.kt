package com.cyanideph.java.legacy.ui

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.unit.dp

/**
 * Adaptive sizing for the legacy Uzzap renderer.
 *
 * The legacy bitmap chrome remains at its source dimensions; only the
 * available content width is constrained when a device is unusually wide.
 */
fun Modifier.legacyAdaptiveDialogWidth() = this.widthIn(max = 640.dp)

/**
 * Keeps legacy popup menus usable on narrow phones while preventing them from
 * becoming excessively wide on tablets/foldables.
 */
fun Modifier.legacyAdaptivePopupWidth() = this.widthIn(max = 360.dp)
