package com.example.routes.user

import com.example.data.mapper.implement.UserBodyMapper
import com.example.domain.model.UserDto
import com.example.repository.user.UserRepository
import com.example.routes.userId
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import com.example.utils.response.AdminResponse
import com.example.utils.response.UserResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserInfo(repository: UserRepository) {
    get {
        when (val result = repository.getUserById(call.userId.toInt())) {
            is Response.SuccessResponse -> {
                val userDto = result.data as UserDto
                val mapper = UserBodyMapper.mapTo(userDto)
                call.respond(
                    result.statusCode, UserResponse(
                        status = OK,
                        message = result.message,
                        user = mapper
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
    }
}
