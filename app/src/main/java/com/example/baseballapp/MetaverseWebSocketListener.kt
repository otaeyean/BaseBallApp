package com.example.baseballapp

import android.app.Activity
import android.util.Log
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

class MetaverseWebSocketListener(private val metaverseListener: MetaverseListener) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        println("WebSocket 연결됨")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val jsonObject = JSONObject(text)
        Log.d("WebSocket", "Received message: $text")

        when (jsonObject.getString("type")) {
            "move" -> {
                val nickname = jsonObject.getString("nickname")
                val x = jsonObject.getDouble("x").toFloat()
                val y = jsonObject.getDouble("y").toFloat()

                (metaverseListener.getContext()as Activity).runOnUiThread {
                    metaverseListener.updateUserPosition(nickname, x, y)
                }
            }
            "chat" -> {
                val nickname = jsonObject.getString("nickname")
                val message = jsonObject.getString("message")

                (metaverseListener.getContext()as Activity).runOnUiThread {
                    metaverseListener.showChatMessage(nickname, message)
                }
            }
            "user-joined" -> {
                val nickname = jsonObject.getString("nickname")
                (metaverseListener.getContext()as Activity).runOnUiThread {
                    metaverseListener.showUserJoined(nickname)
                }
            }
            "user-list" -> {
                val usersArray = jsonObject.getJSONArray("users")
                val users = mutableListOf<String>()
                for (i in 0 until usersArray.length()) {
                    users.add(usersArray.getString(i))
                }
                (metaverseListener.getContext()as Activity).runOnUiThread {
                    metaverseListener.updateUserList(users.toString())
                }
            }

            "user-left" -> {
                val nickname = jsonObject.getString("nickname")
                (metaverseListener.getContext()as Activity).runOnUiThread {
                    metaverseListener.showUserLeft(nickname)
                }
            }
            "error" -> {
                val errorMessage = jsonObject.getString("message")
                (metaverseListener.getContext()as Activity).runOnUiThread {
                    metaverseListener.showError(errorMessage)
                }
            }
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
        println("WebSocket 오류: ${t.message}")
    }
}