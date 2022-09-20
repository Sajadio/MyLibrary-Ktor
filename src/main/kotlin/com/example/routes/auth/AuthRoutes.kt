package com.example.routes.auth

import com.example.data.mapper.implement.UserBodyMapper
import com.example.di.RepositoryProvider
import com.example.domain.model.NewUser
import com.example.domain.model.UserDto
import com.example.domain.model.UserCredentials
import com.example.domain.response.AuthResponse
import com.example.domain.response.UserResponse
import com.example.repository.auth.AuthRepository
import com.example.security.JwtService
import com.example.utils.*
import com.example.utils.validate.ValidateEmail
import com.example.utils.validate.ValidatePassword
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    val repository = RepositoryProvider.provideAuthRepository()
    route("/auth") {
        signUp(repository)
        signIn(repository)
        getSecretInfo()
    }
}

private fun Route.signUp(repository: AuthRepository) {
    post("/signup") {
        val request = call.receive<NewUser>()

        val validateEmail = ValidateEmail().execute(request.email)
        val validatePassword = ValidatePassword().execute(request.password)

        if (validateEmail.status == ERROR) {
            call.respond(HttpStatusCode.BadRequest, validateEmail)
            return@post
        }
        if (validatePassword.status == ERROR) {
            call.respond(HttpStatusCode.BadRequest, validatePassword)
            return@post
        }
        if (repository.findUserByEmail(request.email) != null) {
            call.respond(
                HttpStatusCode.Forbidden,
                AuthResponse(
                    status = ERROR,
                    message = MESSAGE_EMAIL_ALREADY_REGISTERED,
                )
            )
            return@post
        }
        when (val result = repository.signUp(request)) {
            is Response.SuccessResponse -> {
                val createToken = JwtService.generateAccessToken(result.data as UserDto)
                call.respond(
                    result.statusCode, AuthResponse(
                        status = OK,
                        message = result.message,
                        accessToken = createToken
                    )
                )
            }

            is Response.ErrorResponse -> {
                call.respond(
                    result.statusCode, AuthResponse(
                        status = ERROR,
                        message = result.message,
                    )
                )
            }
        }
    }
}

private fun Route.signIn(repository: AuthRepository) {
    post("/signin") {
        val request = call.receive<UserCredentials>()

        when (val result = repository.signIn(request)) {
            is Response.SuccessResponse -> {
                val createToken = JwtService.generateAccessToken(result.data as UserDto)
                call.respond(
                    result.statusCode, AuthResponse(
                        status = OK,
                        message = result.message,
                        accessToken = createToken
                    )
                )
            }

            is Response.ErrorResponse -> {
                call.respond(
                    result.statusCode, AuthResponse(
                        status = ERROR,
                        message = result.message,
                    )
                )
            }
        }

    }
}


private fun Route.getSecretInfo() {
    authenticate("auth-customer") {
        get("/user") {
            val principal = call.principal<JWTPrincipal>()
            val repository = RepositoryProvider.provideAuthRepository()

            when (val result = repository.findUserById(principal?.getClaim("userId", Int::class)!!)) {
                is Response.SuccessResponse -> {
                    val mapper = UserBodyMapper.mapTo(result.data as UserDto)
                    call.respond(
                        result.statusCode, UserResponse(
                            status = OK,
                            message = result.message,
                            user = mapper
                        )
                    )
                }

                is Response.ErrorResponse -> {
                    call.respond(
                        result.statusCode, UserResponse(
                            status = ERROR,
                            message = result.message,
                        )
                    )
                }
            }
        }
    }
}
