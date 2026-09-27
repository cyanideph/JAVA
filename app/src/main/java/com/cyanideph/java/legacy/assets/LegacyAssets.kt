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
    var current by mutableStateOf("default")
        private set

    private var loaded = false

    fun ensure(context: Context) {
        if (!loaded) {
            current = context.getSharedPreferences("kalypte.theme", Context.MODE_PRIVATE)
                .getString("kalypte.theme", "default") ?: "default"
            loaded = true
        }
    }

    fun select(context: Context, theme: String) {
        current = theme
        context.getSharedPreferences("legacy_theme", Context.MODE_PRIVATE)
            .edit().putString("kalypte.theme", theme).apply()
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
        context.assets.open("legacy/" + themedPath(context, path)).use { BitmapFactory.decodeStream(it) }

    @Composable
    fun rememberBitmap(context: Context, path: String) = run {
        LegacyThemeState.ensure(context)
        val resolved = themedPath(context, path)
        remember(resolved) { bitmap(context, resolved).asImageBitmap() }
    }
}