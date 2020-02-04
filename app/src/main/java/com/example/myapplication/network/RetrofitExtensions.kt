package com.example.myapplication.network

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */

suspend fun <T : Any> Call<T>.await(): T? {
    return suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            cancel()
        }
        enqueue(RetrofitCallback<T>(continuation))
    }
}