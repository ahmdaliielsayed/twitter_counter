package com.example.halantwittercounter.data.remote

import com.example.halantwittercounter.domain.dto.PostTweetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {

    @POST("tweets")
    suspend fun postTweet(
        @Body tweet: String
    ): Response<PostTweetResponse>
}