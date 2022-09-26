package com.example.routes.library

import com.example.data.mapper.implement.LibrariesBodyMapper
import com.example.domain.model.LibraryDto
import com.example.repository.library.LibraryRepository
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import com.example.utils.response.LibrariesResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllLibrary(repository: LibraryRepository) {
    get("libraries") {
        try {
            when (val result = repository.getAllLibrary()) {
                is Response.SuccessResponse -> {
                    val mapper = LibrariesBodyMapper.mapTo(result.data as List<LibraryDto>)
                    mapper.forEach {
                        println("libraryId ${it.libraryId }")
                    }

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