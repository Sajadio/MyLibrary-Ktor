package com.example.routes.admin

import com.example.domain.request.Library
import com.example.domain.response.LibrariesResponse
import com.example.domain.repository.AdminRepository
import com.example.routes.*
import com.example.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllLibrariesNotAccepted(repository: AdminRepository) {
    get("/libraries/new") {
        try {
            if (call.adminId.isEmpty()) {
                call.respond(
                    HttpStatusCode.BadRequest, LibrariesResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@get
            }

            when (val result = repository.getAllLibrariesNotAccepted()) {
                is Response.SuccessResponse -> {
                    val libraries = result.data as List<Library>
                    call.respond(
                        result.statusCode, LibrariesResponse(
                            status = OK,
                            message = result.message,
                            libraries = libraries
                        )
                    )
                }

                is Response.ErrorResponse -> {
                    call.respond(
                        result.statusCode, LibrariesResponse(
                            status = ERROR,
                            message = result.message,
                        )
                    )
                }
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest, LibrariesResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}