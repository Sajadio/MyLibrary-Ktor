package com.example.domain.request

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val userId: Int = 0,
    val fullName: String? = null,
    val imageURL: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val gander: String? = null,
    val dateOfBirth: String? = null,
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
