package com.example.routes.auth

import com.example.data.mapper.implement.UserBodyMapper
import com.example.di.RepositoryProvider
import com.example.domain.model.NewUser
import com.example.domain.model.UserDto
import com.example.domain.model.UserCredentials
import com.example.domain.response.AuthResponse
import com.example.domain.response.ErrorResponse
import com.example.domain.response.UserResponse
import com.example.repository.auth.AuthRepository
import com.example.security.JwtTokenService
import com.example.security.TokenConfig
import com.example.utils.MESSAGE_EMAIL_ALREADY_REGISTERED
import com.example.utils.Response
import com.example.utils.SUCCESS
import com.example.utils.validate.ValidateEmail
import com.example.utils.validate.ValidatePassword
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(tokenProviderService: JwtTokenService, tokenConfig: TokenConfig) {
    val repository = RepositoryProvider.provideAuthRepository()
    route("/auth") {
        signUp(tokenProviderService, tokenConfig, repository)
        signIn(tokenProviderService, tokenConfig, repository)
        getSecretInfo()
    }
}

private fun Route.signUp(tokenProviderService: JwtTokenService, tokenConfig: TokenConfig, repository: AuthRepository) {
    post("/signup") {
        val request = call.receive<NewUser>()

        val validateEmail = ValidateEmail().execute(request.email)
        val validatePassword = ValidatePassword().execute(request.password)

        if (validateEmail is Response.ErrorResponse) {
            call.respond(
                validateEmail.statusCode,
                AuthResponse(
                    status = "error",
                    message = validateEmail.message,
                )
            )
            return@post
        }
        if (validatePassword is Response.ErrorResponse) {
            call.respond(
                validatePassword.statusCode,
                AuthResponse(
                    status = "error",
                    message = validatePassword.message,
                )
            )
            return@post
        }
        if (repository.findUserByEmail(request.email) != null) {
            call.respond(
                HttpStatusCode.OK,
                AuthResponse(
                    status = "error",
                    message = MESSAGE_EMAIL_ALREADY_REGISTERED,
                )
            )
            return@post
        }
        when (val result = repository.signUp(request)) {
            is Response.SuccessResponse -> {
                val createToken = tokenProviderService.generateAccessToken(config = tokenConfig, result.data as UserDto)
                call.respond(
                    result.statusCode, AuthResponse(
                        status = "ok",
                        message = result.message,
                        accessToken = createToken
                    )
                )
            }

            is Response.ErrorResponse -> {
                call.respond(
                    result.statusCode, AuthResponse(
                        status = "error",
                        message = result.message,
                    )
                )
            }
        }
    }
}

private fun Route.signIn(tokenProviderService: JwtTokenService, tokenConfig: TokenConfig, repository: AuthRepository) {
    post("/signin") {
        val request = call.receive<UserCredentials>()

        when (val result = repository.signIn(request)) {
            is Response.SuccessResponse -> {
                val createToken = tokenProviderService.generateAccessToken(config = tokenConfig, result.data as UserDto)
                call.respond(
                    result.statusCode, AuthResponse(
                        status = "ok",
                        message = result.message,
                        accessToken = createToken
                    )
                )
            }

            is Response.ErrorResponse -> {
                call.respond(
                    result.statusCode, AuthResponse(
                        status = "error",
                        message = result.message,
                    )
                )
            }
        }

    }
}


private fun Route.getSecretInfo() {
    authenticate() {
        get("/user") {
            val principal = call.principal<JWTPrincipal>()
            val repository = RepositoryProvider.provideAuthRepository()

            when (val result = repository.findUserById(principal?.getClaim("userId", Int::class)!!)) {
                is Response.SuccessResponse -> {
                    val mapper = UserBodyMapper.mapTo(result.data as UserDto)
                    call.respond(
                        result.statusCode, UserResponse(
                            status = "ok",
                            message = result.message,
                            user = mapper
                        )
                    )
                }

                is Response.ErrorResponse -> {
                    call.respond(
                        result.statusCode, UserResponse(
                            status = "error",
                            message = result.message,
                        )
                    )
                }
            }
        }
    }
}
