package com.example.utils

import io.ktor.http.*

sealed class Response<T> {
    data class SuccessResponse<T>(
        val message: String,
        val statusCode: HttpStatusCode,
        val data: T? = null,
    ) : Response<T>()

    data class ErrorResponse(val message: String, val statusCode: HttpStatusCode) : Response<Any>()

}