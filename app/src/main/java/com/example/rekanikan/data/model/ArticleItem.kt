package com.example.rekanikan.data.model

import com.google.gson.annotations.SerializedName

data class ArticleItem(
    val articleId: Int,
    val authorName: String,
    val tag: String,
    val photoUrl: String,
    val title: String,
    val content: String
)