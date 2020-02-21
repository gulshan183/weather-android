package com.example.myapplication.network

/**
 * A generic response model for handling all types of error responses.
 *
 * @property msgRes
 * @property errorCode
 * @property message
 */
class ErrorResponseModel(
    val msgRes: Int? = null,
    val errorCode: String? = null,
    val message: String? = null
)
