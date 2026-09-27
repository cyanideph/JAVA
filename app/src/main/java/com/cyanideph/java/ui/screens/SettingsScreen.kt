package com.cyanideph.java.ui.screens
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
@Composable fun SettingsScreen(onBack:()->Unit){val e=listOf("Edit My Profile","Change Status","Change Password","Change Mobile Number","Offline Settings","Chatroom Tones","Themes","Battery Saving","Silent Mode","Help");Scaffold(topBar={TopAppBar(title={Text("Settings")},navigationIcon={TextButton(onClick=onBack){Text("Back")}})}){p->LazyColumn(Modifier.padding(p)){items(e){x->ListItem(headlineContent={Text(x)});HorizontalDivider()}}}}
