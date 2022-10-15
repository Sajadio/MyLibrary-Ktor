package com.example.routes.admin

import com.example.domain.request.Admin
import com.example.domain.repository.AdminRepository
import com.example.routes.adminId
import com.example.utils.*
import com.example.domain.response.AdminResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*



fun Route.updateAdminInfo(repository: AdminRepository) {
    put("/update/profile/info") {
        try {
            val adminRequest = call.receive<Admin>()
            when (val result = repository.updateAdminInfo(adminRequest,call.adminId.toInt())) {
                is Response.SuccessResponse -> {
                    call.respond(
                        result.statusCode, AdminResponse(
                            status = OK,
                            message = result.message
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
                HttpStatusCode.InternalServerError, AdminResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}
