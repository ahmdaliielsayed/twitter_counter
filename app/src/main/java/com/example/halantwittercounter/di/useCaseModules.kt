package com.example.halantwittercounter.di

import com.example.halantwittercounter.domain.usecase.PostTweetUseCase
import com.example.halantwittercounter.domain.usecase.PostTweetUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModules = module {

    factoryOf(::PostTweetUseCaseImpl) { bind<PostTweetUseCase>() }
}