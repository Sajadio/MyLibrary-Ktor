package com.example.plugins

import com.example.routes.auth.authRoutes
import com.example.security.JwtTokenService
import com.example.security.TokenConfig
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(tokenProviderService: JwtTokenService, tokenConfig: TokenConfig) {
    routing {
        authRoutes(tokenProviderService, tokenConfig)
    }
}
