package com.example.routes.user

import com.example.repository.user.UserRepository
import com.example.routes.userId
import com.example.utils.*
import com.example.utils.response.AdminResponse
import com.example.utils.response.User
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
        var userRequest: User? = null
        val multipart = call.receiveMultipart()
        var fileName: String? = null
        try {

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

            val profilePictureUrl = "${BASE_URL}profile_pictures/${fileName}"
            userRequest?.let {
                val user = User(
                    userId = call.userId.toInt(),
                    fullName = it.fullName,
                    urlPhoto = "profile_pictures/$fileName",
                    email = it.email,
                    phoneNumber = it.phoneNumber,
                )

                when (val result = repository.updateUserInfo(user, call.userId.toInt())) {
                    is Response.SuccessResponse -> {
                        call.respond(
                            result.statusCode, AdminResponse(
                                status = OK,
                                message = result.message
                            )
                        )
                    }

                    is Response.ErrorResponse -> {
                        File("$PROFILE_PICTURE_PATH/$fileName").delete()
                        call.respond(
                            HttpStatusCode.InternalServerError, AdminResponse(
                                status = ERROR,
                                message = result.message,
                            )
                        )
                    }
                }
            } ?: call.respond(
                HttpStatusCode.BadRequest, AdminResponse(
                    status = ERROR,
                    message = GENERIC_ERROR
                )
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest, AdminResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}
