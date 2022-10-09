package com.example.routes.admin

import com.example.domain.repository.AdminRepository
import com.example.routes.*
import com.example.utils.*
import com.example.domain.response.AdminResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteAllUser(repository: AdminRepository) {
    get("users/deleteAll") {
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
            when (val result = repository.deleteAllUsers()) {
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