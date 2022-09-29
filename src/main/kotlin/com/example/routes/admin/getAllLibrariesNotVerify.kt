package com.example.routes.admin

import com.example.data.mapper.implement.LibrariesBodyMapper
import com.example.domain.model.LibraryDto
import com.example.domain.response.LibrariesResponse
import com.example.repository.admin.AdminRepository
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
                    val mapper = LibrariesBodyMapper.mapTo(result.data as List<LibraryDto>)
                    call.respond(
                        result.statusCode, LibrariesResponse(
                            status = OK,
                            message = result.message,
                            libraries = mapper
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