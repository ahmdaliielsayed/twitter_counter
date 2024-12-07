package com.example.halantwittercounter.domain.dto

import com.google.gson.annotations.SerializedName

data class PostTweetResponse(
    @SerializedName("data")
    val tweet: Tweet
)
