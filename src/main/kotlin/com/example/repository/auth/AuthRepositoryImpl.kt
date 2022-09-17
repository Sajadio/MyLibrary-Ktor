package com.example.repository.auth

import com.example.domain.model.NewUser
import com.example.domain.model.UserCredentials
import com.example.service.auth.AuthService
import com.example.utils.*
import io.ktor.http.*

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun signUp(newUser: NewUser): Response<Any> {
        return try {
            val result = authService.signUp(newUser)
            if (result != null) {
                Response.SuccessResponse(
                    message = USER_REGISTRATION_SUCCESS,
                    statusCode = HttpStatusCode.Created,
                    data = result
                )
            } else {
                Response.ErrorResponse(GENERIC_ERROR, statusCode = HttpStatusCode.BadRequest)
            }
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString(), statusCode = HttpStatusCode.BadRequest)
        }

    }

    override suspend fun signIn(userCredentials: UserCredentials): Response<Any> {
        return try {
            val result = authService.signIn(userCredentials)
            if (result != null) {
                Response.SuccessResponse(
                    message = USER_LOGIN_SUCCESS,
                    HttpStatusCode.OK,
                    data = result
                )
            } else {
                Response.ErrorResponse(USER_LOGIN_FAILURE, HttpStatusCode.BadRequest)
            }
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString(), statusCode = HttpStatusCode.BadRequest)
        }
    }

    override suspend fun findUserByEmail(email: String) = authService.findUserByEmail(email)

    override suspend fun findUserById(id: Int): Response<Any> {
        return try {
            authService.findUserById(id)?.let { user ->
                Response.SuccessResponse(
                    message = SUCCESS,
                    statusCode = HttpStatusCode.Found,
                    data = user
                )
            } ?: Response.ErrorResponse(
                message = USER_LOGIN_FAILURE,
                statusCode = HttpStatusCode.NotFound
            )
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString(), statusCode = HttpStatusCode.BadRequest)
        }
    }


}