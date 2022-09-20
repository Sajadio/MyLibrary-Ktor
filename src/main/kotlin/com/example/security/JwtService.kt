package com.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.domain.model.UserDto
import com.example.domain.response.ErrorResponse
import com.example.domain.response.TokenResponse
import com.example.utils.ERROR
import com.example.utils.EXPIRY_TIME
import com.example.utils.INVALID_AUTHENTICATION_TOKEN
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.util.date.*
import java.util.*

object JwtService {

    private const val issuer = "libraryServer"
    private const val audience = "libraryServer"
    private const val subject = "MyLibraryAuthentication"
    private val secret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(secret)
    private val expiresIn = getTimeMillis() + EXPIRY_TIME
    private val createdAt = getTimeMillis()

    private const val CLAIM_ID = "userId"
    private const val CLAIM_EMAIL = "email"

    private val jwtVerifier: JWTVerifier = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun generateAccessToken(user: UserDto): TokenResponse {
        val accessToken = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withSubject(subject)
            .withClaim(CLAIM_ID, user.userId)
            .withClaim(CLAIM_EMAIL, user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRY_TIME))
            .sign(algorithm)
        return TokenResponse(createdAt, expiresIn, accessToken)
    }

    fun customerAuth(auth: AuthenticationConfig) {
        auth.jwt("auth-customer") {
            verifier(jwtVerifier)
            validate {
                checkValidate(it)
            }
            challenge { _, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    ErrorResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN,
                        code = HttpStatusCode.Unauthorized.description,
                    )
                )
            }
        }
    }


    private fun checkValidate(credential: JWTCredential): JWTPrincipal? {
        return if (credential.payload.audience.contains(audience)) {
            JWTPrincipal(credential.payload)
        } else null
    }
}