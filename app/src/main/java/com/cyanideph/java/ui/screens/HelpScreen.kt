package com.cyanideph.java.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
@Composable fun HelpScreen(onBack:()->Unit){val s=listOf("Intro Help","Adding New Buddies","Sending Messages","Sending Indicator","Unread Indicator","Messaging Shortcuts","Inviting Friends","Sending Contacts","User Status","Available by SMS","SMS/ESMS","Phone Calling","Hide Application","Changing Mobile Number","Log Off/Exit");Scaffold(topBar={TopAppBar(title={Text("Help")},navigationIcon={TextButton(onClick=onBack){Text("Back")}})}){p->Column(Modifier.padding(p).padding(16.dp)){s.forEach{Text("• "+it)}}}}
