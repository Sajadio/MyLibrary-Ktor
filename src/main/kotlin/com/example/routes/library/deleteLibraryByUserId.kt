package com.example.routes.library

import com.example.domain.response.LibraryResponse
import com.example.repository.library.LibraryRepository
import com.example.routes.userId
import com.example.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteUserLibrary(repository: LibraryRepository) {
    get("library/delete") {
        try {

            val userId = call.request.queryParameters["userId"]?.toIntOrNull()
            if (userId != call.userId.toInt()) {
                call.respond(
                    HttpStatusCode.BadRequest, LibraryResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@get
            }

            when (val result = repository.deleteUserLibrary(userId)) {
                is Response.SuccessResponse -> {
                    call.respond(
                        result.statusCode, LibraryResponse(
                            status = OK,
                            message = result.message,
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
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest, LibraryResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}