package com.example.utils

import io.ktor.http.*

const val EXPIRY_TIME = 365L * 1000L * 60L * 60L * 24L

const val BASE_URL = "http://192.168.100.147:8081/"
const val PROFILE_PICTURE_USER_PATH = "build/resources/main/static/profile_pictures/user/"
const val PROFILE_PICTURES_USER = "profile_pictures/user/"
const val PROFILE_PICTURE_ADMIN_PATH = "build/resources/main/static/profile_pictures/admin/"
const val PROFILE_PICTURES_ADMIN = "profile_pictures/admin/"
const val LIBRARY_PICTURES = "library_pictures/"
const val LIBRARY_PICTURE_PATH = "build/resources/main/static/library_pictures/"

const val SERVER_KEY = "SERVER KEY"
const val CONTENT_TYPE = "applications/json"
const val BASE_URL_FIREBASE = "https://fcm.googleapis.com/"

fun checkResponseStatus(
    message: String,
    statusCode: HttpStatusCode,
    data: Any? = null
): Response<Any> {
    return try {
        if (statusCode == HttpStatusCode.OK) {
            Response.SuccessResponse(
                message = message,
                statusCode = HttpStatusCode.OK,
                data = data
            )
        } else
            Response.ErrorResponse(
                message = message,
                statusCode = statusCode,
            )

    } catch (e: Exception) {
        Response.ErrorResponse(message = e.message.toString(), statusCode = HttpStatusCode.BadRequest)
    }
}