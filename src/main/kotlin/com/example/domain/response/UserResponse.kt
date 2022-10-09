package com.example.domain.response

import com.example.domain.request.User
import kotlinx.serialization.Serializable


@Serializable
data class UserResponse(
    val status: String,
    val message: String? = null,
    val user: User? = null
)

