package com.cyanideph.java.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyanideph.java.ui.model.Message
@Composable fun MessagesScreen(onBack:()->Unit){var t by remember{mutableStateOf("")};val m=remember{mutableStateListOf(Message("Friend 1","Hello from Uzzap","20:00",false),Message("You","Hi!","20:01",true))};Scaffold(topBar={TopAppBar(title={Text("Messenger")},navigationIcon={TextButton(onClick=onBack){Text("Back")}})},bottomBar={Row(Modifier.fillMaxWidth().padding(8.dp)){OutlinedTextField(t,{t=it},Modifier.weight(1f));Button(onClick={if(t.isNotBlank()){m+=Message("You",t,"now",true);t=""}},Modifier.padding(start=8.dp)){Text("Send")}}}){p->LazyColumn(Modifier.padding(p)){items(m){x->ListItem(headlineContent={Text(x.sender)},supportingContent={Text(x.body)},trailingContent={Text(x.time)})}}}}
