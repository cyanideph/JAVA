package com.cyanideph.java.legacy.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

object ReptilianTheme {
    val menuSelected = Color(0xFFC0FD85)
    val text = Color(0xFF000000)
    val specialText = Color(0xFFAF0C0D)
    val otherSpecialText = Color(0xFF0D0CAF)
    val panelBackground = Color(0xFFF9F9F9)
    val mainMenuBar = Color(0xFF8EEF04)
    val scrollbarFill = Color(0xFF8EEF04)
    val scrollbarBackground = Color(0xFF05741D)

    val callDisplaySize = 20.sp
    val statusDisplaySize = 20.sp
    val statusErrorSize = 14.sp
    val standardSize = 14.sp

    // Compatibility aliases used by legacy-parity screens. Keep these mapped to the
    // canonical theme values so screens cannot silently drift from the Java theme.
    val Surface get() = panelBackground
    val Text get() = text
    val SpecialText get() = specialText
    val OtherSpecialText get() = otherSpecialText
    val MainMenuBar get() = mainMenuBar
    val ScrollbarFill get() = scrollbarFill
    val ScrollbarBackground get() = scrollbarBackground
    val FontSize get() = standardSize
}