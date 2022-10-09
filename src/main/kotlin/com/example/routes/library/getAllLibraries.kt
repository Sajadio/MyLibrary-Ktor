package com.example.routes.library

import com.example.domain.request.Library
import com.example.domain.repository.LibraryRepository
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import com.example.domain.response.LibrariesResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllLibrary(repository: LibraryRepository) {
    get("libraries") {
        try {
            when (val result = repository.getAllLibrary()) {
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