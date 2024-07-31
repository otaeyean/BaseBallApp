package com.example.baseballapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val title: String,
    val content: String,
    val authorId: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable

@Parcelize
data class BoardData(
    val id: Int,
    val title: String,
    val content: String,
    val authorId: String,
    val createdAt: String,
    val updatedAt: String,
    val upVote: Int
) : Parcelable
