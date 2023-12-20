package com.example.rekanikan.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetArticlesResponse(

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: List<MessageItem>
)

data class MessageItem(

	@field:SerializedName("article_id")
	val articleId: Int,

	@field:SerializedName("author_name")
	val authorName: String,

	@field:SerializedName("tag")
	val tag: String,

	@field:SerializedName("photo_url")
	val photoUrl: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String
)
