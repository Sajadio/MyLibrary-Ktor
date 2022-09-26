package com.example.domain.model

data class LibraryDto(
    val libraryId: Int = 0,
    val userId: Int,
    val libraryName: String?,
    val libraryAddress: String?,
    val library_poster: String?,
    val library_rate: Double? = null,
    val libraryPhone: String?,
    val createdAt: String = ""
)
