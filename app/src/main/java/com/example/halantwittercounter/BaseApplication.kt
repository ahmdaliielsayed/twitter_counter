package com.example.halantwittercounter

import android.app.Application
import com.example.halantwittercounter.di.remoteModule
import com.example.halantwittercounter.di.repositoriesModule
import com.example.halantwittercounter.di.useCaseModules
import com.example.halantwittercounter.di.viewModelsModule
import com.example.halantwittercounter.utils.Constants.ACCESS_TOKEN
import com.example.halantwittercounter.utils.Constants.ACCESS_TOKEN_SECRET
import com.example.halantwittercounter.utils.Constants.API_KEY
import com.example.halantwittercounter.utils.Constants.API_SECRET
import com.example.halantwittercounter.utils.Constants.BASE_URL
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    companion object {
        // Used to load the 'halantwittercounter' library on application startup.
        init {
            System.loadLibrary("halantwittercounter")
        }
    }

    override fun onCreate() {
        super.onCreate()

        BASE_URL = getBaseUrl()
        API_KEY = getApiKey()
        API_SECRET = getApiSecret()
        ACCESS_TOKEN = getAccessToken()
        ACCESS_TOKEN_SECRET = getAccessTokenSecret()

        startKoin {
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    remoteModule,
                    repositoriesModule,
                    useCaseModules,
                    viewModelsModule,
                )
            )
        }
    }

    /**
     * A native method that is implemented by the 'halantwittercounter' native library,
     * which is packaged with this application.
     */
    private external fun getBaseUrl(): String
    private external fun getApiKey(): String
    private external fun getApiSecret(): String
    private external fun getAccessToken(): String
    private external fun getAccessTokenSecret(): String
}