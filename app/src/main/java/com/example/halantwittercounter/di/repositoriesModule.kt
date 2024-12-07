package com.example.halantwittercounter.di

import com.example.halantwittercounter.twittercounter.domain.repository.PostTweetRepository
import com.example.halantwittercounter.twittercounter.data.repository.PostTweetRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoriesModule = module {

    singleOf(::PostTweetRepositoryImpl) { bind<PostTweetRepository>() }
}