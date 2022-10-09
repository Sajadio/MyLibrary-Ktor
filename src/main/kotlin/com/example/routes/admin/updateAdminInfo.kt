package com.example.routes.admin

import com.example.domain.request.Admin
import com.example.domain.repository.AdminRepository
import com.example.routes.adminId
import com.example.utils.*
import com.example.domain.response.AdminResponse
import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File



fun Route.updateAdminInfo(repository: AdminRepository, gson: Gson) {
    put("/update") {
        try {
            var userRequest: Admin? = null
            val multipart = call.receiveMultipart()
            var fileName: String? = null
            multipart.forEachPart { partData ->
                when (partData) {
                    is PartData.FormItem -> {
                        if (partData.name == "adminInfo") {
                            userRequest = gson.fromJson(
                                partData.value,
                                Admin::class.java
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

            userRequest?.let {
                val admin = Admin(
                    adminId = call.adminId.toInt(),
                    fullName = it.fullName,
                    urlPhoto = "profile_pictures/$fileName",
                    email = it.email,
                    phoneNumber = it.phoneNumber,
                )

                when (val result = repository.updateAdminInfo(admin)) {
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
