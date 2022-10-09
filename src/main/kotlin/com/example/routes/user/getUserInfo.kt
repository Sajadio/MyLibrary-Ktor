package com.example.routes.user

import com.example.domain.request.User
import com.example.domain.repository.UserRepository
import com.example.routes.userId
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import com.example.domain.response.AdminResponse
import com.example.domain.response.UserResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserInfo(repository: UserRepository) {
    get {
        try {
            when (val result = repository.getUserById(call.userId.toInt())) {
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
                        result.statusCode, AdminResponse(
                            status = ERROR,
                            message = result.message,
                        )
                    )
                }
            }
        }catch (e:Exception){
            call.respond(
                HttpStatusCode.BadRequest, UserResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}
