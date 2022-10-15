package com.example.routes.book

import com.example.domain.request.Book
import com.example.domain.response.BookResponse
import com.example.domain.repository.BookRepository
import com.example.domain.repository.LibraryRepository
import com.example.routes.userId
import com.example.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addBook(repository: BookRepository, libraryRepo: LibraryRepository) {
    post("book/add") {
        try {
            val request = call.receive<Book>()
            if (request.userId != call.userId.toInt()) {
                call.respond(
                    HttpStatusCode.BadRequest, BookResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@post
            }

            if (!libraryRepo.checkIfTheLibraryIsAccepted(call.userId.toInt())) {
                call.respond(
                    HttpStatusCode.BadRequest, BookResponse(
                        status = ERROR,
                        message = TERMS_ADD_BOOK
                    )
                )
                return@post
            }

            if (!libraryRepo.checkIfUserHasLibrary(call.userId.toInt())) {
                call.respond(
                    HttpStatusCode.BadRequest, BookResponse(
                        status = OK,
                        message = ACCESS_LIBRARY,
                    )
                )
                return@post
            }

            when (val result = repository.addBook(request)) {
                is Response.SuccessResponse -> {
                    call.respond(
                        result.statusCode, BookResponse(
                            status = OK,
                            message = result.message,
                        )
                    )
                }

                is Response.ErrorResponse -> {
                    call.respond(
                        result.statusCode, BookResponse(
                            status = ERROR,
                            message = result.message,
                        )
                    )
                }
            }

        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest, BookResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}