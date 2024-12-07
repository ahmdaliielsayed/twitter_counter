package com.example.halantwittercounter.data.remote

import com.example.halantwittercounter.utils.Constants.HTTP_REQUEST_TIMEOUT
import com.example.halantwittercounter.utils.Constants.HTTP_SERVER_ERROR_RANGE
import com.example.halantwittercounter.utils.Constants.HTTP_TOO_MANY_REQUESTS
import retrofit2.Response

inline fun <reified T> responseToResult(
    response: Response<T>
): Result<T, NetworkError> {
    return when {
        response.isSuccessful -> {
            val body = response.body()
            if (body == null) {
                Result.Error(NetworkError.EMPTY_RESPONSE)
            } else {
                Result.Success(body)
            }
        }
        response.code() == HTTP_REQUEST_TIMEOUT -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        response.code() == HTTP_TOO_MANY_REQUESTS -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        response.code() in HTTP_SERVER_ERROR_RANGE -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}