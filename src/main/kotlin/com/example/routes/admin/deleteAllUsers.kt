package com.example.routes.admin

import com.example.data.mapper.implement.AdminBodyMapper
import com.example.data.mapper.implement.UserBodyMapper
import com.example.data.mapper.implement.UsersBodyMapper
import com.example.domain.model.AdminDto
import com.example.domain.model.UserDto
import com.example.repository.admin.AdminRepository
import com.example.routes.adminId
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import com.example.domain.response.AdminResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteAllUser(repository: AdminRepository) {
    get("users/delete-all") {
        call.adminId.isNotEmpty().run {
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

        }
    }
}