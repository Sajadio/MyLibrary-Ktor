package com.example.routes.library

import com.example.domain.request.Library
import com.example.domain.repository.LibraryRepository
import com.example.routes.userId
import com.example.utils.*
import com.example.domain.response.LibraryResponse
import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addLibrary(repository: LibraryRepository, gson: Gson) {
    post("library/add") {
        try {
            val multipart = call.receiveMultipart()
            var libraryRequest: Library? = null
            var fileName: String? = null
            multipart.forEachPart { partData ->
                when (partData) {
                    is PartData.FormItem -> {
                        if (partData.name == "create_library") {
                            libraryRequest = gson.fromJson(
                                partData.value,
                                Library::class.java
                            )
                        }
                    }

                    is PartData.FileItem -> {
                        fileName = partData.save(LIBRARY_PICTURE_PATH)
                    }

                    is PartData.BinaryItem -> Unit
                    else -> Unit
                }
            }

            val uri = "$LIBRARY_PICTURES$fileName"
            libraryRequest?.let { request ->
                val library = Library(
                    userId = call.userId.toInt(),
                    libraryName = request.libraryName,
                    libraryAddress = request.libraryAddress,
                    libraryImage = uri,
                    libraryPhone = request.libraryPhone,
                )

                if (repository.checkIfUserHasLibrary(call.userId.toInt())) {
                    call.respond(
                        HttpStatusCode.NotAcceptable, LibraryResponse(
                            status = ERROR,
                            message = MESSAGE_LIBRARY_ADDITION,
                        )
                    )
                    return@post
                }

                when (val result = repository.addLibrary(library)) {
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
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError, LibraryResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}