package com.example.routes.book

import com.example.domain.request.Book
import com.example.domain.response.BookResponse
import com.example.domain.repository.BookRepository
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getBookById(repository: BookRepository) {
    get("book") {
        try {
        val bookId  = call.request.queryParameters["bookId"]?.toIntOrNull()
            bookId?.let {
                when (val result = repository.getBookById(bookId)) {
                    is Response.SuccessResponse -> {
                        val book = result.data as Book
                        call.respond(
                            result.statusCode, BookResponse(
                                status = OK,
                                message = result.message,
                                book = book
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

            } ?: call.respond(
                HttpStatusCode.BadRequest,
                BookResponse(
                    status = ERROR,
                    message = "This book is not exist",
                )
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                BookResponse(
                    status = ERROR,
                    message = e.message,
                )
            )
        }
    }
}