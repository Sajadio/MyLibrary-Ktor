package com.example.routes.book

import com.example.domain.response.BookResponse
import com.example.domain.repository.BookRepository
import com.example.routes.userId
import com.example.utils.ERROR
import com.example.utils.INVALID_AUTHENTICATION_TOKEN
import com.example.utils.OK
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteBookById(repository: BookRepository) {
    get("book/delete") {
        try {
            val bookId = call.request.queryParameters["bookId"]?.toIntOrNull()
            val userId = call.request.queryParameters["userId"]?.toIntOrNull()
            if (userId != call.userId.toInt()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BookResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN,
                    )
                )
                return@get
            }
            bookId?.let {
                when (val result = repository.deleteBookById(bookId, userId)) {
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

            } ?: call.respond(
                HttpStatusCode.BadRequest,
                BookResponse(
                    status = ERROR,
                    message = "The id is null",
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