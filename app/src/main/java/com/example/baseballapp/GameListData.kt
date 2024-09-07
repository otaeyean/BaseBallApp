package com.example.baseballapp

data class GameListData(
    var date:String,
    val time:String,
    val team1:String,
    val team2:String,
    val team1Score:String,
    val team2Score: String,
    val place:String,
    var note:String,
    val roomId: String
)

data class MetaverseMatch(
    val team1: String,
    val team2: String,
    val team1Score: String,
    val team2Score: String,
    val date:String
)
