package com.example.baseballapp

data class Post(
    val title: String,
    val content: String,
    val authorId: String,
    val createdAt: String,
    val updatedAt: String // 추가: updatedAt 필드가 필요함
)
data class BoardData(
    val id: Int,
    val title: String,
    val content: String,
    val authorId: String,
    val createdAt: String,
    val updatedAt: String,
    val upVote: Int
)