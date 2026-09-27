package com.cyanideph.java.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatusScreen(onBack:()->Unit){
 var selected by remember{mutableStateOf("Available")}; var message by remember{mutableStateOf("")}
 val states=listOf("Available","Not Available","Invisible")
 Scaffold(topBar={TopAppBar(title={Text("Change Status")},navigationIcon={TextButton(onClick=onBack){Text("Back")}})}){p->
  Column(Modifier.padding(p).padding(16.dp),verticalArrangement=Arrangement.spacedBy(8.dp)){
   states.forEach{s->Row{RadioButton(selected==s,{selected=s});Text(s,Modifier.padding(top=12.dp))}}
   OutlinedTextField(message,{message=it},label={Text("Status Message")},modifier=Modifier.fillMaxWidth())
   Button(onClick={},modifier=Modifier.fillMaxWidth()){Text("Apply")}
  }
 }
}
