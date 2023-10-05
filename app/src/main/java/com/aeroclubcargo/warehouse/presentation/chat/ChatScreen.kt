package com.aeroclubcargo.warehouse.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar

//@Preview(device = Devices.NEXUS_10,)
@Composable
fun ChatScreen (
    navController: NavController,
    ){
    Scaffold(topBar = {
        GetTopBar(
             navController = navController,
            isDashBoard = false)
    }) {
        Column (modifier = Modifier.padding(16.dp)){
            Text(text = "Messaging", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(2.dp))
            MainBody()
        }
    }
}

@Composable
fun MainBody(){
    Column(modifier = Modifier.padding(8.dp),) {
        ChatScreen2()
    }
}

fun generateChatList(): List<Chat> {
    // Replace this with your actual chat data retrieval logic
    return listOf(
        Chat("User 1", "Hello!", listOf("Hello!", "How are you?", "I'm good, thanks!")),
        Chat("User 2", "Hi there!", listOf("Hi!", "What's up?"))
        // Add more chat items as needed
    )
}
@Composable
fun ChatScreen2() {
    var selectedChat by remember { mutableStateOf<Chat?>(null) }
    val chatList = generateChatList()

    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Left side: User chat list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Gray)
        ) {
            items(chatList) { chat ->
                ChatListItem(
                    chat = chat,
                    selected = chat == selectedChat,
                    onClick = { selectedChat = chat }
                )
            }
        }

        // Right side: Selected chat screen details
        if (selectedChat != null) {
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                SelectedChatScreen(selectedChat!!)
            }

        } else {
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                Text(
                    text = "Select a chat",
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ChatListItem(
    chat: Chat,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(selected = selected, onClick = onClick)
            .padding(16.dp)
            .background(if (selected) Color.Blue else Color.Transparent)
    ) {
        // Display chat item details like name, message, etc.
        Text(
            text = chat.name,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = chat.lastMessage,
            modifier = Modifier
                .weight(2f)
        )
    }
}

@Composable
fun SelectedChatScreen(chat: Chat) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
    ) {
        // Header with chat details
        Text(
            text = chat.name,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(16.dp)
        )

        // Chat messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(chat.messages) { message ->
                ChatMessageItem(message)
            }
        }

        // Input field for typing messages
        BasicTextField(
            value = "",
            onValueChange = { /* Handle input text change */ },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(16.dp)
        )
    }
}

@Composable
fun ChatMessageItem(message: String) {
    Text(
        text = message,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

data class Chat(val name: String, val lastMessage: String, val messages: List<String>)
