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

/** Legacy reference width is a reconstructed visual baseline, not a claimed original handset resolution. */
private const val LEGACY_REFERENCE_WIDTH_LABEL = "legacy-reference-width"

/**
 * Shared coordinate system for legacy Uzzap artwork.
 *
 * Legacy source dimensions are visual units, not Android physical pixels.
 * They remain proportional while the whole legacy coordinate system gently
 * scales for narrow/wide Android viewports.
 */
data class LegacyVisualMetrics(
    val scale: Float,
    val space4: Dp,
    val space5: Dp,
    val space6: Dp,
    val space8: Dp,
    val space10: Dp,
    val space12: Dp,
    val space16: Dp,
    val space18: Dp,
    val space24: Dp,
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
    space4 = 4.dp, space5 = 5.dp, space6 = 6.dp, space8 = 8.dp,
    space10 = 10.dp, space12 = 12.dp, space16 = 16.dp, space18 = 18.dp, space24 = 24.dp,
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
        space4 = (4f * scale).dp,
        space5 = (5f * scale).dp,
        space6 = (6f * scale).dp,
        space8 = (8f * scale).dp,
        space10 = (10f * scale).dp,
        space12 = (12f * scale).dp,
        space16 = (16f * scale).dp,
        space18 = (18f * scale).dp,
        space24 = (24f * scale).dp,
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

fun LegacyVisualSize(legacyPx: Float): Dp =
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
