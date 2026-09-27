package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.ui.LegacyBackground
import com.cyanideph.java.legacy.ui.LegacyText
import com.cyanideph.java.legacy.theme.ReptilianTheme

private data class LegacyMenuItem(
    val label: String,
    val small: String,
    val large: String,
    val onClick: () -> Unit
)

@Composable
fun MainMenuScreen(
    onBuddies: () -> Unit,
    onMessages: () -> Unit,
    onRooms: () -> Unit,
    onSettings: () -> Unit,
    onHelp: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    var selected by remember { mutableIntStateOf(0) }
    var showOptions by remember { mutableStateOf(false) }

    val items = listOf(
        LegacyMenuItem("Subscription", "000-smart-small", "000-smart-large") { },
        LegacyMenuItem("Buddy Matching", "001-abm-small", "001-abm-large") { },
        LegacyMenuItem("Add or Invite Buddies", "002-buddies-small", "002-buddies-large", onBuddies),
        LegacyMenuItem("Settings", "003-settings-small", "003-settings-large", onSettings),
        LegacyMenuItem("Silent Mode", "008-ringtone-small", "008-ringtone-large") { },
        LegacyMenuItem("Themes", "005-themes-small", "005-themes-large") { },
        LegacyMenuItem("Help", "006-help-small", "006-help-large", onHelp),
        LegacyMenuItem("Battery Saving", "007-batteryinfo-small", "007-batteryinfo-large") { },
        LegacyMenuItem("Extended Messaging", "d000-em-small", "d000-em-large", onMessages),
        LegacyMenuItem("Instant Messaging", "d001-im-small", "d001-im-large", onMessages),
        LegacyMenuItem("Chat Rooms", "d002-chat-small", "d002-chat-large", onRooms),
        LegacyMenuItem("Change Status", "d003-status-small", "d003-status-large") { }
    )

    val columns = remember {
        val widths = items.map {
            LegacyAssets.bitmap(context, it.large).width
        }
        maxOf(1, 360 / maxOf(1, widths.maxOrNull() ?: 1))
    }

    LegacyBackground(Modifier.fillMaxSize()) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val availableWidthPx = with(density) { maxWidth.toPx() }
            val maxIconWidth = items.maxOf { LegacyAssets.bitmap(context, it.large).width.toFloat() }
            val columnCount = maxOf(1, (availableWidthPx / (maxIconWidth + 16f)).toInt())
            val spacingPx = ((availableWidthPx - columnCount * maxIconWidth) / (columnCount + 1f)).coerceAtLeast(4f)
            val spacing = with(density) { spacingPx.toDp() }

            Column(Modifier.fillMaxSize()) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    items.chunked(columnCount).forEach { row ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally)
                        ) {
                            row.forEach { item ->
                                val index = items.indexOf(item)
                                val path = if (index == selected) item.large else item.small
                                val bitmap = LegacyAssets.rememberBitmap(context, path)
                                val w = with(density) { bitmap.width.toDp() }
                                val h = with(density) { bitmap.height.toDp() }
                                Column(
                                    Modifier
                                        .width(w)
                                        .clickable {
                                            selected = index
                                            item.onClick()
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(Modifier.height(4.dp))
                                    Image(
                                        bitmap,
                                        contentDescription = item.label,
                                        Modifier.size(w, h),
                                        contentScale = ContentScale.None
                                    )
                                    Spacer(Modifier.height(2.dp))
                                }
                            }
                        }
                    }
                }

                LegacyText(
                    text = items.getOrNull(selected)?.label.orEmpty(),
                    Modifier
                        .fillMaxWidth()
                        .clickable { showOptions = true }
                        .wrapContentHeight()
                        .padding(vertical = 8.dp),
                )

                Image(
                    LegacyAssets.rememberBitmap(context, "themes/uzzap/menu-bottombar.png"),
                    contentDescription = null,
                    Modifier
                        .fillMaxWidth()
                        .height(with(density) {
                            LegacyAssets.bitmap(context, "themes/uzzap/menu-bottombar.png").height.toDp()
                        }),
                    contentScale = ContentScale.FillBounds
                )
            }

            if (showOptions) {
                LegacyOptionsPopup(
                    onDismiss = { showOptions = false }
                )
            }
        }
    }
}

@Composable
private fun LegacyOptionsPopup(onDismiss: () -> Unit) {
    val options = listOf(
        "Lock Keypad",
        "Log Off",
        "Intro Help Screen",
        "About Uzzap",
        "Exit Application"
    )
    Box(
        Modifier
            .fillMaxSize()
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.layout.Column(
            Modifier
                .wrapContentWidth()
                .clickable { }
                .background(ReptilianTheme.Surface)
        ) {
            options.forEach {
                LegacyText(it, Modifier.padding(horizontal = 18.dp, vertical = 9.dp))
            }
        }
    }
}
