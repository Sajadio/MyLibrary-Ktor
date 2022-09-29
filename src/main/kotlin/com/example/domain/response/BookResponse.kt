package com.example.domain.response

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
data class Book(
    val bookId: Int,
    val userId: Int,
    val libraryId: Int,
    val bookAuthor: String? = null,
    val bookTitle: String? = null,
    val bookPoster: String? = null,
    val bookFile: String? = null,
    val bookBackground: Int? = null,
    val newPrice: Double? = null,
    val oldPrice: Double? = null,
    val createdAt: String = "",
)

@Serializable
data class BookId(
    val userId:Int,
    val bookId: List<Int>
)
