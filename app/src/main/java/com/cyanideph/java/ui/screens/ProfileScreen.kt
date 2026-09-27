package com.cyanideph.java.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(onBack:()->Unit){
 var nickname by remember{mutableStateOf("")}; var first by remember{mutableStateOf("")}; var last by remember{mutableStateOf("")}; var email by remember{mutableStateOf("")}
 Scaffold(topBar={TopAppBar(title={Text("Edit My Profile")},navigationIcon={TextButton(onClick=onBack){Text("Back")}})}){p->
  Column(Modifier.padding(p).padding(16.dp),verticalArrangement=Arrangement.spacedBy(10.dp)){
   OutlinedTextField(nickname,{nickname=it},label={Text("Nickname")},modifier=Modifier.fillMaxWidth())
   OutlinedTextField(first,{first=it},label={Text("First Name")},modifier=Modifier.fillMaxWidth())
   OutlinedTextField(last,{last=it},label={Text("Last Name")},modifier=Modifier.fillMaxWidth())
   OutlinedTextField(email,{email=it},label={Text("Email")},modifier=Modifier.fillMaxWidth())
   Button(onClick={},modifier=Modifier.fillMaxWidth()){Text("Save")}
  }
 }
}
