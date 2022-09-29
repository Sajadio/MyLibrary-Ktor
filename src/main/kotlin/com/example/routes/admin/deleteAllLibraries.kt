package com.example.routes.admin

import com.example.repository.admin.AdminRepository
import com.example.routes.adminId
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import com.example.domain.response.AdminResponse
import com.example.utils.INVALID_AUTHENTICATION_TOKEN
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteAllLibraries(repository: AdminRepository) {
    get("libraries/deleteAll") {
        try {
            if (call.adminId.isEmpty()) {
                call.respond(
                    HttpStatusCode.BadRequest, AdminResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@get
            }
            when (val result = repository.deleteAllLibraries()) {
                is Response.SuccessResponse -> {
                    call.respond(
                        result.statusCode, AdminResponse(
                            status = OK,
                            message = result.message,
                        )
                    )
                }

                is Response.ErrorResponse -> {
                    call.respond(
                        result.statusCode, AdminResponse(
                            status = ERROR,
                            message = result.message,
                        )
                    )
                }
            }
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