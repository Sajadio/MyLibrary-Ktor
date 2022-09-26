package com.example.routes.admin

import com.example.data.mapper.implement.AdminBodyMapper
import com.example.data.mapper.implement.UserBodyMapper
import com.example.data.mapper.implement.UsersBodyMapper
import com.example.domain.model.AdminDto
import com.example.domain.model.UserDto
import com.example.repository.admin.AdminRepository
import com.example.routes.adminId
import com.example.utils.*
import com.example.utils.response.AdminResponse
import com.example.utils.response.UserResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.findUserByEmail(repository: AdminRepository) {
    get("user/search") {
        val email = call.request.queryParameters["email"]
        call.adminId.isNotEmpty().run {
            email?.let {
                when (val result = repository.findUserByEmail(email)) {
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
            } ?: call.respond(
                HttpStatusCode.BadRequest, AdminResponse(
                    status = ERROR,
                    message = MESSAGE_USER_ID
                )
            )
        }
    }
}