package com.newsnap.data.model

data class NewsItem(
    val title: String,
    val description: String,
    val link: String,
    val imageUrl: String?,
    val pubDate: String,
    val source: String
)
