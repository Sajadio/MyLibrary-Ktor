package com.example.domain.response

data class AuthResponse(
    val status: String,
    val message: String? = null,
    val accessToken: TokenResponse? = null
)
