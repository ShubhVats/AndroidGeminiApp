package com.example.androidgeminiapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {
    private val _chatMsgList = MutableLiveData<List<ChatMsgModel>>()
    private val _chatMsg = ArrayList<ChatMsgModel>()
    var chatMsgModel: LiveData<List<ChatMsgModel>> = _chatMsgList;
    private val _loadState = MutableLiveData<Boolean>()
    var loadState: LiveData<Boolean> = _loadState;


    fun getChats(): LiveData<List<ChatMsgModel>> {
        return chatMsgModel
    }

    suspend fun addChat(text: String) {
        _loadState.value = true
        _chatMsg.add(ChatMsgModel(text, "left", "user"))
        _chatMsgList.value = _chatMsg
//        Log.i("TAG", "addChat: ${_chatMsgList.value}")
        val response = Gem(text)
        _chatMsg.add(ChatMsgModel(response.toString(), "right", "AI"))
        _loadState.value = false


    }
}