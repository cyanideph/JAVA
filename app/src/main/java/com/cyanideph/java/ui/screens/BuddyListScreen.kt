package com.cyanideph.java.ui.screens
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cyanideph.java.ui.model.Buddy
@Composable fun BuddyListScreen(onBack:()->Unit){val b=listOf(Buddy("cy","cy","Available","Most Frequent"),Buddy("friend1","Friend 1","Available","Buddies"),Buddy("friend2","Friend 2","Not Available","Buddies"));Scaffold(topBar={TopAppBar(title={Text("EM - Buddy List")},navigationIcon={TextButton(onClick=onBack){Text("Back")}})}){p->LazyColumn(Modifier.padding(p)){items(b){x->ListItem(headlineContent={Text(x.displayName)},supportingContent={Text(x.status+" • "+x.group)})}}}}
