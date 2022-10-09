package com.example.routes.admin

import com.example.domain.repository.AdminRepository
import com.example.routes.*
import com.example.utils.*
import com.example.domain.response.AdminResponse
import com.example.domain.response.UserResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteUserById(repository: AdminRepository) {
    get("user/delete") {
        try {
            if (call.adminId.isEmpty()) {
                call.respond(
                    HttpStatusCode.BadRequest, UserResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@get
            }

            val userId = call.request.queryParameters["userId"]?.toIntOrNull()
            userId?.let {
                when (val result = repository.deleteUserById(userId)) {
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
            } ?: call.respond(
                HttpStatusCode.BadRequest, AdminResponse(
                    status = ERROR,
                    message = MESSAGE_USER_ID
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
