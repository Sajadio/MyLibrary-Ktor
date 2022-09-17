package com.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.domain.model.UserDto
import com.example.domain.response.TokenResponse
import com.example.utils.EXPIRY_TIME
import io.ktor.server.config.*
import io.ktor.util.date.*
import java.util.*

class JwtTokenService : TokenService {

    override fun generateAccessToken(config: TokenConfig, claim: UserDto): TokenResponse {
        val createdAt = getTimeMillis()
        var accessToken = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(createdAt + EXPIRY_TIME))
            .withClaim("userId", claim.userId)
            .sign(Algorithm.HMAC256(config.secret))

        return TokenResponse(createdAt, config.expireInMs, accessToken)
    }
}