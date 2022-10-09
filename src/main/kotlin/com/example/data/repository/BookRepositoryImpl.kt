package com.example.data.repository

import com.example.domain.repository.BookRepository
import com.example.domain.request.Book
import com.example.domain.response.BookId
import com.example.service.book.BookService
import com.example.utils.*
import io.ktor.http.*

class BookRepositoryImpl(
    private val bookService: BookService
) : BookRepository {

    override suspend fun addBook(book: Book) = if (bookService.addBook(book))
        checkResponseStatus(
            message = SUCCESS,
            statusCode = HttpStatusCode.OK,
        )
    else
        checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.BadRequest,
        )

    override suspend fun getAllBooks(): Response<Any> {
        val books = bookService.getAllBooks()
        return if (books.isNotEmpty())
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK,
                data = books
            )
        else checkResponseStatus(
            message = EMPTY_RESULT,
            statusCode = HttpStatusCode.BadRequest
        )
    }

    override suspend fun getBookByTitle(titleBook: String): Response<Any> {
        val result = bookService.getBookByTitle(titleBook)
       return if (result.isNotEmpty()) {
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK,
                data = result
            )
        } else
            checkResponseStatus(
                message = EMPTY_RESULT,
                statusCode = HttpStatusCode.NotFound
            )
    }

    override suspend fun getBookById(bookId: Int) =
        bookService.getBookById(bookId)?.let { book ->
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK,
                data = book
            )
        } ?: checkResponseStatus(
            message = EMPTY_RESULT,
            statusCode = HttpStatusCode.NotFound
        )

    override suspend fun updateBookInfo(book: Book) =
        if (bookService.updateBookInfo(book))
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK
            )
        else checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.BadRequest
        )

    override suspend fun deleteBookById(bookId: Int, userId: Int) = if (bookService.deleteBookById(bookId,userId))
        checkResponseStatus(
            message = SUCCESS,
            statusCode = HttpStatusCode.OK
        )
    else checkResponseStatus(
        message = ACCESS_BOOK,
        statusCode = HttpStatusCode.BadRequest
    )

    override suspend fun deleteAllBooks(booksId: BookId) = if (bookService.deleteAllBooks(booksId))
        checkResponseStatus(
            message = SUCCESS,
            statusCode = HttpStatusCode.OK
        )
    else checkResponseStatus(
        message = GENERIC_ERROR,
        statusCode = HttpStatusCode.BadRequest
    )
}