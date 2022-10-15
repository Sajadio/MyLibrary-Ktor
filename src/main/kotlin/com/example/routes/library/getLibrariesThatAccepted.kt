package com.example.routes.library

import com.example.domain.repository.LibraryRepository
import com.example.domain.response.LibrariesResponse
import com.example.routes.userId
import com.example.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getLibrariesThatAccepted(repository: LibraryRepository) {
    get("library/isAccepted"){
        try {
            val result = repository.checkIfTheLibraryIsAccepted(call.userId.toInt())
            if (result){
                call.respond(
                   HttpStatusCode.OK, LibrariesResponse(
                        status = OK,
                        message = ACCEPT_LIBRARY,
                    )
                )
            }else{
                call.respond(
                    HttpStatusCode.OK, LibrariesResponse(
                        status = ERROR,
                        message = REJECT_LIBRARY,
                    )
                )
            }

        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError, LibrariesResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}