package com.example.routes

import com.example.security.JwtService.adminId
import com.example.security.JwtService.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

val ApplicationCall.userId: String get() = principal<JWTPrincipal>()?.userId.toString()
val ApplicationCall.adminId: String get() = principal<JWTPrincipal>()?.adminId.toString()