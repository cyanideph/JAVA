package com.cyanideph.java.ui.screens
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HistoryScreen(onBack:()->Unit){
 val entries=listOf("History - Friend 1","View SMS History","View EM/SMS History","View Email","Purchase History")
 Scaffold(topBar={TopAppBar(title={Text("History")},navigationIcon={TextButton(onClick=onBack){Text("Back")}})}){p->LazyColumn(Modifier.padding(p)){items(entries){e->ListItem(headlineContent={Text(e)});HorizontalDivider()}}}
}
