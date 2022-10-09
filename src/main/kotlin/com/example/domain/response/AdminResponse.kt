package com.example.domain.response

import com.example.domain.request.Admin
import com.example.domain.request.User
import kotlinx.serialization.Serializable


@Serializable
data class AdminResponse(
    val status: String,
    val message: String? = null,
    val admin: Admin? = null,
)

@Serializable
data class UsersResponse(
    val status: String,
    val message: String? = null,
    val users: List<User>? = null
)
