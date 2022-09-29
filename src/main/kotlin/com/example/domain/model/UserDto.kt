package com.example.domain.model


data class UserDto(
    val userId: Int = 0,
    val fullName: String? = null,
    val urlPhoto: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phoneNumber: String? = null,
    val doHaveLibrary: Boolean = false,
    val createdAt: String = ""
)

data class NewUser(
    val fullName: String,
    val email: String,
    val password: String,
)

data class UserCredentials(
    val email: String,
    val password: String,
)
