package com.example.routes.auth

import com.example.domain.model.AdminCredentials
import com.example.domain.model.AdminDto
import com.example.domain.model.UserCredentials
import com.example.domain.model.UserDto
import com.example.repository.auth.AuthRepository
import com.example.security.JwtService
import com.example.security.UserPrincipal
import com.example.utils.AuthType
import com.example.utils.ERROR
import com.example.utils.OK
import com.example.utils.Response
import com.example.utils.response.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.login(repository: AuthRepository) {
    post("/login") {
        try {
            when (call.request.queryParameters["auth-type"]) {
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
