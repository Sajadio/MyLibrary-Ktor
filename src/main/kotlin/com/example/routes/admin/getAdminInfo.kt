package com.example.routes.admin

import com.example.domain.request.Admin
import com.example.domain.repository.AdminRepository
import com.example.routes.adminId
import com.example.utils.*
import com.example.utils.Response
import com.example.domain.response.AdminResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAdminInfo(repository: AdminRepository) {
    get {
        try {
            if (call.adminId.isEmpty()) {
                call.respond(
                    HttpStatusCode.Unauthorized, AdminResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@get
            }

            when (val result = repository.getAdminById(call.adminId.toInt())) {
                is Response.SuccessResponse -> {
                    val admin = result.data as Admin
                    call.respond(
                        result.statusCode, AdminResponse(
                            status = OK,
                            message = result.message,
                            admin = admin
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