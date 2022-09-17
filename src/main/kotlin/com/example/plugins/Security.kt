package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.domain.response.ErrorResponse
import com.example.security.TokenConfig
import com.example.utils.INVALID_AUTHENTICATION_TOKEN
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureSecurity(config: TokenConfig) {

    authentication {
        jwt {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(config.audience)) {
                    JWTPrincipal(credential.payload)
                } else null
            }
            challenge { _, _ ->
                call.respond(
                    ErrorResponse(
                        status = "error",
                        message = INVALID_AUTHENTICATION_TOKEN,
                        code =  HttpStatusCode.Unauthorized.description,
                    )
                )
            }
        }
    }

}
