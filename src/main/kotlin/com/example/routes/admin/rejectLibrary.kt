package com.example.routes.admin

import com.example.domain.response.AdminResponse
import com.example.domain.response.LibraryResponse
import com.example.repository.admin.AdminRepository
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.rejectLibrary(repository: AdminRepository) {
    put("library/reject") {
        try {
            val libraryId = call.request.queryParameters["libraryId"]?.toIntOrNull()
            libraryId?.let {
                when (val result = repository.rejectLibrary(libraryId)) {
                    is Response.SuccessResponse -> {
                        call.respond(
                            result.statusCode, LibraryResponse(
                                status = OK,
                                message = result.message
                            )
                        )
                    }

                    is Response.ErrorResponse -> {
                        call.respond(
                           result.statusCode, LibraryResponse(
                                status = ERROR,
                                message = result.message,
                            )
                        )
                    }
                }
            } ?: call.respond(
                HttpStatusCode.InternalServerError, LibraryResponse(
                    status = ERROR,
                    message = "The library id is null",
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