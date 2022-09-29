package com.example.repository.book

import com.example.domain.model.BookDto
import com.example.domain.response.BookId
import com.example.utils.Response

interface BookRepository {
    suspend fun addBook(bookDto: BookDto): Response<Any>
    suspend fun getAllBooks(): Response<Any>
    suspend fun getBookByTitle(titleBook: String): Response<Any>
    suspend fun getBookById(bookId: Int): Response<Any>
    suspend fun updateBookInfo(bookDto: BookDto): Response<Any>
    suspend fun deleteBookById(bookId: Int, userId: Int): Response<Any>
    suspend fun deleteAllBooks(booksId: BookId): Response<Any>
}