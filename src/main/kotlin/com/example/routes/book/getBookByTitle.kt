package com.example.routes.book

import com.example.data.database.table.toBookDto
import com.example.domain.response.BooksResponse
import com.example.domain.repository.BookRepository
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.ResultRow

fun Route.getBookByTitle(repository: BookRepository) {
    get("book") {
        try {
            val bookTitle = call.request.queryParameters["title"]
            bookTitle?.let {
                when (val result = repository.getBookByTitle(bookTitle)) {
                    is Response.SuccessResponse -> {
                        val books = (result.data as List<ResultRow>).map {
                            it.toBookDto()!!
                        }
                        call.respond(
                            result.statusCode, BooksResponse(
                                status = OK,
                                message = result.message,
                                books = books
                            )
                        )
                    }

                    is Response.ErrorResponse -> {
                        call.respond(
                            result.statusCode, BooksResponse(
                                status = ERROR,
                                message = result.message,
                            )
                        )
                    }
                }
            } ?: call.respond(
                HttpStatusCode.BadRequest,
                message = "This book is not exist",
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest, BooksResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }

}