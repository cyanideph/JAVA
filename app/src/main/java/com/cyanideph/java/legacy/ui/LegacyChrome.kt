package com.cyanideph.java.legacy.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme
private const val BarPath="themes/default/"
@Composable fun LegacyBitmapBar(left:String,middle:String,right:String,modifier:Modifier=Modifier){
 val c=LocalContext.current; val d=LocalDensity.current; val l=LegacyAssets.rememberBitmap(c,left); val m=LegacyAssets.rememberBitmap(c,middle); val r=LegacyAssets.rememberBitmap(c,right); val h=maxOf(l.height,m.height,r.height)
 Row(modifier.height(with(d){h.toDp()})){Image(l,null,Modifier.width(with(d){l.width.toDp()}).fillMaxHeight(),contentScale=ContentScale.FillBounds);Image(m,null,Modifier.weight(1f).fillMaxHeight(),contentScale=ContentScale.FillBounds);Image(r,null,Modifier.width(with(d){r.width.toDp()}).fillMaxHeight(),contentScale=ContentScale.FillBounds)}
}
@Composable fun LegacyTitleBar(title:String,modifier:Modifier=Modifier){Box(modifier){LegacyBitmapBar("${BarPath}titlebar-left.png","${BarPath}titlebar-middle.png","${BarPath}titlebar-right.png",Modifier.fillMaxWidth());BasicText(title,color=ReptilianTheme.TitleBarText,fontSize=ReptilianTheme.FontSize,modifier=Modifier.padding(horizontal=8.dp))}}
@Composable fun LegacyFunctionBar(modifier:Modifier=Modifier,leftLabel:String="",rightLabel:String="",onLeftClick:(()->Unit)?=null,onRightClick:(()->Unit)?=null){
 val c=LocalContext.current; val d=LocalDensity.current; val m=LegacyAssets.rememberBitmap(c,"themes/default/functionbar-middle.png")
 Box(modifier){LegacyBitmapBar("themes/default/functionbar-left.png","themes/default/functionbar-middle.png","themes/default/functionbar-right.png",Modifier.fillMaxWidth());Row(Modifier.fillMaxWidth().height(with(d){m.height.toDp()}),horizontalArrangement=Arrangement.SpaceBetween){BasicText(leftLabel,color=ReptilianTheme.FunctionBarText,fontSize=ReptilianTheme.FontSize,modifier=Modifier.padding(start=6.dp).then(if(onLeftClick!=null)Modifier.clickable{onLeftClick()}else Modifier));BasicText(rightLabel,color=ReptilianTheme.FunctionBarText,fontSize=ReptilianTheme.FontSize,modifier=Modifier.padding(end=6.dp).then(if(onRightClick!=null)Modifier.clickable{onRightClick()}else Modifier))}}
}
@Composable fun LegacyBackground(modifier:Modifier=Modifier,color:Color=Color.White,content:@Composable BoxScope.()->Unit){val c=LocalContext.current;val bg=LegacyAssets.rememberBitmap(c,"themes/default/background-pattern.png");Box(modifier.background(color)){Image(bg,null,Modifier.fillMaxSize(),contentScale=ContentScale.FillBounds);content()}}
@Composable fun LegacyText(text:String,modifier:Modifier=Modifier)=BasicText(text,color=ReptilianTheme.Text,fontSize=ReptilianTheme.FontSize,modifier=modifier)
@Composable fun LegacyFrame(modifier:Modifier=Modifier,content:@Composable BoxScope.()->Unit){
 val c=LocalContext.current;val d=LocalDensity.current;val tl=LegacyAssets.rememberBitmap(c,"themes/default/frame-topleft.png");val top=LegacyAssets.rememberBitmap(c,"themes/default/frame-top.png");val tr=LegacyAssets.rememberBitmap(c,"themes/default/frame-topright.png");val left=LegacyAssets.rememberBitmap(c,"themes/default/frame-left.png");val right=LegacyAssets.rememberBitmap(c,"themes/default/frame-right.png");val bl=LegacyAssets.rememberBitmap(c,"themes/default/frame-bottomleft.png");val bottom=LegacyAssets.rememberBitmap(c,"themes/default/frame-bottom.png");val br=LegacyAssets.rememberBitmap(c,"themes/default/frame-bottomright.png")
 BoxWithConstraints(modifier){val l=with(d){tl.width.toDp()};val r=with(d){tr.width.toDp()};val t=with(d){tl.height.toDp()};val b=with(d){bl.height.toDp()};Box(Modifier.fillMaxSize().background(ReptilianTheme.panelBackground)){content();Image(tl,null,Modifier.align(androidx.compose.ui.Alignment.TopStart).size(l,t));Image(tr,null,Modifier.align(androidx.compose.ui.Alignment.TopEnd).size(r,t));Image(bl,null,Modifier.align(androidx.compose.ui.Alignment.BottomStart).size(l,b));Image(br,null,Modifier.align(androidx.compose.ui.Alignment.BottomEnd).size(r,b));Image(top,null,Modifier.fillMaxWidth().height(t).padding(start=l,end=r).align(androidx.compose.ui.Alignment.TopCenter),contentScale=ContentScale.FillBounds);Image(bottom,null,Modifier.fillMaxWidth().height(b).padding(start=l,end=r).align(androidx.compose.ui.Alignment.BottomCenter),contentScale=ContentScale.FillBounds);Image(left,null,Modifier.fillMaxHeight().width(l).padding(top=t,bottom=b).align(androidx.compose.ui.Alignment.CenterStart),contentScale=ContentScale.FillBounds);Image(right,null,Modifier.fillMaxHeight().width(r).padding(top=t,bottom=b).align(androidx.compose.ui.Alignment.CenterEnd),contentScale=ContentScale.FillBounds)}}
}
@Composable fun LegacyTabStrip(tabs:List<String>,selected:Int,onSelected:(Int)->Unit,modifier:Modifier=Modifier,indicators:List<Int> = emptyList()){
 val c=LocalContext.current;val d=LocalDensity.current;val sa=LegacyAssets.rememberBitmap(c,"themes/default/tab-selected.png");val na=LegacyAssets.rememberBitmap(c,"themes/default/tab-not-selected.png");val iu=LegacyAssets.rememberBitmap(c,"themes/default/unread-icon.png");val ic=LegacyAssets.rememberBitmap(c,"themes/default/chat-icon.png");val ius=LegacyAssets.rememberBitmap(c,"themes/default/unread-sending-icon.png");val iss=LegacyAssets.rememberBitmap(c,"themes/default/sending-message-icon.png")
 BoxWithConstraints(modifier){val visible=(with(d){maxWidth.toPx()}/sa.width).toInt().coerceAtLeast(1);val start=if(tabs.isEmpty())0 else if(selected<visible)0 else (selected-visible+1).coerceAtMost((tabs.size-visible).coerceAtLeast(0));LegacyFrame(Modifier.fillMaxWidth()){Row(Modifier.height(with(d){maxOf(sa.height,na.height).toDp()})){tabs.drop(start).take(visible).forEachIndexed{li,label->val i=start+li;val bg=if(i==selected)sa else na;Box(Modifier.width(with(d){bg.width.toDp()}).fillMaxHeight().clickable{onSelected(i)}){Image(bg,null,Modifier.fillMaxSize(),contentScale=ContentScale.FillBounds);LegacyText(label,Modifier.align(androidx.compose.ui.Alignment.Center));val s=indicators.getOrNull(i)?:0;val icon=when(s){1->iu;2->iss;3->ius;else->ic};if(s!=0)Image(icon,null,Modifier.align(androidx.compose.ui.Alignment.TopEnd))}}}}}}
}
@Composable fun LegacyCheckbox(checked:Boolean,onCheckedChange:(Boolean)->Unit,modifier:Modifier=Modifier){val c=LocalContext.current;val a=LegacyAssets.rememberBitmap(c,if(checked)"themes/default/tickbox-selected.png" else "themes/default/tickbox-not-selected.png");val d=LocalDensity.current;Image(a,contentDescription=if(checked)"Selected" else "Not selected",modifier.size(with(d){a.width.toDp()},with(d){a.height.toDp()}).clickable{onCheckedChange(!checked)},contentScale=ContentScale.None)}
