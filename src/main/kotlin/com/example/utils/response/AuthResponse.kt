package com.example.utils.response

data class AuthResponse(
    val status: String,
    val message: String? = null,
    val accessToken: TokenResponse? = null
)
