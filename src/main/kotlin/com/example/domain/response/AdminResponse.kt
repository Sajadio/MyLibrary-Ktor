package com.example.domain.response

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

@Serializable
data class Admin(
    val adminId: Int = 0,
    val fullName: String? = null,
    val urlPhoto: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val createdAt: String = ""
)
