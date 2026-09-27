package com.cyanideph.java.ui.model
data class Buddy(
    val id: String,
    val displayName: String,
    val status: String,
    val group: String,
    val yahooId: String? = null,
    val msnId: String? = null,
    val email: String? = null,
    val mobile: String? = null,
    val type: String = "amazilia",
    val authorized: Boolean = true,
    val hasHistory: Boolean = false,
    val hasAmazCid: Boolean = true,
    val isOtherContact: Boolean = false
)
data class ChatRoom(val name:String,val category:String,val participants:Int,val joined:Boolean=false)
data class Message(val sender:String,val body:String,val time:String,val outgoing:Boolean)
