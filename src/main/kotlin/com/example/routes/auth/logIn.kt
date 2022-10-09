package com.example.routes.auth

import com.example.domain.request.AdminCredentials
import com.example.domain.request.Admin
import com.example.domain.request.UserCredentials
import com.example.domain.request.User
import com.example.domain.repository.AuthRepository
import com.example.security.JwtService
import com.example.security.UserPrincipal
import com.example.utils.AuthType
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import com.example.domain.response.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.login(repository: AuthRepository) {
    post("/login/auth/{type}") {
        try {
            when (call.parameters["type"]) {
                AuthType.ADMIN.name -> {
                    val request = call.receive<AdminCredentials>()
                    when (val result = repository.adminLogIn(request)) {

                        is Response.SuccessResponse -> {
                            val admin = result.data as Admin
                            val userPrincipal = UserPrincipal(
                                id = admin.adminId.toString(),
                                email = admin.email.toString(),
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
                            val user = result.data as User
                            val userPrincipal = UserPrincipal(
                                id = user.userId.toString(),
                                email = user.email.toString(),
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
