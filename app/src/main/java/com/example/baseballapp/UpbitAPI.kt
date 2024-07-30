package com.example.baseballapp

import retrofit2.Call
import retrofit2.http.GET

interface UpbitAPI {
    @GET("player")
    fun getAllPlayers(): Call<List<PlayerData>>

    @GET("schedule")
    fun getAllSchedule():Call<List<GameListData>>

    @GET("team")
    fun getAllTeams(): Call<List<TeamRankData>>

    @GET("hitter")
    fun getAllHitters(): Call<List<HitterRankData>>

    @GET("pitcher")
    fun getAllPitchers(): Call<List<PitcherRankData>>
}

