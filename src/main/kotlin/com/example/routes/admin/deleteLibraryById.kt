package com.example.routes.admin

import com.example.domain.response.AdminResponse
import com.example.domain.repository.AdminRepository
import com.example.routes.adminId
import com.example.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteLibraryById(repository: AdminRepository) {
    get("library/delete") {

        try {
            if (call.adminId.isEmpty()) {
                call.respond(
                    HttpStatusCode.BadRequest, AdminResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@get
            }
            val libraryId = call.request.queryParameters["libraryId"]?.toIntOrNull()

            libraryId?.let {
                when (val result = repository.deleteLibraryById(libraryId)) {
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
                    message = MESSAGE_LIBRARY_NAME
                )
            )

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
