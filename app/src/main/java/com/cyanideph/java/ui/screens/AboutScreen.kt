package com.cyanideph.java.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

@Composable
fun AboutScreen(onBack: () -> Unit) {
    LegacyBackground(Modifier.fillMaxSize(), ReptilianTheme.Surface) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("* About Uzzap", Modifier.fillMaxWidth())
            LegacyFrame(Modifier.fillMaxWidth().weight(1f).padding(8.dp)) {
                Column(Modifier.fillMaxWidth().padding(8.dp)) {
                    LegacyText("Version: 1.0.14")
                    LegacyText("Copyright (c) 2008 3rd Brand Pte Ltd.\nAll Rights Reserved.")
                }
            }
            LegacyFunctionBar(modifier = Modifier.fillMaxWidth(), leftLabel = "Options", rightLabel = "Back", onRightClick = onBack)
        }
    }
}
