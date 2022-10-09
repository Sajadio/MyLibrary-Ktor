package com.example.routes.admin

import com.example.domain.request.User
import com.example.domain.repository.AdminRepository
import com.example.routes.adminId
import com.example.utils.*
import com.example.domain.response.UserResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserByEmail(repository: AdminRepository) {
    get("user/search") {
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

            val email = call.request.queryParameters["email"]
            email?.let {
                when (val result = repository.getUserByEmail(email)) {
                    is Response.SuccessResponse -> {
                        val user = result.data as User
                        call.respond(
                            result.statusCode, UserResponse(
                                status = OK,
                                message = result.message,
                                user = user
                            )
                        )
                    }

                    is Response.ErrorResponse -> {
                        call.respond(
                            result.statusCode, UserResponse(
                                status = ERROR,
                                message = result.message,
                            )
                        )
                    }
                }
            }
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