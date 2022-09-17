package com.example.domain.response

import kotlinx.serialization.Serializable


@Serializable
data class UserResponse(
    val status: String,
    val message: String? = null,
    val user: User? = null
)

@Serializable
data class User(
    val userId: Int,
    val fullName: String,
    val photo: String,
    val email: String,
    val phoneNumber: Int,
    val createdAt: String
)
