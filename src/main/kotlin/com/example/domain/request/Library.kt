package com.example.domain.request

import kotlinx.serialization.Serializable

@Serializable
data class Library(
    val libraryId: Int = 0,
    val userId: Int,
    val libraryName: String?,
    val libraryAddress: String?,
    val libraryImage: String?,
    val library_rate: Double? = null,
    val libraryPhone: String?,
    val isAccept: Boolean = false,
    val createdAt: String = ""
)
