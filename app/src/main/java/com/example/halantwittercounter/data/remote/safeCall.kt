package com.example.halantwittercounter.data.remote

import com.google.gson.JsonParseException
import kotlinx.coroutines.ensureActive
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(crossinline execute: suspend () -> Response<T>): Result<T, NetworkError> {
    return try {
        val response = execute()
        responseToResult(response)
    } catch (e: UnknownHostException) {
        Result.Error(NetworkError.NO_INTERNET)
    } catch (e: ConnectException) {
        Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SocketTimeoutException) {
        Result.Error(NetworkError.REQUEST_TIMEOUT)
    } catch (e: JsonParseException) {
        Result.Error(NetworkError.SERIALIZATION) // solve the problem in your JSON parser
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }
}