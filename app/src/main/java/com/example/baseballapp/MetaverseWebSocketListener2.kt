package com.example.baseballapp

import android.app.Activity
import android.util.Log
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

class MetaverseWebSocketListener2(private val activity: Metaverse2Activity) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        println("WebSocket 연결됨")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val jsonObject = JSONObject(text)
        Log.d("WebSocket", "Received message: $text")

        when (jsonObject.getString("type")) {
            "chat" -> {
                val nickname = jsonObject.getString("nickname")
                val message = jsonObject.getString("message")

                activity.runOnUiThread {
                    activity.onChatMessageReceived(nickname, message)  // 수정된 부분
                    Log.d("Metaverse2Activity", "Chat message: $nickname: $message")
                }
            }

            "user-list" -> {
                val usersArray = jsonObject.getJSONArray("users")
                val users = mutableListOf<String>()
                for (i in 0 until usersArray.length()) {
                    users.add(usersArray.getString(i))
                }
                activity.runOnUiThread {
                    activity.updateUserList(users.toString())
                }
            }
            "user-joined" -> {
                val nickname = jsonObject.getString("nickname")
                (activity as Activity).runOnUiThread {
                    activity.showUserJoined(nickname)
                }
            }
            "user-left" -> {
                val nickname = jsonObject.getString("nickname")
                (activity as Activity).runOnUiThread {
                    activity.showUserLeft(nickname)
                }
            }
            "error" -> {
                val errorMessage = jsonObject.getString("message")
                activity.runOnUiThread {
                    activity.showError(errorMessage)
                }
            }
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
        println("WebSocket 오류: ${t.message}")
    }
}