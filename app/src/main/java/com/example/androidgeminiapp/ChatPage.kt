package com.example.androidgeminiapp

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun ChatPage(modifier: Modifier, viewModel: ChatViewModel) {

    var question by remember {
        mutableStateOf("")
    }

    val keyBoardController = LocalSoftwareKeyboardController.current

    var chatMsgs = viewModel.chatMsgModel.observeAsState()

    val loadState = viewModel.loadState.observeAsState()
    val co = rememberCoroutineScope()



    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        when (loadState.value) {
            true -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                ) {
                    chatMsgs.value?.let { it ->
                        items(count = it.size, itemContent = {
                            Log.i("TAG", chatMsgs.value!![it].chatText)
                            ChatMsg(chatMsgs.value!![it].chatText, chatMsgs.value!![it].side)
                        })
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .padding(0.dp, 0.dp, 0.dp, 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(value = question, onValueChange = {
                        question = it
                    }, label = { Text(text = "Enter your question...") })
                    IconButton(
                        onClick = {
                            co.launch {
                                viewModel.addChat(question)
                                question = ""
                                keyBoardController?.hide()
                            }
                        },
                        content = {
                            Icon(
                                Icons.Rounded.Send, contentDescription = "Send"
                            )
                        },
                    )
                }

            }
        }
    }
}

@Composable
fun ChatMsg(text: String, sender: String) {
    Box(
        modifier = Modifier.fillMaxWidth(), contentAlignment = when (sender) {
            "left" -> {
                Alignment.BottomEnd
            }

            else -> {
                Alignment.TopStart
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .width(200.dp)
                .padding(8.dp),
            color = when (sender) {
                "left" -> Color.Green
                else -> Color.Blue
            },
            border = BorderStroke(0.3.dp, Color.Black),
            shape = RoundedCornerShape(7.dp)

        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}

