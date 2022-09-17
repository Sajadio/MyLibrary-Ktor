package com.example

import com.example.database.DatabaseFactory
import com.example.domain.response.ErrorResponse
import io.ktor.server.application.*
import com.example.plugins.*
import com.example.security.JwtTokenService
import com.example.security.TokenConfig
import com.example.utils.EXPIRY_TIME
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    configureStatusPage()

    DatabaseFactory.init()

    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET"),
        expireInMs = EXPIRY_TIME

    )
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
    configureRouting(tokenService,tokenConfig)
}
