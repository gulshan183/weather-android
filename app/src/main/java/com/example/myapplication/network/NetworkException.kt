package com.example.myapplication.network

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */

/**
 * Network Exception for API call failures, wrapping the [ErrorResponseModel]
 *
 * @property errorResponseModel
 */
class NetworkException(val errorResponseModel: ErrorResponseModel) :
    Throwable(errorResponseModel.message)