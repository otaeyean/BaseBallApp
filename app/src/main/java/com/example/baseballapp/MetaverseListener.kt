package com.example.baseballapp

import android.content.Context

interface MetaverseListener {
    fun getContext(): Context
    fun updateUserPosition(nickname: String, x: Float, y: Float)
    fun showChatMessage(nickname: String, message: String)
    fun showUserJoined(nickname: String)
    fun updateUserList(users: String)
    fun showUserLeft(nickname: String)
    fun showError(errorMessage: String)
}