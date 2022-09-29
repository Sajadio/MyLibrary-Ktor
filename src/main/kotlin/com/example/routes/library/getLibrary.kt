package com.example.routes.library

import com.example.data.mapper.implement.LibraryBodyMapper
import com.example.domain.model.LibraryDto
import com.example.repository.library.LibraryRepository
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import com.example.domain.response.LibraryResponse
import com.example.routes.userId
import com.example.utils.INVALID_AUTHENTICATION_TOKEN
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getLibrary(repository: LibraryRepository) {
    get("/library") {
        try {
            val libraryId = call.request.queryParameters["libraryId"]?.toIntOrNull()
            val userId = call.request.queryParameters["userId"]?.toIntOrNull()
            if (userId == call.userId.toInt()) {
                libraryId?.let {
                    when (val result = repository.getLibraryById(libraryId)) {
                        is Response.SuccessResponse -> {
                            val mapper = LibraryBodyMapper.mapTo(result.data as LibraryDto)
                            call.respond(
                                result.statusCode, LibraryResponse(
                                    status = OK,
                                    message = result.message,
                                    library = mapper
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
            } else
                 call.respond(
                    HttpStatusCode.BadRequest,
                    message = INVALID_AUTHENTICATION_TOKEN
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