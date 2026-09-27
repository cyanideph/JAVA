package com.cyanideph.java.legacy.assets

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap

object LegacyAssets {
    fun bitmap(context: Context, path: String) =
        context.assets.open("legacy/$path").use { BitmapFactory.decodeStream(it) }

    @Composable
    fun rememberBitmap(context: Context, path: String) =
        remember(path) { bitmap(context, path).asImageBitmap() }
}