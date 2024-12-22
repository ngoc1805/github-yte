package com.example.yte.Firebase

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yte.address
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException

class ChatViewModel: ViewModel() {
    var state by mutableStateOf(ChatState())
    private set

    private val api: FcmApi = Retrofit.Builder()
        .baseUrl(address)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()

    fun onRemoteTokenChange(newToken: String){
        state = state.copy(
            remoteToken = newToken
        )
    }
    fun onSubmitRemoteToken(){
        state = state.copy(
            isEnteringToken = false
        )
    }
    fun onMessageChange(message: String){
        state = state.copy(
            messageText = message
        )
    }
    fun sendMessage(isBroadcast: Boolean){
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                to = if(isBroadcast) null else state.remoteToken,
                notification = NotificationBody(
                    title = "New message",
                    body = state.messageText
                )
            )
            try {
                if (isBroadcast){
                    api.broadcast(messageDto)
                }else{
                    api.sendMessage(messageDto)
                }
                state = state.copy(
                    messageText = ""
                )
            }catch (e: HttpException){
                e.printStackTrace()
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }
}