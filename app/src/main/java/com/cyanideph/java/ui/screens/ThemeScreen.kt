package com.cyanideph.java.ui.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.LegacyBackground
import com.cyanideph.java.legacy.ui.LegacyFunctionBar
import com.cyanideph.java.legacy.ui.LegacyText
import com.cyanideph.java.legacy.ui.LegacyTitleBar

/** Legacy c.java theme selector: exact theme catalogue exposed by the original client. */
@Composable
fun ThemeScreen(onBack: () -> Unit) {
    val themes = listOf("black", "dolphins", "hearts", "roses", "uzzap")
    val selected = remember { mutableStateOf("uzzap") }
    val context = LocalContext.current

    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* Change theme", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                themes.forEach { theme ->
                    LegacyText(
                        theme,
                        Modifier.fillMaxWidth()
                            .background(if (theme == selected.value) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                            .clickable { selected.value = theme }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                }
            }
            LegacyFunctionBar(
                leftLabel = "Select",
                rightLabel = "Close",
                modifier = Modifier.fillMaxWidth().clickable { onBack() }
            )
        }
    }
}
