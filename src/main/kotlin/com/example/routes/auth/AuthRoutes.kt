package com.example.routes.auth

import com.example.domain.model.*
import com.example.utils.response.AuthResponse
import com.example.repository.auth.AuthRepository
import com.example.security.JwtService
import com.example.security.UserPrincipal
import com.example.utils.*
import com.example.utils.validate.ValidateEmail
import com.example.utils.validate.ValidatePassword
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.signUp(repository: AuthRepository) {
    post("/signup") {
        val authType = call.request.queryParameters["auth-type"]

        try {
            when (authType) {
                AuthType.ADMIN.name -> {
                    val request = call.receive<NewAdmin>()

                    val validateEmail = ValidateEmail.execute(request.email)
                    val validatePassword = ValidatePassword.execute(request.password)
                    if (validateEmail.status == ERROR) {
                        call.respond(HttpStatusCode.BadRequest, validateEmail)
                        return@post
                    }
                    if (validatePassword.status == ERROR) {
                        call.respond(HttpStatusCode.BadRequest, validatePassword)
                        return@post
                    }

                    if (repository.findAdminByEmail(request.email) != null) {
                        call.respond(
                            HttpStatusCode.Forbidden,
                            AuthResponse(
                                status = ERROR,
                                message = MESSAGE_EMAIL_ALREADY_REGISTERED,
                            )
                        )
                        return@post
                    }

                    when (val result = repository.adminSignUp(request)) {
                        is Response.SuccessResponse -> {
                            val adminDto = result.data as AdminDto
                            val userPrincipal = UserPrincipal(
                                id = adminDto.adminId.toString(),
                                email = adminDto.email.toString(),
                            )
                            val createToken = JwtService.generateAccessToken(userPrincipal)
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

                AuthType.USER.name -> {
                    val request = call.receive<NewUser>()
                    val validateEmail = ValidateEmail.execute(request.email)
                    val validatePassword = ValidatePassword.execute(request.password)
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
                    when (val result = repository.userSignUp(request)) {
                        is Response.SuccessResponse -> {
                            val userDto = result.data as UserDto
                            val userPrincipal = UserPrincipal(
                                id = userDto.userId.toString(),
                                email = userDto.email.toString(),
                            )
                            val createToken = JwtService.generateAccessToken(userPrincipal)
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
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest, AuthResponse(
                    status = ERROR,
                    message = e.message,
                )
            )
        }
    }
}

fun Route.login(repository: AuthRepository) {
    post("/login") {
        val authType = call.request.queryParameters["auth-type"]
        try {
            when (authType) {
                AuthType.ADMIN.name -> {
                    val request = call.receive<AdminCredentials>()
                    when (val result = repository.adminLogIn(request)) {

                        is Response.SuccessResponse -> {
                            val adminDto = result.data as AdminDto
                            val userPrincipal = UserPrincipal(
                                id = adminDto.adminId.toString(),
                                email = adminDto.email.toString(),
                            )
                            val createToken = JwtService.generateAccessToken(userPrincipal)
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

                AuthType.USER.name -> {
                    val request = call.receive<UserCredentials>()
                    when (val result = repository.userLogIn(request)) {

                        is Response.SuccessResponse -> {
                            val userDto = result.data as UserDto
                            val userPrincipal = UserPrincipal(
                                id = userDto.userId.toString(),
                                email = userDto.email.toString(),
                            )
                            val createToken = JwtService.generateAccessToken(userPrincipal)
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
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest, AuthResponse(
                    status = ERROR,
                    message = e.message,
                )
            )
        }

    }
}
