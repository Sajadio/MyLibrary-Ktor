package com.example.routes.book

import com.example.domain.request.Book
import com.example.domain.response.BookResponse
import com.example.domain.repository.BookRepository
import com.example.routes.*
import com.example.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateBookInfo(repository: BookRepository) {
    put("book/update") {
        try {
            val request = call.receive<Book>()
            if (request.userId != call.userId.toInt()) {
                call.respond(
                    HttpStatusCode.BadRequest, BookResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@put
            }
            when (val result = repository.updateBookInfo(request)) {
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