package com.example.myapplication.network

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */
class NetworkException(val errorResponseModel: ErrorResponseModel) :
    Throwable(errorResponseModel.message)