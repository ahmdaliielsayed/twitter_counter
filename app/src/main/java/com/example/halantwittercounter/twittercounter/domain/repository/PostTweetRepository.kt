package com.example.halantwittercounter.twittercounter.domain.repository

import com.example.halantwittercounter.domain.dto.PostTweetResponse
import retrofit2.Response

interface PostTweetRepository {

    suspend fun postTweet(tweet: String): Response<PostTweetResponse>
}