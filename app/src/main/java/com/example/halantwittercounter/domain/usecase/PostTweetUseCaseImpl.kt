package com.example.halantwittercounter.domain.usecase

import com.example.halantwittercounter.data.remote.NetworkError
import com.example.halantwittercounter.data.remote.Result
import com.example.halantwittercounter.data.remote.safeCall
import com.example.halantwittercounter.domain.dto.PostTweetResponse
import com.example.halantwittercounter.twittercounter.domain.repository.PostTweetRepository

class PostTweetUseCaseImpl(
    private val postTweetRepository: PostTweetRepository
): PostTweetUseCase {

    override suspend fun postTweet(tweet: String): Result<PostTweetResponse, NetworkError> {
        return safeCall<PostTweetResponse> {
            postTweetRepository.postTweet(tweet)
        }
    }
}