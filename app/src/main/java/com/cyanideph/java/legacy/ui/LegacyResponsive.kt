package com.cyanideph.java.legacy.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

private const val RESPONSIVE_WIDTH_FRACTION = 0.94f
private const val LEGACY_REFERENCE_WIDTH_DP = 320f
private const val LEGACY_MIN_SCALE = 0.82f
private const val LEGACY_MAX_SCALE = 1.20f

/**
 * Shared coordinate system for legacy Uzzap artwork.
 *
 * Legacy source dimensions are visual units, not Android physical pixels.
 * They remain proportional while the whole legacy coordinate system gently
 * scales for narrow/wide Android viewports.
 */
data class LegacyVisualMetrics(
    val scale: Float,
    val titleBarHeight: Dp,
    val functionBarHeight: Dp,
    val menuBottomBarHeight: Dp,
    val mainMenuLargeIcon: Dp,
    val mainMenuSmallIcon: Dp,
    val tabWidth: Dp,
    val tabSelectedHeight: Dp,
    val tabUnselectedHeight: Dp,
    val checkboxSize: Dp,
    val presenceIconSize: Dp
)

private val DefaultLegacyVisualMetrics = LegacyVisualMetrics(
    scale = 1f,
    titleBarHeight = 32.dp,
    functionBarHeight = 32.dp,
    menuBottomBarHeight = 64.dp,
    mainMenuLargeIcon = 57.dp,
    mainMenuSmallIcon = 43.dp,
    tabWidth = 38.dp,
    tabSelectedHeight = 26.dp,
    tabUnselectedHeight = 22.dp,
    checkboxSize = 18.dp,
    presenceIconSize = 16.dp
)

@Composable
@ReadOnlyComposable
fun legacyVisualMetrics(): LegacyVisualMetrics {
    val widthDp = LocalConfiguration.current.screenWidthDp.toFloat().coerceAtLeast(1f)
    val scale = (widthDp / LEGACY_REFERENCE_WIDTH_DP)
        .coerceIn(LEGACY_MIN_SCALE, LEGACY_MAX_SCALE)
    return LegacyVisualMetrics(
        scale = scale,
        titleBarHeight = (32f * scale).dp,
        functionBarHeight = (32f * scale).dp,
        menuBottomBarHeight = (64f * scale).dp,
        mainMenuLargeIcon = (57f * scale).dp,
        mainMenuSmallIcon = (43f * scale).dp,
        tabWidth = (38f * scale).dp,
        tabSelectedHeight = (26f * scale).dp,
        tabUnselectedHeight = (22f * scale).dp,
        checkboxSize = (18f * scale).dp,
        presenceIconSize = (16f * scale).dp
    )
}

@Composable
fun LegacyVisualSize(legacyPx: Int): Dp =
    (legacyPx * legacyVisualMetrics().scale).dp

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
