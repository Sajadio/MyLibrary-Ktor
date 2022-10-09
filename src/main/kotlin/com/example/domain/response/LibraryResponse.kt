package com.example.domain.response

import com.example.domain.request.Library
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
