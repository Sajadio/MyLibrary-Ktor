package com.example.routes.user

import com.example.domain.request.User
import com.example.domain.repository.UserRepository
import com.example.routes.userId
import com.example.utils.*
import com.example.domain.response.UserResponse
import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File


fun Route.updateUserInfo(repository: UserRepository, gson: Gson) {
    put("/update") {
        try {
            var userRequest: User? = null
            val multipart = call.receiveMultipart()
            var fileName: String? = null

            multipart.forEachPart { partData ->
                when (partData) {
                    is PartData.FormItem -> {
                        if (partData.name == "userInfo") {
                            userRequest = gson.fromJson(
                                partData.value,
                                User::class.java
                            )
                        }
                    }

                    is PartData.FileItem -> {
                        fileName = partData.save(PROFILE_PICTURE_PATH)
                    }

                    is PartData.BinaryItem -> Unit
                    else -> Unit
                }
            }

            if (userRequest?.userId != call.userId.toInt()) {
                call.respond(
                    HttpStatusCode.BadRequest, UserResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@put
            }

            val uri = "profile_pictures/$fileName"
            userRequest?.let {
                val user = User(
                    userId = call.userId.toInt(),
                    fullName = it.fullName,
                    urlPhoto = uri,
                    email = it.email,
                    phoneNumber = it.phoneNumber,
                )

                when (val result = repository.updateUserInfo(user)) {
                    is Response.SuccessResponse -> {
                        call.respond(
                            result.statusCode, UserResponse(
                                status = OK,
                                message = result.message
                            )
                        )
                    }

                    is Response.ErrorResponse -> {
                        File("$PROFILE_PICTURE_PATH/$fileName").delete()
                        call.respond(
                            HttpStatusCode.InternalServerError, UserResponse(
                                status = ERROR,
                                message = result.message,
                            )
                        )
                    }
                }
            } ?: call.respond(
                HttpStatusCode.BadRequest, UserResponse(
                    status = ERROR,
                    message = GENERIC_ERROR
                )
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest, UserResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}