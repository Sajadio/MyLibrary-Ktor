package com.example.domain.request

import kotlinx.serialization.Serializable


@Serializable
data class Admin(
    val adminId: Int = 0,
    val fullName: String? = null,
    val urlPhoto: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val createdAt: String = ""
)

data class NewAdmin(
    val fullName: String,
    val email: String,
    val password: String,
)

data class AdminCredentials(
    val email: String,
    val password: String,
)
