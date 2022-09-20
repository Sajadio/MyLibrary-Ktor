package com.example.security

import io.ktor.server.auth.*

data class UserIdPrincipal(val userId: Int,val email:String): Principal