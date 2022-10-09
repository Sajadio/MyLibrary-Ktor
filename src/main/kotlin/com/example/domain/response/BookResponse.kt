package com.example.domain.response

import com.example.domain.request.Book
import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    val status: String,
    val message: String? = null,
    val book: Book? = null
)

@Serializable
data class BooksResponse(
    val status: String,
    val message: String? = null,
    val books: List<Book>? = null
)

@Serializable
data class BookId(
    val userId:Int,
    val bookId: List<Int>
)
