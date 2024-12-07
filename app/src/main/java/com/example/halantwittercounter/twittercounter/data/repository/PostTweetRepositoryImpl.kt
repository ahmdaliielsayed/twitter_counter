package com.example.halantwittercounter.twittercounter.data.repository

import com.example.halantwittercounter.domain.dto.PostTweetResponse
import com.example.halantwittercounter.twittercounter.data.remote.RemoteDataSource
import com.example.halantwittercounter.twittercounter.domain.repository.PostTweetRepository
import retrofit2.Response

class PostTweetRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): PostTweetRepository {

    override suspend fun postTweet(tweet: String): Response<PostTweetResponse> {
        return remoteDataSource.postTweet(tweet)
    }
}