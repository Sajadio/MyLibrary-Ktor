package com.example.routes.library

import com.example.domain.request.Library
import com.example.domain.repository.LibraryRepository
import com.example.utils.*
import com.example.domain.response.LibraryResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getLibraryById(repository: LibraryRepository) {
    get("library") {
        try {
            val libraryId = call.request.queryParameters["libraryId"]?.toIntOrNull()
            libraryId?.let {
                when (val result = repository.getLibraryById(libraryId)) {
                    is Response.SuccessResponse -> {
                        val library = result.data as Library
                        call.respond(
                            result.statusCode, LibraryResponse(
                                status = OK,
                                message = result.message,
                                library = library
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
                HttpStatusCode.BadRequest,
                message = "The libraryId is null"
            )

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