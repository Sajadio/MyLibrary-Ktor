package com.example.routes.library

import com.example.domain.request.Library
import com.example.domain.repository.LibraryRepository
import com.example.routes.userId
import com.example.utils.ERROR
import com.example.utils.INVALID_AUTHENTICATION_TOKEN
import com.example.utils.OK
import com.example.utils.Response
import com.example.domain.response.LibraryResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateLibraryInfo(repository: LibraryRepository) {
    put("library/update") {
        try {
            val request = call.receive<Library>()
            if (request.userId != call.userId.toInt()) {
                call.respond(
                    HttpStatusCode.BadRequest, LibraryResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@put
            }
            when (val result = repository.updateLibraryInfo(request)) {
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