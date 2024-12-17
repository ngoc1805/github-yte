package com.example.yte.ChatBot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.AppBarView
import com.example.yte.R
import com.example.yte.ui.theme.Purple80

@Composable
fun ChatPage(
    navController: NavController,
    chatViewModel: ChatViewModel = viewModel()
){
    Column(modifier = Modifier) {
        AppBarView(
            title ="Trò chuyện cùng AI",
            color = R.color.white,
            backgroundColor =R.color.darkblue,
            alignment =Alignment.Center,
            isVisible = true,
            onDeleteNavClicked = {navController.popBackStack()}
        )
        MessageList(
            modifier = Modifier.weight(1f),
            messageLisr = chatViewModel.messageList
        )
        MessageInput(
            onMessageSend = {
                chatViewModel.sendMessage(it)
            }
        )
    }
}
@Composable
fun MessageInput(onMessageSend: (String)-> Unit){
    var message by remember{ mutableStateOf("") }
    Column {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = message ,
                onValueChange = {
                    message = it
                }
            )
            IconButton(onClick = {
                if(message.isNotEmpty()){
                    onMessageSend(message)
                    message = ""
                }
            }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
    
}
@Composable
fun MessageList(modifier: Modifier = Modifier,messageLisr : List<MessageModel>){
    if(messageLisr.isEmpty()){
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.baseline_question_answer_24),
                contentDescription = "Icon",
                tint = Purple80
            )
            Text(text = "Hãy hỏi tôi bất kỳ điều gì!!!", fontSize = 18.sp)

        }
    }else{
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageLisr.reversed()){
                MessageRow(messageModel = it)
            }
        }
    }
}
@Composable
fun MessageRow(messageModel: MessageModel){
    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if (isModel) Color.LightGray else Color(0xFF03A9F4))
                    .padding(16.dp)

            ) {
                Text(
                    text = messageModel.message,
                    fontWeight = FontWeight.W500,
                    color = if (isModel)Color.Black else Color.White
                )
            }

        }
    }
}