package com.example.baseballapp

import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

class MetaverseWebSocketListener(private val activity: Metaverse1Activity) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        println("WebSocket 연결됨")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val jsonObject = JSONObject(text)
        when (jsonObject.getString("type")) {
            "move" -> {
                val nickname = jsonObject.getString("nickname")
                val x = jsonObject.getDouble("x").toFloat()
                val y = jsonObject.getDouble("y").toFloat()

                activity.runOnUiThread {
                    activity.updateUserPosition(nickname, x, y)
                }
            }
            "chat" -> {
                val nickname = jsonObject.getString("nickname")
                val message = jsonObject.getString("message")

                activity.runOnUiThread {
                    activity.showChatMessage(nickname, message)
                }
            }
            "user-joined" -> {
                val nickname = jsonObject.getString("nickname")
                activity.runOnUiThread {
                    activity.showUserJoined(nickname)
                }
            }
            "user-list"->{
                val usersArray=jsonObject.getJSONArray("users")
                val users= mutableListOf<String>()
                for(i in 0 until usersArray.length()){
                    users.add(usersArray.getString(i))
                }
                activity.runOnUiThread {
                    activity.updateUserList(users.toString())
                }
            }
            "user-left" -> {
                val nickname = jsonObject.getString("nickname")
                activity.runOnUiThread {
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