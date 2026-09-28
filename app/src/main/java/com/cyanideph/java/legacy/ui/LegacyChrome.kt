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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.cyanideph.java.legacy.assets.LegacyAssets
import com.cyanideph.java.legacy.theme.ReptilianTheme

private const val BarPath="themes/default/"
private val legacyTextStyle get() = TextStyle(color=ReptilianTheme.Text,fontSize=ReptilianTheme.FontSize)
private val legacyTitleStyle get() = TextStyle(color=ReptilianTheme.TitleBarText,fontSize=ReptilianTheme.FontSize)
private val legacyFunctionStyle get() = TextStyle(color=ReptilianTheme.FunctionBarText,fontSize=ReptilianTheme.FontSize)

@Composable fun LegacyBitmapBar(left:String,middle:String,right:String,modifier:Modifier=Modifier){
 val c=LocalContext.current
 val l=LegacyAssets.rememberBitmap(c,left)
 val m=LegacyAssets.rememberBitmap(c,middle)
 val r=LegacyAssets.rememberBitmap(c,right)
 val metrics=legacyVisualMetrics()
 val scale=metrics.scale
 val h=maxOf(l.height,m.height,r.height)
 Row(modifier.height((h*scale).dp)){
  Image(l,null,Modifier.width((l.width*scale).dp).fillMaxHeight(),contentScale=ContentScale.FillBounds)
  Image(m,null,Modifier.weight(1f).fillMaxHeight(),contentScale=ContentScale.FillBounds)
  Image(r,null,Modifier.width((r.width*scale).dp).fillMaxHeight(),contentScale=ContentScale.FillBounds)
 }
}

@Composable fun LegacyTitleBar(title:String,modifier:Modifier=Modifier){
 Box(modifier){
  LegacyBitmapBar(BarPath+"titlebar-left.png",BarPath+"titlebar-middle.png",BarPath+"titlebar-right.png",Modifier.fillMaxWidth())
  BasicText(title,style=legacyTitleStyle,modifier=Modifier.padding(horizontal=legacyVisualMetrics().space8).wrapContentHeight())
 }
}

@Composable fun LegacyFunctionBar(modifier:Modifier=Modifier,leftLabel:String="",rightLabel:String="",onLeftClick:(()->Unit)?=null,onRightClick:(()->Unit)?=null){
 val c=LocalContext.current
 val metrics=legacyVisualMetrics()
 Box(modifier){
  LegacyBitmapBar("themes/default/functionbar-left.png","themes/default/functionbar-middle.png","themes/default/functionbar-right.png",Modifier.fillMaxWidth())
  Row(Modifier.fillMaxWidth().height(metrics.functionBarHeight),horizontalArrangement=Arrangement.SpaceBetween){
   BasicText(leftLabel,style=legacyFunctionStyle,modifier=Modifier.padding(start=legacyVisualMetrics().space6).then(if(onLeftClick!=null)Modifier.clickable{onLeftClick()}else Modifier))
   BasicText(rightLabel,style=legacyFunctionStyle,modifier=Modifier.padding(end=legacyVisualMetrics().space6).then(if(onRightClick!=null)Modifier.clickable{onRightClick()}else Modifier))
  }
 }
}

@Composable fun LegacyBackground(modifier:Modifier=Modifier,color:Color=Color.White,content:@Composable BoxScope.()->Unit){
 val c=LocalContext.current
 val bg=LegacyAssets.rememberBitmap(c,"themes/default/background-pattern.png")
 Box(modifier.background(color)){Image(bg,null,Modifier.fillMaxSize(),contentScale=ContentScale.FillBounds);content()}
}

@Composable fun LegacyText(text:String,modifier:Modifier=Modifier)=BasicText(text,style=legacyTextStyle,modifier=modifier)

@Composable fun LegacyFrame(modifier:Modifier=Modifier,content:@Composable BoxScope.()->Unit){
 val c=LocalContext.current
 val tl=LegacyAssets.rememberBitmap(c,"themes/default/frame-topleft.png")
 val top=LegacyAssets.rememberBitmap(c,"themes/default/frame-top.png")
 val tr=LegacyAssets.rememberBitmap(c,"themes/default/frame-topright.png")
 val left=LegacyAssets.rememberBitmap(c,"themes/default/frame-left.png")
 val right=LegacyAssets.rememberBitmap(c,"themes/default/frame-right.png")
 val bl=LegacyAssets.rememberBitmap(c,"themes/default/frame-bottomleft.png")
 val bottom=LegacyAssets.rememberBitmap(c,"themes/default/frame-bottom.png")
 val br=LegacyAssets.rememberBitmap(c,"themes/default/frame-bottomright.png")
 val scale=legacyVisualMetrics().scale
 BoxWithConstraints(modifier){
  val l=(tl.width*scale).dp
  val r=(tr.width*scale).dp
  val t=(tl.height*scale).dp
  val b=(bl.height*scale).dp
  Box(Modifier.fillMaxSize().background(ReptilianTheme.panelBackground)){
   content()
   Image(tl,null,Modifier.align(androidx.compose.ui.Alignment.TopStart).size(l,t))
   Image(tr,null,Modifier.align(androidx.compose.ui.Alignment.TopEnd).size(r,t))
   Image(bl,null,Modifier.align(androidx.compose.ui.Alignment.BottomStart).size(l,b))
   Image(br,null,Modifier.align(androidx.compose.ui.Alignment.BottomEnd).size(r,b))
   Image(top,null,Modifier.fillMaxWidth().height(t).padding(start=l,end=r).align(androidx.compose.ui.Alignment.TopCenter),contentScale=ContentScale.FillBounds)
   Image(bottom,null,Modifier.fillMaxWidth().height(b).padding(start=l,end=r).align(androidx.compose.ui.Alignment.BottomCenter),contentScale=ContentScale.FillBounds)
   Image(left,null,Modifier.fillMaxHeight().width(l).padding(top=t,bottom=b).align(androidx.compose.ui.Alignment.CenterStart),contentScale=ContentScale.FillBounds)
   Image(right,null,Modifier.fillMaxHeight().width(r).padding(top=t,bottom=b).align(androidx.compose.ui.Alignment.CenterEnd),contentScale=ContentScale.FillBounds)
  }
 }
}

@Composable fun LegacyTabStrip(tabs:List<String>,selected:Int,onSelected:(Int)->Unit,modifier:Modifier=Modifier,indicators:List<Int> = emptyList()){
 val c=LocalContext.current
 val sa=LegacyAssets.rememberBitmap(c,"themes/default/tab-selected.png")
 val na=LegacyAssets.rememberBitmap(c,"themes/default/tab-not-selected.png")
 val iu=LegacyAssets.rememberBitmap(c,"themes/default/unread-icon.png")
 val ic=LegacyAssets.rememberBitmap(c,"themes/default/chat-icon.png")
 val ius=LegacyAssets.rememberBitmap(c,"themes/default/unread-sending-icon.png")
 val iss=LegacyAssets.rememberBitmap(c,"themes/default/sending-message-icon.png")
 val metrics=legacyVisualMetrics()
 BoxWithConstraints(modifier){
  val tabWidth=metrics.tabWidth
  val visible=(maxWidth/tabWidth).toInt().coerceAtLeast(1)
  val start=if(tabs.isEmpty())0 else if(selected<visible)0 else (selected-visible+1).coerceAtMost((tabs.size-visible).coerceAtLeast(0))
  LegacyFrame(Modifier.fillMaxWidth()){
   Row(Modifier.height(maxOf(metrics.tabSelectedHeight,metrics.tabUnselectedHeight))){
    tabs.drop(start).take(visible).forEachIndexed{li,label->
     val i=start+li
     val bg=if(i==selected)sa else na
     Box(Modifier.width(tabWidth).fillMaxHeight().clickable{onSelected(i)}){
      Image(bg,null,Modifier.fillMaxSize(),contentScale=ContentScale.FillBounds)
      LegacyText(label,Modifier.align(androidx.compose.ui.Alignment.Center))
      val s=indicators.getOrNull(i)?:0
      val icon=when(s){1->iu;2->iss;3->ius;else->ic}
      if(s!=0)Image(icon,null,Modifier.size((icon.width*metrics.scale).dp,(icon.height*metrics.scale).dp).align(androidx.compose.ui.Alignment.TopEnd))
     }
    }
   }
  }
 }
}

@Composable fun LegacyCheckbox(checked:Boolean,onCheckedChange:(Boolean)->Unit,modifier:Modifier=Modifier){
 val c=LocalContext.current
 val a=LegacyAssets.rememberBitmap(c,if(checked)"themes/default/tickbox-selected.png" else "themes/default/tickbox-not-selected.png")
 val metrics=legacyVisualMetrics()
 Image(a,contentDescription=if(checked)"Selected" else "Not selected",modifier=modifier.size(metrics.checkboxSize).clickable{onCheckedChange(!checked)},contentScale=ContentScale.FillBounds)
}
