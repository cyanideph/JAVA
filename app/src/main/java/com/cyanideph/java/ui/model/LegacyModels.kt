package com.cyanideph.java.ui.model
data class Buddy(val id:String,val displayName:String,val status:String,val group:String)
data class ChatRoom(val name:String,val category:String,val participants:Int,val joined:Boolean=false)
data class Message(val sender:String,val body:String,val time:String,val outgoing:Boolean)
