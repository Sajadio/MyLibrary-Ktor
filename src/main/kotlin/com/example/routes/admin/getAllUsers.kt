package com.example.routes.admin

import com.example.domain.request.User
import com.example.domain.repository.AdminRepository
import com.example.utils.*
import com.example.utils.Response
import com.example.domain.response.BookResponse
import com.example.domain.response.UsersResponse
import com.example.routes.adminId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllUser(repository: AdminRepository) {
    get("users") {
        try {
            if (call.adminId.isEmpty()) {
                call.respond(
                    HttpStatusCode.BadRequest, BookResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@get
            }

            call.adminId.isNotEmpty().run {
                when (val result = repository.getAllUsers()) {
                    is Response.SuccessResponse -> {
                        val users = result.data as List<User>
                        call.respond(
                            result.statusCode, UsersResponse(
                                status = OK,
                                message = result.message,
                                users = users
                            )
                        )
                    }

                    is Response.ErrorResponse -> {
                        call.respond(
                            result.statusCode, UsersResponse(
                                status = ERROR,
                                message = result.message,
                            )
                        )
                    }
                }

            }

        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest, UsersResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}