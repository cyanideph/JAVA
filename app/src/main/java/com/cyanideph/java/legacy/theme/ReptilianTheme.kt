package com.cyanideph.java.legacy.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

object ReptilianTheme {
    val menuSelected = Color(0xFFB6B6B6)
    val MenuSelected get() = menuSelected
    val text = Color(0xFF000000)
    val titleBarText = Color(0xFFFFFFFF)
    val functionBarText = Color(0xFFFFFFFF)
    val popupBackground = Color(0xFFFFFFFF)
    val editorBackground = Color(0xFFDFDFDF)
    val editorBorder = Color(0xFF868686)
    val specialText = Color(0xFFAF0C0D)
    val otherSpecialText = Color(0xFF0D0CAF)
    val panelBackground = Color(0xFFFFFFFF)
    val mainMenuBar = Color(0xFFDFDFDF)
    val scrollbarFill = Color(0xFF868686)
    val scrollbarBackground = Color(0xFFDFDFDF)

    val callDisplaySize = 20.sp
    val statusDisplaySize = 20.sp
    val statusErrorSize = 14.sp
    val standardSize = 14.sp

    // Compatibility aliases used by legacy-parity screens. Keep these mapped to the
    // canonical theme values so screens cannot silently drift from the Java theme.
    val Surface get() = panelBackground
    val Text get() = text
    val TitleBarText get() = titleBarText
    val FunctionBarText get() = functionBarText
    val PopupBackground get() = popupBackground
    val EditorBackground get() = editorBackground
    val EditorBorder get() = editorBorder
    val SpecialText get() = specialText
    val OtherSpecialText get() = otherSpecialText
    val MainMenuBar get() = mainMenuBar
    val ScrollbarFill get() = scrollbarFill
    val ScrollbarBackground get() = scrollbarBackground
    val FontSize get() = standardSize
}