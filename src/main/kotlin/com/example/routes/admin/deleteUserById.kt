package com.example.routes.admin

import com.example.data.mapper.implement.AdminBodyMapper
import com.example.data.mapper.implement.UserBodyMapper
import com.example.data.mapper.implement.UsersBodyMapper
import com.example.domain.model.AdminDto
import com.example.domain.model.UserDto
import com.example.repository.admin.AdminRepository
import com.example.routes.adminId
import com.example.utils.*
import com.example.domain.response.AdminResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteUserById(repository: AdminRepository) {
    get("user/delete") {
        val userId = call.request.queryParameters["userId"]?.toIntOrNull()
        call.adminId.isNotEmpty().run {
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
        }
    }
}