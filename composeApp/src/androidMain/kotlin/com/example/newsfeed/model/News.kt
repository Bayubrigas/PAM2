package com.example.newsfeed.model

data class News(
    val id: Int,
    val title: String,
    val category: String,
    val content: String,
    val isRead: Boolean = false
)