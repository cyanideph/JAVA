package com.cyanideph.java.legacy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme

@Composable
fun LegacyBitmapBar(left:String,middle:String,right:String,modifier:Modifier=Modifier){
 val c=LocalContext.current
 val density=LocalDensity.current
 val l=LegacyAssets.rememberBitmap(c,left); val m=LegacyAssets.rememberBitmap(c,middle); val r=LegacyAssets.rememberBitmap(c,right)
 Row(modifier.height(22.dp)){
  Image(l,null,Modifier.width(with(density){l.width.toDp()}).fillMaxHeight(),contentScale=ContentScale.FillBounds)
  Image(m,null,Modifier.weight(1f).fillMaxHeight(),contentScale=ContentScale.FillBounds)
  Image(r,null,Modifier.width(with(density){r.width.toDp()}).fillMaxHeight(),contentScale=ContentScale.FillBounds)
 }
}
@Composable fun LegacyTitleBar(title:String,modifier:Modifier=Modifier){
 Column(modifier){
  Box{LegacyBitmapBar("themes/uzzap/titlebar-left.png","themes/uzzap/titlebar-middle.png","themes/uzzap/titlebar-right.png")
   Text(title,color=ReptilianTheme.Text,fontSize=ReptilianTheme.FontSize,modifier=Modifier.padding(horizontal=8.dp))}
 }
}
@Composable fun LegacyFunctionBar(modifier:Modifier=Modifier)=LegacyBitmapBar("themes/uzzap/functionbar-left.png","themes/uzzap/functionbar-middle.png","themes/uzzap/functionbar-right.png",modifier)