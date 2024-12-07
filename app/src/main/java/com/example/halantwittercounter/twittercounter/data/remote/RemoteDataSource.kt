package com.example.halantwittercounter.twittercounter.data.remote

import com.example.halantwittercounter.domain.dto.PostTweetResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun postTweet(tweet: String): Response<PostTweetResponse>
}