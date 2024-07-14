package com.example.baseballapp

import retrofit2.Call
import retrofit2.http.GET

interface UpbitAPI {
    @GET("player")
    fun getAllPlayers(): Call<List<PlayerData>>
}

