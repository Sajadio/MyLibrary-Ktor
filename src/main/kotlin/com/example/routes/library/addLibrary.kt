package com.example.routes.library

import com.example.data.mapper.implement.LibraryBodyMapper
import com.example.domain.model.LibraryDto
import com.example.repository.library.LibraryRepository
import com.example.routes.userId
import com.example.utils.*
import com.example.utils.response.AdminResponse
import com.example.utils.response.Library
import com.example.utils.response.LibraryResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addLibrary(repository: LibraryRepository) {
    post("/library/add") {
        try {
            val request = call.receive<LibraryDto>()
            if(request.userId == call.userId.toInt()) {
                when (val result = repository.addLibrary(request)) {
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
                            result.statusCode, AdminResponse(
                                status = ERROR,
                                message = result.message,
                            )
                        )
                    }
                }
            }else
                call.respond(
                    HttpStatusCode.BadRequest, LibraryResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
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