package com.example.halantwittercounter.twittercounter.data.remote

import com.example.halantwittercounter.data.remote.APIService
import com.example.halantwittercounter.domain.dto.PostTweetResponse
import retrofit2.Response

class RemoteDataSourceImpl(
    private val apiService: APIService
) : RemoteDataSource {

    override suspend fun postTweet(tweet: String): Response<PostTweetResponse> {
        return apiService.postTweet(tweet)
    }
}