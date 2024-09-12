package com.example.baseballapp

import android.content.Context

fun saveToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("auth_token", token)
        apply()
    }
}

fun saveUsername(context: Context, username: String) {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("username", username)
        apply()
    }
}
