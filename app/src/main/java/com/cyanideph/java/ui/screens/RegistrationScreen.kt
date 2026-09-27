package com.cyanideph.java.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegistrationScreen(onBack:()->Unit){
 var user by remember{mutableStateOf("")};var mobile by remember{mutableStateOf("")};var first by remember{mutableStateOf("")};var last by remember{mutableStateOf("")};var email by remember{mutableStateOf("")};var pass by remember{mutableStateOf("")};var confirm by remember{mutableStateOf("")}
 Scaffold(topBar={TopAppBar(title={Text("Register")},navigationIcon={TextButton(onClick=onBack){Text("Back")}})}){p->Column(Modifier.padding(p).padding(16.dp),verticalArrangement=Arrangement.spacedBy(8.dp)){listOf("User ID" to user,"Mobile" to mobile,"First Name" to first,"Last Name" to last,"Email" to email).forEach{(l,v)->OutlinedTextField(v,{nv->when(l){"User ID"->user=nv;"Mobile"->mobile=nv;"First Name"->first=nv;"Last Name"->last=nv;"Email"->email=nv}},label={Text(l)},modifier=Modifier.fillMaxWidth())};OutlinedTextField(pass,{pass=it},label={Text("Password")},modifier=Modifier.fillMaxWidth());OutlinedTextField(confirm,{confirm=it},label={Text("Retype Password")},modifier=Modifier.fillMaxWidth());Button(onClick={},enabled=pass.isNotEmpty()&&pass==confirm,modifier=Modifier.fillMaxWidth()){Text("Register")}}}
}
