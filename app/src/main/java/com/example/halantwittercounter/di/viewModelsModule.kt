package com.example.halantwittercounter.di

import com.example.halantwittercounter.twittercounter.presentation.viewmodel.TwitterCounterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {

    viewModelOf(::TwitterCounterViewModel)
}