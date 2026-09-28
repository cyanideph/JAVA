package com.cyanideph.java.legacy.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlin.jvm.JvmName

object ReptilianTheme {
    val menuSelected = Color(0xFFB6B6B6)
    @get:JvmName("getMenuSelectedCompat")
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
    @get:JvmName("getTextCompat")
    val Text get() = text
    @get:JvmName("getTitleBarTextCompat")
    val TitleBarText get() = titleBarText
    @get:JvmName("getFunctionBarTextCompat")
    val FunctionBarText get() = functionBarText
    @get:JvmName("getPopupBackgroundCompat")
    val PopupBackground get() = popupBackground
    @get:JvmName("getEditorBackgroundCompat")
    val EditorBackground get() = editorBackground
    @get:JvmName("getEditorBorderCompat")
    val EditorBorder get() = editorBorder
    @get:JvmName("getSpecialTextCompat")
    val SpecialText get() = specialText
    @get:JvmName("getOtherSpecialTextCompat")
    val OtherSpecialText get() = otherSpecialText
    @get:JvmName("getMainMenuBarCompat")
    val MainMenuBar get() = mainMenuBar
    @get:JvmName("getScrollbarFillCompat")
    val ScrollbarFill get() = scrollbarFill
    @get:JvmName("getScrollbarBackgroundCompat")
    val ScrollbarBackground get() = scrollbarBackground
    @get:JvmName("getFontSizeCompat")
    val FontSize get() = standardSize
}