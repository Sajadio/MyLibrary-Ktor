package com.example.utils.response

import kotlinx.serialization.Serializable


@Serializable
data class LibraryResponse(
    val status: String,
    val message: String? = null,
    val library: Library? = null
)

@Serializable
data class LibrariesResponse(
    val status: String,
    val message: String? = null,
    val libraries: List<Library>? = null
)

@Serializable
data class Library(
    val libraryId: Int,
    val userId: Int,
    val libraryName: String?,
    val libraryAddress: String?,
    val library_poster: String?,
    val library_rate: Double?,
    val libraryPhone: String?,
    val createdAt: String = ""
)
