package com.example.routes.book

import com.example.domain.response.BookId
import com.example.domain.response.BookResponse
import com.example.repository.book.BookRepository
import com.example.routes.userId
import com.example.utils.ERROR
import com.example.utils.INVALID_AUTHENTICATION_TOKEN
import com.example.utils.OK
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteAllBooks(repository: BookRepository) {
    get("book/deleteAll") {
        try {
        val booksId = call.receive<BookId>()
            if (booksId.userId != call.userId.toInt()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BookResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN,
                    )
                )
                return@get
            }
            when (val result = repository.deleteAllBooks(booksId)) {
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
                HttpStatusCode.BadRequest,
                BookResponse(
                    status = ERROR,
                    message = e.message,
                )
            )
        }
    }
}