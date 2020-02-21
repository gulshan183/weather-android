package com.example.myapplication.network

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */

/**
 * Sequential handling of Retrofit callback concept. Throws [NetworkException] in case of failure
 *
 * @param T
 * @return
 */
@Throws(NetworkException::class)
suspend fun <T : Any> Call<T>.await(): T? {
    return suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            cancel()
        }
        enqueue(RetrofitCallback<T>(continuation))
    }
}