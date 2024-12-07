package com.example.halantwittercounter.domain.usecase

import com.example.halantwittercounter.data.remote.NetworkError
import com.example.halantwittercounter.domain.dto.PostTweetResponse
import com.example.halantwittercounter.data.remote.Result

interface PostTweetUseCase {

    suspend fun postTweet(tweet: String): Result<PostTweetResponse, NetworkError>
}