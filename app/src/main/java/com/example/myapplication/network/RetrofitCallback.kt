package com.example.myapplication.network

import com.example.myapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */
class RetrofitCallback<T>(val continuation: Continuation<T>): Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is IOException) {
            if (t is SocketTimeoutException) {
                onFailure(message = "Unable to connect to the server.", msgResId = R.string.unreachable_server)
            } else {
                onFailure(message = "Unable to connect to our servers, check your Internet Connection.",
                    msgResId = R.string.unreachable_server_internet_error, errorCode = NO_INTERNET
                )
            }
        }
     else
    {
        onFailure(message = "Network Error Occurred", msgResId = R.string.network_error)
    }
}




    override fun onResponse(call: Call<T>, response: Response<T>) {
        when (response.code()) {

            200 -> response.body()?.let {
                continuation.resume(it)
            } ?: onFailure(message="Something went wrong",msgResId = R.string.something_went_wrong)

            401 -> onFailure(message="Invalid API key",msgResId = R.string.something_went_wrong)
            404 -> onFailure(message = "Resource not found", errorCode = response.code().toString())
            else -> onFailure(message="Something went wrong",msgResId = R.string.something_went_wrong)
        }
    }

    private fun onFailure(msgResId:Int?=null,message:String?=null,errorCode:String?=null){
        continuation.resumeWithException(NetworkException(
            ErrorResponseModel(msgRes=msgResId,
            message=message,
                errorCode=errorCode)))
    }
}