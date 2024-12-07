package com.example.halantwittercounter.di

import com.example.halantwittercounter.data.remote.APIService
import com.example.halantwittercounter.data.remote.RetrofitService
import com.example.halantwittercounter.twittercounter.data.remote.RemoteDataSource
import com.example.halantwittercounter.twittercounter.data.remote.RemoteDataSourceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val remoteModule = module {

    single<APIService> { RetrofitService.retrofitService }

    singleOf(::RemoteDataSourceImpl) { bind<RemoteDataSource>() }
}