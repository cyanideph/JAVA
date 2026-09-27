package com.cyanideph.java.ui.screens
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cyanideph.java.ui.model.ChatRoom
@Composable fun ChatRoomsScreen(onBack:()->Unit,onRoom:(String)->Unit){val r=listOf(ChatRoom("General","Public",12),ChatRoom("Friends","Social",6),ChatRoom("Pinoy Chat","Public",31));Scaffold(topBar={TopAppBar(title={Text("Chat Rooms")},navigationIcon={TextButton(onClick=onBack){Text("Back")}})}){p->LazyColumn(Modifier.padding(p)){items(r){x->ListItem(headlineContent={Text(x.name)},supportingContent={Text(x.category+" • "+x.participants+" participants")},trailingContent={Button(onClick={onRoom(x.name)}){Text("Join")}})}}}}
