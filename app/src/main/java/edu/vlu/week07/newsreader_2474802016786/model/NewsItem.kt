package edu.vlu.week07.newsreader_2474802016786.model

import com.google.gson.annotations.SerializedName

data class NewsItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("userId")
    val userId: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String
)
