package com.cyanideph.java.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Uzzap")
        OutlinedTextField(username, { username = it }, label = { Text("User ID") })
        OutlinedTextField(password, { password = it }, label = { Text("Password") })
        Button(onClick = onLogin, enabled = username.isNotBlank() && password.isNotBlank()) {
            Text("Log In")
        }
    }
}
