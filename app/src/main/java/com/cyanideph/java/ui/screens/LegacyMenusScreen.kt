package com.cyanideph.java.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LegacyMenusScreen(onBack:()->Unit,onSelect:(String)->Unit){
 val entries=listOf(
  "main-menu-options" to listOf("Lock Keypad","Log Off","Intro Help","About Uzzap","Exit Application"),
  "buddy-list-options" to listOf("Contact","Send Group Message","Manage Groups","New Group","Add/Invite Buddies","Clear Message History","Status Message","Available","Not Available","Invisible","Settings"),
  "message-options" to listOf("Set Recipient","Send Message","Edit Message","Add Recipient","Add Cc Recipient","Show/Hide CC Recipients","Show/Hide Recipients","Add Emoticon","Send Whisper"),
  "messenger-options" to listOf("Send New Message","Reply All","View History","Received Contacts","Profile","Accept Buddy Invite","Reject Buddy Invite","Send Message","Close Tab"),
  "chat-room-actions" to listOf("Send Message","Send Whisper","List Participants","Invite Participants","Leave Chatroom"),
  "billing-options" to listOf("Current Billing Status","Purchase a Package","Auto-Renew","View Purchase History"),
  "offline-options" to listOf("Available SMS","Email","Store on server")
 )
 Scaffold(topBar={TopAppBar(title={Text("Legacy Menus")},navigationIcon={TextButton(onClick=onBack){Text("Back")}})}){p->
  LazyColumn(Modifier.padding(p)){items(entries){(name,items)->ListItem(headlineContent={Text(name)},supportingContent={Text(items.joinToString(" • "))},trailingContent={TextButton(onClick={onSelect(name)}){Text("Open")}});HorizontalDivider()}}
 }
}
