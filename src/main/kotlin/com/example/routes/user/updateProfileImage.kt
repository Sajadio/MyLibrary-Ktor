package com.example.routes.user

import com.example.domain.repository.UserRepository
import com.example.routes.userId
import com.example.utils.*
import com.example.domain.response.UserResponse
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.updateProfileImage(repository: UserRepository) {
    put("/update/profile/image") {
        try {
            val multipart = call.receiveMultipart()
            var fileName: String? = null
            multipart.forEachPart { partData ->
                when (partData) {
                    is PartData.FileItem -> {
                        if (partData.name == "Profile_Image")
                        fileName = partData.saveImageProfile(PROFILE_PICTURE_PATH)
                    }
                    else -> Unit
                }
            }
            val uri = "$PROFILE_PICTURES$fileName"
            isTheSameImage(uri, repository)
            when (val result = repository.updateProfileImage(uri, call.userId.toInt())) {
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
                        result.statusCode, UserResponse(
                            status = ERROR,
                            message = result.message,
                        )
                    )
                }
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError, UserResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}

private suspend fun isTheSameImage(imageURL: String?, repository: UserRepository) {
    imageURL?.let {
        if (repository.isTheSameImage(imageURL)) {
            File("$PROFILE_PICTURE_PATH$imageURL").delete()
        }
    }
}