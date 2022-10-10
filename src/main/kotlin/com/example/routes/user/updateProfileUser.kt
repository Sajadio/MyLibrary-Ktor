package com.example.routes.user

import com.example.domain.request.User
import com.example.domain.repository.UserRepository
import com.example.utils.*
import com.example.domain.response.UserResponse
import com.example.routes.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateProfileUser(repository: UserRepository) {
    put("/update/profile/info") {
        try {
            val userRequest = call.receive<User>()
            when (val result = repository.updateProfileUser(userRequest,call.userId.toInt())) {
                is Response.SuccessResponse -> {
                    call.respond(
                        result.statusCode, UserResponse(
                            status = OK,
                            message = result.message
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