package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import com.cyanideph.java.legacy.ui.LegacyFrame
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.ui.LegacyBackground
import com.cyanideph.java.legacy.ui.LegacyText
import com.cyanideph.java.legacy.ui.LegacyFunctionBar
import com.cyanideph.java.legacy.ui.LegacyAdaptiveDialog
import com.cyanideph.java.legacy.theme.ReptilianTheme

private data class LegacyMenuItem(val label: String, val small: String, val large: String, val onClick: () -> Unit)

@Composable
fun MainMenuScreen(
    onBuddies: () -> Unit,
    onMessages: () -> Unit,
    onInstantMessaging: () -> Unit = onMessages,
    onRooms: () -> Unit,
    onSettings: () -> Unit,
    onHelp: () -> Unit,
    onSubscription: () -> Unit = {},
    onBuddyMatching: () -> Unit = {},
    onAddInvite: () -> Unit = {},
    onThemes: () -> Unit = {},
    onBatterySaving: () -> Unit = {},
    onStatus: () -> Unit = {},
    onSilentMode: (Boolean) -> Unit = {},
    onLogOff: () -> Unit = {},
    onAbout: () -> Unit = {},
    onExit: () -> Unit = {}
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    var selected by remember { mutableIntStateOf(0) }
    var firstRow by remember { mutableIntStateOf(0) }
    var showOptions by remember { mutableStateOf(false) }
    // Java source: Kalypte.n is a runtime ringtone/audio controller; b() simply toggles its boolean state.
    // Keep the same non-persistent runtime toggle here; no extra UI is rendered by the legacy app.
    var silentModeEnabled by remember { mutableStateOf(false) }
    val items = listOf(
        LegacyMenuItem("Subscription", "themes/default/000-smart-small.png", "themes/default/000-smart-large.png", onSubscription),
        LegacyMenuItem("Buddy Matching", "themes/default/001-abm-small.png", "themes/default/001-abm-large.png", onBuddyMatching),
        LegacyMenuItem("Add or Invite Buddies", "themes/default/002-buddies-small.png", "themes/default/002-buddies-large.png", onAddInvite),
        LegacyMenuItem("Settings", "themes/default/003-settings-small.png", "themes/default/003-settings-large.png", onSettings),
        LegacyMenuItem("Silent Mode", "themes/default/008-ringtone-small.png", "themes/default/008-ringtone-large.png", { silentModeEnabled = !silentModeEnabled; onSilentMode(silentModeEnabled) }),
        LegacyMenuItem("Themes", "themes/default/005-themes-small.png", "themes/default/005-themes-large.png", onThemes),
        LegacyMenuItem("Help", "themes/default/006-help-small.png", "themes/default/006-help-large.png", onHelp),
        LegacyMenuItem("Battery Saving", "themes/default/007-batteryinfo-small.png", "themes/default/007-batteryinfo-large.png", onBatterySaving),
        LegacyMenuItem("Extended Messaging", "themes/default/d000-em-small.png", "themes/default/d000-em-large.png", onMessages),
        LegacyMenuItem("Instant Messaging", "themes/default/d001-im-small.png", "themes/default/d001-im-large.png", onInstantMessaging),
        LegacyMenuItem("Chat Rooms", "themes/default/d002-chat-small.png", "themes/default/d002-chat-large.png", onRooms),
        LegacyMenuItem("Change Status", "themes/default/d003-status-small.png", "themes/default/d003-status-large.png", onStatus)
    )
    LegacyBackground(Modifier.fillMaxSize()) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val containerMaxHeight = maxHeight
            val metrics = com.cyanideph.java.legacy.ui.legacyVisualMetrics()
            val cellWidth = metrics.mainMenuLargeIcon
            val cellHeight = metrics.mainMenuLargeIcon
            val columnCount = maxOf(1, (maxWidth / cellWidth).toInt())
            val spacing = ((maxWidth.value - columnCount * cellWidth.value) / (columnCount + 1f)).coerceAtLeast(0f).dp
            val bottomBar = LegacyAssets.rememberBitmap(context, "themes/uzzap/menu-bottombar.png")
            val textBarHeight = (ReptilianTheme.FontSize.value + 8f).dp
            val bottomBarHeight = metrics.menuBottomBarHeight
            val functionBarHeight = metrics.functionBarHeight
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.weight(1f).fillMaxWidth()) {
                    val totalRows = (items.size + columnCount - 1) / columnCount
                    val viewportHeight = (containerMaxHeight - textBarHeight - bottomBarHeight - functionBarHeight).coerceAtLeast(1.dp)
                    val visibleRows = maxOf(1, ((viewportHeight + spacing) / (cellHeight + spacing)).toInt())
                    val needsScroll = totalRows > visibleRows
                    val maxFirstRow = (totalRows - visibleRows).coerceAtLeast(0)
                    firstRow = firstRow.coerceIn(0, maxFirstRow)
                    val selectedRow = selected / columnCount
                    LaunchedEffect(selectedRow, visibleRows, totalRows) {
                        if (selectedRow < firstRow) firstRow = selectedRow
                        else if (selectedRow >= firstRow + visibleRows) firstRow = (selectedRow - visibleRows + 1).coerceAtMost(maxFirstRow)
                    }
                    val visibleItems = items.drop(firstRow * columnCount).take(visibleRows * columnCount)
                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(spacing)) {
                        visibleItems.chunked(columnCount).forEach { row ->
                            Row(Modifier.fillMaxWidth().height(cellHeight), horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally)) {
                                row.forEach { item ->
                                    val index = items.indexOf(item)
                                    val path = if (index == selected) item.large else item.small
                                    val bitmap = LegacyAssets.rememberBitmap(context, path)
                                    val iconSize = if (index == selected) metrics.mainMenuLargeIcon else metrics.mainMenuSmallIcon
                                    Box(Modifier.width(cellWidth).fillMaxHeight().clickable { selected = index; item.onClick() }, contentAlignment = Alignment.Center) {
                                        Image(bitmap, contentDescription = item.label, Modifier.size(iconSize), contentScale = ContentScale.FillBounds)
                                    }
                                }
                            }
                        }
                    }
                    if (needsScroll) {
                        val trackHeightPx = with(density) { viewportHeight.toPx() }.coerceAtLeast(1f)
                        val thumbHeight = (trackHeightPx * visibleRows / totalRows).coerceAtLeast(with(density) { 12.dp.toPx() })
                        val thumbOffset = (trackHeightPx - thumbHeight) * firstRow / maxFirstRow.coerceAtLeast(1)
                        Box(Modifier.align(Alignment.TopEnd).width(8.dp).fillMaxHeight().background(ReptilianTheme.ScrollbarBackground))
                        Box(Modifier.align(Alignment.TopEnd).offset { IntOffset(with(density) { 1.dp.roundToPx() }, thumbOffset.toInt()) }.width(6.dp).height(with(density) { thumbHeight.toDp() }).background(ReptilianTheme.ScrollbarFill))
                    }
                }
                Box(Modifier.fillMaxWidth().height(textBarHeight).background(ReptilianTheme.MainMenuBar).clickable { showOptions = true }, contentAlignment = Alignment.Center) { LegacyText(items.getOrNull(selected)?.label.orEmpty()) }
                Image(bottomBar, contentDescription = null, Modifier.fillMaxWidth().height(bottomBarHeight), contentScale = ContentScale.FillBounds)
                LegacyFunctionBar(leftLabel = "Options", rightLabel = "Exit", modifier = Modifier.fillMaxWidth(), onLeftClick = { showOptions = true }, onRightClick = onExit)
            }
            if (showOptions) LegacyOptionsPopup(onDismiss = { showOptions = false }, onLogOff = onLogOff, onAbout = onAbout, onExit = onExit)
        }
    }
}

@Composable
private fun LegacyOptionsPopup(
    onDismiss: () -> Unit,
    onLogOff: () -> Unit,
    onAbout: () -> Unit,
    onExit: () -> Unit
) {
    val options = listOf(
        "Lock Keypad" to { onDismiss() },
        "Log Off" to { onDismiss(); onLogOff() },
        "Intro Help Screen" to { onDismiss() },
        "About Uzzap" to { onDismiss(); onAbout() },
        "Exit Application" to { onDismiss(); onExit() }
    )
    LegacyAdaptiveDialog(onDismissRequest = onDismiss, maxWidth = 360.dp) {
        LegacyFrame(Modifier.wrapContentWidth()) {
            Column(Modifier.wrapContentWidth().background(ReptilianTheme.Surface)) {
                options.forEach { (label, action) ->
                    LegacyText(label, Modifier.fillMaxWidth().clickable(onClick = action).padding(horizontal = 18.dp, vertical = 9.dp))
                }
            }
        }
    }
}
