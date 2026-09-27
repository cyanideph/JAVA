package com.cyanideph.java.legacy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
        Image(m,null,Modifier.weight(1f).fillMaxHeight(),contentScale=ContentScale.FillBounds)
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