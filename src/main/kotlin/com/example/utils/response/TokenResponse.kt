package com.example.utils.response

data class TokenResponse(
    val createdAt: Long,
    val expireInMs: Long,
    val accessToken: String,
)