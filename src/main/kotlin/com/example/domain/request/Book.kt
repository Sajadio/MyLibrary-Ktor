package com.example.domain.request

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val bookId: Int = 0,
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
