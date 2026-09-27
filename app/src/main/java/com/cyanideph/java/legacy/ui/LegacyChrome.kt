package com.cyanideph.java.legacy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme

private const val BarPath = "themes/uzzap/"

@Composable
fun LegacyBitmapBar(left:String,middle:String,right:String,modifier:Modifier=Modifier){
    val c=LocalContext.current
    val d=LocalDensity.current
    val l=LegacyAssets.rememberBitmap(c,left)
    val m=LegacyAssets.rememberBitmap(c,middle)
    val rr=LegacyAssets.rememberBitmap(c,right)
    val h=maxOf(l.height,m.height,rr.height)
    Row(modifier.height(with(d){h.toDp()})){
        Image(l,null,Modifier.width(with(d){l.width.toDp()}).fillMaxHeight(),contentScale=ContentScale.FillBounds)
        Image(m,null,Modifier.weight(1f).fillMaxHeight(),contentScale=ContentScale.Tile)
        Image(rr,null,Modifier.width(with(d){rr.width.toDp()}).fillMaxHeight(),contentScale=ContentScale.FillBounds)
    }
}

@Composable
fun LegacyTitleBar(title:String,modifier:Modifier=Modifier){
    Box(modifier){
        LegacyBitmapBar("${BarPath}titlebar-left.png","${BarPath}titlebar-middle.png","${BarPath}titlebar-right.png")
        Text(title,color=ReptilianTheme.Text,fontSize=ReptilianTheme.FontSize,modifier=Modifier.padding(horizontal=8.dp))
    }
}

@Composable
fun LegacyFunctionBar(modifier:Modifier=Modifier)=
    LegacyBitmapBar("${BarPath}functionbar-left.png","${BarPath}functionbar-middle.png","${BarPath}functionbar-right.png",modifier)

@Composable
fun LegacyBackground(modifier:Modifier=Modifier,content:@Composable BoxScope.()->Unit){
    val c=LocalContext.current
    val bg=LegacyAssets.rememberBitmap(c,"themes/uzzap/background-pattern.png")
    Box(modifier.background(Color.White)){
        Image(bg,null,Modifier.fillMaxSize(),contentScale=ContentScale.Tile)
        content()
    }
}

@Composable
fun LegacyText(text:String,modifier:Modifier=Modifier)=
    Text(text,color=ReptilianTheme.Text,fontSize=ReptilianTheme.FontSize,modifier=modifier)

@Composable
fun LegacyTabStrip(
    tabs: List<String>,
    selected: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    indicators: List<Int> = emptyList()
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val selectedAsset = LegacyAssets.rememberBitmap(context, "tab-selected")
    val normalAsset = LegacyAssets.rememberBitmap(context, "tab-not-selected")
    val indicatorUnread = LegacyAssets.rememberBitmap(context, "unread-icon")
    val indicatorChat = LegacyAssets.rememberBitmap(context, "chat-icon")
    val indicatorUnreadSending = LegacyAssets.rememberBitmap(context, "unread-sending-icon")
    val indicatorSending = LegacyAssets.rememberBitmap(context, "sending-message-icon")
    BoxWithConstraints(modifier) {
    val availableWidthPx = with(density) { maxWidth.toPx() }
    val visibleCount = (availableWidthPx / with(density) { normalAsset.width.toDp().toPx() }).toInt().coerceAtLeast(1)
    val start = when {
        tabs.isEmpty() -> 0
        selected < visibleCount -> 0
        else -> (selected - visibleCount + 1).coerceAtMost((tabs.size - visibleCount).coerceAtLeast(0))
    }
    Row(Modifier.height(with(density) { maxOf(selectedAsset.height, normalAsset.height).toDp() })) {
        tabs.drop(start).take(visibleCount).forEachIndexed { localIndex, label ->
            val index = start + localIndex
            val bg = if (index == selected) selectedAsset else normalAsset
            Box(
                Modifier
                    .width(with(density) { bg.width.toDp() })
                    .fillMaxHeight()
                    .clickable { onSelected(index) }
            ) {
                Image(bg, null, Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)
                LegacyText(label, Modifier.align(androidx.compose.ui.Alignment.Center))
                val state = indicators.getOrNull(index) ?: 0
                val icon = when (state) {
                    1 -> indicatorUnread
                    2 -> indicatorSending
                    3 -> indicatorUnreadSending
                    else -> indicatorChat
                }
                if (state != 0) {
                    Image(icon, null, Modifier.align(androidx.compose.ui.Alignment.TopEnd))
                }
            }
        }
    }
}
@Composable
fun LegacyCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val asset = LegacyAssets.rememberBitmap(
        context,
        if (checked) "tickbox-selected" else "tickbox-not-selected"
    )
    val density = LocalDensity.current
    Image(
        asset,
        contentDescription = if (checked) "Selected" else "Not selected",
        modifier
            .size(
                with(density) { asset.width.toDp() },
                with(density) { asset.height.toDp() }
            )
            .clickable { onCheckedChange(!checked) },
        contentScale = ContentScale.None
    )
}
