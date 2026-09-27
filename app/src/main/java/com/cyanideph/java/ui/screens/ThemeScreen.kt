package com.cyanideph.java.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.assets.LegacyThemeState
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.LegacyBackground
import com.cyanideph.java.legacy.ui.LegacyFunctionBar
import com.cyanideph.java.legacy.ui.LegacyText
import com.cyanideph.java.legacy.ui.LegacyTitleBar

/** Legacy c.java theme selector: exact theme catalogue exposed by the original client. */
@Composable
fun ThemeScreen(onBack: () -> Unit) {
    val themes = listOf("black", "dolphins", "hearts", "roses", "uzzap")
    val selected = remember { mutableStateOf("default") }

    LegacyThemeState.ensure(LocalContext.current)
    selected.value = LegacyThemeState.current

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Change theme", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                val context = LocalContext.current
                val density = LocalDensity.current
                val arrow = LegacyAssets.rememberBitmap(context, "small-arrow")
                themes.forEach { theme ->
                    androidx.compose.foundation.layout.Row(
                        Modifier.fillMaxWidth()
                            .background(if (theme == selected.value) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                            .clickable {\n                                selected.value = theme\n                                LegacyThemeState.select(context, theme)\n                            }
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            arrow,
                            contentDescription = null,
                            modifier = Modifier.size(
                                with(density) { arrow.width.toDp() },
                                with(density) { arrow.height.toDp() }
                            ),
                            contentScale = ContentScale.None
                        )
                        LegacyText(theme, Modifier.padding(start = 4.dp))
                    }
                }
            }
            LegacyFunctionBar(
                leftLabel = "",
                rightLabel = "Close",
                modifier = Modifier.fillMaxWidth(),
                onRightClick = onBack
            )
        }
    }
}
