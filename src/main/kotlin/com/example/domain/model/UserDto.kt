package com.example.domain.model

import io.ktor.server.auth.*


data class UserDto(
    val userId: Int = 0,
    val fullName: String,
    val photo: String = "",
    val email: String,
    val password: String,
    val phoneNumber: Int,
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

data class UserSession(val name: String, val count: Int) : Principal
