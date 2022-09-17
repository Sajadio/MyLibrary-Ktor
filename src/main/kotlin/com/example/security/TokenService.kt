package com.example.security

import com.example.domain.model.UserDto
import com.example.domain.response.TokenResponse

interface TokenService {
    fun generateAccessToken(config: TokenConfig, claim: UserDto): TokenResponse
}