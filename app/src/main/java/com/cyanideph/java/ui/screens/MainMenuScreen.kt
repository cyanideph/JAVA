package com.cyanideph.java.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen(
    onBuddies:()->Unit,
    onMessages:()->Unit,
    onRooms:()->Unit,
    onSettings:()->Unit,
    onHelp:()->Unit
){
    val items=listOf(
        "Subscription","Buddy Matching","Add or Invite Buddies","Settings",
        "Silent Mode","Themes","Help","Battery Saving",
        "Extended Messaging","Instant Messaging","Chat Rooms","Change Status"
    )
    Column(Modifier.fillMaxSize().background(Color(0xFFF9F9F9))){
        Box(Modifier.fillMaxWidth().background(Color(0xFF8EEF04)).padding(8.dp)){
            Text("Uzzap",color=Color.Black,fontSize=14.sp)
        }
        items.forEachIndexed{ i,label->
            val action=when(i){2->onBuddies;3->onSettings;6->onHelp;8->onMessages;9->onMessages;10->onRooms;else->{ { } }}
            Row(Modifier.fillMaxWidth().clickable{action()}.padding(horizontal=10.dp,vertical=8.dp)){
                Text(label,color=Color.Black,fontSize=14.sp)
            }
        }
    }
}