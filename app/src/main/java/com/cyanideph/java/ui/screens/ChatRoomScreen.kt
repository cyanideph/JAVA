package com.cyanideph.java.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable fun ChatRoomScreen(room:String,onBack:()->Unit){var t by remember{mutableStateOf("")};Scaffold(topBar={TopAppBar(title={Text("Chat - "+room)},navigationIcon={TextButton(onClick=onBack){Text("Back")}})},bottomBar={Row(Modifier.fillMaxWidth().padding(8.dp)){OutlinedTextField(t,{t=it},Modifier.weight(1f));Button(onClick={t=""},Modifier.padding(start=8.dp)){Text("Send")}}}){p->Column(Modifier.padding(p).padding(16.dp)){Text("Welcome to "+room);Text("Room actions: participants, whispers, invitations, leave room.")}}}
