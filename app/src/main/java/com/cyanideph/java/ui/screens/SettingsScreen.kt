package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cyanideph.java.legacy.theme.ReptilianTheme
import com.cyanideph.java.legacy.ui.*

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onProfile: () -> Unit = {},
    onStatus: () -> Unit = {},
    onPassword: () -> Unit = {},
    onMobile: () -> Unit = {},
    onOffline: () -> Unit = {},
    onChatroomTones: () -> Unit = {}
) {
    var showOptions by remember { mutableStateOf(false) }
    var selected by remember { mutableIntStateOf(0) }
    val options = listOf("Edit My Profile", "Change Status", "Change Password", "Change Mobile Number", "Offline Settings", "Chatroom Tones")
    fun select(label: String) {
        showOptions = false
        when (label) {
            "Edit My Profile" -> onProfile()
            "Change Status" -> onStatus()
            "Change Password" -> onPassword()
            "Change Mobile Number" -> onMobile()
            "Offline Settings" -> onOffline()
            "Chatroom Tones" -> onChatroomTones()
        }
    }
    LegacyBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LegacyTitleBar("Settings", Modifier.fillMaxWidth())
            Column(Modifier.weight(1f).fillMaxWidth()) {
                options.forEachIndexed { index, label ->
                    LegacyText(
                        label,
                        Modifier.fillMaxWidth()
                            .background(if (index == selected) ReptilianTheme.MenuSelected else ReptilianTheme.Surface)
                            .clickable { selected = index; select(label) }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                }
            }
            LegacyFunctionBar(leftLabel = "Options", rightLabel = "Menu", modifier = Modifier.fillMaxWidth(), onLeftClick = { showOptions = true }, onRightClick = onBack)
        }
    }
    if (showOptions) Dialog(onDismissRequest = { showOptions = false }) {
        Column(Modifier.background(ReptilianTheme.Surface)) {
            options.forEach { label -> LegacyText(label, Modifier.fillMaxWidth().clickable { select(label) }.padding(horizontal = 18.dp, vertical = 9.dp)) }
            LegacyText("Close", Modifier.fillMaxWidth().clickable { showOptions = false; onBack() }.padding(horizontal = 18.dp, vertical = 9.dp))
        }
    }
}
