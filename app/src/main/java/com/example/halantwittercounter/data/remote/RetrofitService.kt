package com.example.halantwittercounter.data.remote

import com.example.halantwittercounter.utils.Constants.ACCESS_TOKEN
import com.example.halantwittercounter.utils.Constants.ACCESS_TOKEN_SECRET
import com.example.halantwittercounter.utils.Constants.API_KEY
import com.example.halantwittercounter.utils.Constants.API_SECRET
import com.example.halantwittercounter.utils.Constants.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private val gson = GsonBuilder().serializeNulls().create()

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .addInterceptor(OAuthInterceptor(
            apiKey = API_KEY,
            apiSecret = API_SECRET,
            accessToken = ACCESS_TOKEN,
            accessTokenSecret = ACCESS_TOKEN_SECRET
        ))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    val retrofitService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }
}