package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.example.security.JwtService
import io.ktor.server.application.*

fun Application.configureSecurity() {

    authentication {
        JwtService.customerAuth(this)
        jwt {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
        }
    }
}



