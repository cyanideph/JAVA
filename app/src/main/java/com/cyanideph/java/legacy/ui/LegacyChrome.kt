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

private const val BarPath = "themes/default/"

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
fun LegacyFunctionBar(
    modifier: Modifier = Modifier,
    leftLabel: String = "",
    rightLabel: String = ""
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val middle = LegacyAssets.rememberBitmap(context, "themes/uzzap/functionbar-middle.png")
    Box(modifier) {
        LegacyBitmapBar("themes/uzzap/functionbar-left.png", "themes/uzzap/functionbar-middle.png", "themes/uzzap/functionbar-right.png", Modifier.fillMaxWidth())
        Row(
            Modifier.fillMaxWidth().height(with(density) { middle.height.toDp() }),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LegacyText(leftLabel, Modifier.padding(start = 6.dp))
            LegacyText(rightLabel, Modifier.padding(end = 6.dp))
        }
    }
}

@Composable
fun LegacyBackground(modifier: Modifier = Modifier, color: Color = Color.White, content: @Composable BoxScope.() -> Unit){
    val c=LocalContext.current
    val bg=LegacyAssets.rememberBitmap(c,"themes/uzzap/background-pattern.png")
    Box(modifier.background(color)){
        Image(bg,null,Modifier.fillMaxSize(),contentScale=ContentScale.Tile)
        content()
    }
}

@Composable
fun LegacyText(text:String,modifier:Modifier=Modifier)=
    Text(text,color=ReptilianTheme.Text,fontSize=ReptilianTheme.FontSize,modifier=modifier)

@Composable
fun LegacyFrame(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val tl = LegacyAssets.rememberBitmap(context, "themes/default/frame-topleft.png")
    val top = LegacyAssets.rememberBitmap(context, "themes/default/frame-top.png")
    val tr = LegacyAssets.rememberBitmap(context, "themes/default/frame-topright.png")
    val left = LegacyAssets.rememberBitmap(context, "themes/default/frame-left.png")
    val right = LegacyAssets.rememberBitmap(context, "themes/default/frame-right.png")
    val bl = LegacyAssets.rememberBitmap(context, "themes/default/frame-bottomleft.png")
    val bottom = LegacyAssets.rememberBitmap(context, "themes/default/frame-bottom.png")
    val br = LegacyAssets.rememberBitmap(context, "themes/default/frame-bottomright.png")
    BoxWithConstraints(modifier) {
        val l = with(density) { tl.width.toDp() }
        val r = with(density) { tr.width.toDp() }
        val t = with(density) { tl.height.toDp() }
        val b = with(density) { bl.height.toDp() }
        Box(Modifier.fillMaxSize().background(ReptilianTheme.panelBackground)) {
            content()
            Image(tl, null, Modifier.align(androidx.compose.ui.Alignment.TopStart).size(l, t), contentScale = ContentScale.None)
            Image(tr, null, Modifier.align(androidx.compose.ui.Alignment.TopEnd).size(r, t), contentScale = ContentScale.None)
            Image(bl, null, Modifier.align(androidx.compose.ui.Alignment.BottomStart).size(l, b), contentScale = ContentScale.None)
            Image(br, null, Modifier.align(androidx.compose.ui.Alignment.BottomEnd).size(r, b), contentScale = ContentScale.None)
            Image(top, null, Modifier.fillMaxWidth().height(t).padding(horizontal = l).align(androidx.compose.ui.Alignment.TopCenter), contentScale = ContentScale.Tile)
            Image(bottom, null, Modifier.fillMaxWidth().height(b).padding(horizontal = l).align(androidx.compose.ui.Alignment.BottomCenter), contentScale = ContentScale.Tile)
            Image(left, null, Modifier.fillMaxHeight().width(l).padding(vertical = t).align(androidx.compose.ui.Alignment.CenterStart), contentScale = ContentScale.Tile)
            Image(right, null, Modifier.fillMaxHeight().width(r).padding(vertical = t).align(androidx.compose.ui.Alignment.CenterEnd), contentScale = ContentScale.Tile)
        }
    }
}

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
    val selectedAsset = LegacyAssets.rememberBitmap(context, "themes/default/tab-selected.png")
    val normalAsset = LegacyAssets.rememberBitmap(context, "themes/default/tab-not-selected.png")
    val indicatorUnread = LegacyAssets.rememberBitmap(context, "themes/default/unread-icon.png")
    val indicatorChat = LegacyAssets.rememberBitmap(context, "themes/default/chat-icon.png")
    val indicatorUnreadSending = LegacyAssets.rememberBitmap(context, "themes/default/unread-sending-icon.png")
    val indicatorSending = LegacyAssets.rememberBitmap(context, "themes/default/sending-message-icon.png")
    BoxWithConstraints(modifier) {
    val availableWidthPx = with(density) { maxWidth.toPx() }
    val visibleCount = (availableWidthPx / with(density) { selectedAsset.width.toDp().toPx() }).toInt().coerceAtLeast(1)
    val start = when {
        tabs.isEmpty() -> 0
        selected < visibleCount -> 0
        else -> (selected - visibleCount + 1).coerceAtMost((tabs.size - visibleCount).coerceAtLeast(0))
    }
    LegacyFrame(Modifier.fillMaxWidth()) {
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
        if (checked) "themes/default/tickbox-selected.png" else "themes/default/tickbox-not-selected.png"
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
