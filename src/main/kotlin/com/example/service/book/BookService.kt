package com.example.service.book

import com.example.domain.request.Book
import com.example.domain.response.BookId
import org.jetbrains.exposed.sql.ResultRow

interface BookService {
    suspend fun addBook(book: Book): Boolean
    suspend fun getAllBooks(): List<Book?>
    suspend fun getBookByTitle(titleBook: String): List<ResultRow>
    suspend fun getBookById(bookId: Int): Book?
    suspend fun updateBookInfo(book: Book): Boolean
    suspend fun deleteBookById(bookId: Int, userId: Int): Boolean
    suspend fun deleteAllBooks(booksId:BookId): Boolean
}