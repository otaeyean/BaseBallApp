package com.example.baseballapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitAPI {
    @GET("player")
    fun getAllPlayers(): Call<List<PlayerData>>

    @GET("team")
    fun getAllTeams(): Call<List<TeamRankData>>

    @GET("hitter")
    fun getAllHitters(): Call<List<HitterRankData>>

    @GET("pitcher")
    fun getAllPitchers(): Call<List<PitcherRankData>>

    @GET("schedule")
    fun getSchedule(@Query("date") date: String): Call<List<ScheduleData>>
}

