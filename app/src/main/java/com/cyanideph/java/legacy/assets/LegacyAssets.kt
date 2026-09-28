package com.cyanideph.java.legacy.assets

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap

object LegacyThemeState {
    private const val DEFAULT_THEME = "default"
    private val bundledThemes = setOf("default", "uzzap")

    var current by mutableStateOf(DEFAULT_THEME)
        private set

    private var loaded = false

    fun ensure(context: Context) {
        if (!loaded) {
            val saved = context.getSharedPreferences("kalypte.theme", Context.MODE_PRIVATE)
                .getString("kalypte.theme", DEFAULT_THEME) ?: DEFAULT_THEME
            current = if (saved in bundledThemes) saved else DEFAULT_THEME
            loaded = true
        }
    }

    fun select(context: Context, theme: String) {
        val selected = if (theme in bundledThemes) theme else DEFAULT_THEME
        current = selected
        context.getSharedPreferences("kalypte.theme", Context.MODE_PRIVATE)
            .edit().putString("kalypte.theme", selected).apply()
    }
}

object LegacyAssets {
    private fun themedPath(context: Context, path: String): String {
        LegacyThemeState.ensure(context)
        return if (path.startsWith("themes/default/")) {
            "themes/" + LegacyThemeState.current + "/" + path.removePrefix("themes/default/")
        } else path
    }

    fun bitmap(context: Context, path: String) =
        openThemed(context, path).use { BitmapFactory.decodeStream(it) }

    private fun openThemed(context: Context, path: String): java.io.InputStream {
        val resolved = "legacy/" + themedPath(context, path)
        return try {
            context.assets.open(resolved)
        } catch (_: java.io.IOException) {
            val fallback = if (path.startsWith("themes/default/")) "legacy/" + path else resolved
            context.assets.open(fallback)
        }
    }

    @Composable
    fun rememberBitmap(context: Context, path: String) = run {
        LegacyThemeState.ensure(context)
        val resolved = themedPath(context, path)
        remember(resolved) { bitmap(context, resolved).asImageBitmap() }
    }
}
