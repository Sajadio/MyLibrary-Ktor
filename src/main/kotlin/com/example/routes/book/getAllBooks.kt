package com.example.routes.book

import com.example.data.mapper.implement.BooksBodyMapper
import com.example.domain.model.BookDto
import com.example.domain.response.BooksResponse
import com.example.repository.book.BookRepository
import com.example.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllBooks(repository: BookRepository) {
    get("books") {
        try {
            when (val result = repository.getAllBooks()) {
                is Response.SuccessResponse -> {
                    val mapper = BooksBodyMapper.mapTo(result.data as List<BookDto>)
                    call.respond(
                        result.statusCode, BooksResponse(
                            status = OK,
                            message = result.message,
                            books = mapper
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