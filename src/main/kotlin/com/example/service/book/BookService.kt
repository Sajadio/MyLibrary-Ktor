package com.example.service.book

import com.example.domain.model.BookDto
import com.example.domain.response.BookId
import org.jetbrains.exposed.sql.ResultRow

interface BookService {
    suspend fun addBook(bookDto: BookDto): Boolean
    suspend fun getAllBooks(): List<BookDto?>
    suspend fun getBookByTitle(titleBook: String): List<ResultRow>
    suspend fun getBookById(bookId: Int): BookDto?
    suspend fun updateBookInfo(bookDto: BookDto): Boolean
    suspend fun deleteBookById(bookId: Int, userId: Int): Boolean
    suspend fun deleteAllBooks(booksId:BookId): Boolean
}