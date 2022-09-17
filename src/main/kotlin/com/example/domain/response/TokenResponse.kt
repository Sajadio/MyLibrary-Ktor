package com.example.domain.response

data class TokenResponse(
    val createdAt: Long,
    val expireInMs: Long,
    val accessToken: String,
)