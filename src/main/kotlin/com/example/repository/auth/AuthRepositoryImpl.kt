package com.example.repository.auth

import com.example.domain.model.*
import com.example.service.auth.AuthService
import com.example.utils.*
import io.ktor.http.*

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun userSignUp(newUser: NewUser) = authService.userSignUp(newUser)?.let { result ->
        checkResponseStatus(
            USER_REGISTRATION_SUCCESS,
            HttpStatusCode.OK,
            result
        )
    } ?: checkResponseStatus(
        message = MESSAGE_EMAIL_ALREADY_REGISTERED,
        statusCode = HttpStatusCode.BadRequest
    )


    override suspend fun userLogIn(userCredentials: UserCredentials) =
        authService.userLogIn(userCredentials)?.let { result ->
            checkResponseStatus(
                USER_LOGIN_SUCCESS,
                HttpStatusCode.OK,
                result
            )
        } ?: checkResponseStatus(
            message = USER_LOGIN_FAILURE,
            statusCode = HttpStatusCode.BadRequest
        )

    override suspend fun findUserByEmail(email: String) = authService.findUserByEmail(email)
    override suspend fun adminSignUp(newAdmin: NewAdmin) = authService.adminSignUp(newAdmin)?.let { result ->
        checkResponseStatus(
            ADMIN_REGISTRATION_SUCCESS,
            HttpStatusCode.OK,
            result
        )
    } ?: checkResponseStatus(
        message = MESSAGE_EMAIL_ALREADY_REGISTERED,
        statusCode = HttpStatusCode.BadRequest
    )

    override suspend fun adminLogIn(adminCredentials: AdminCredentials) =
        authService.adminLogIn(adminCredentials)?.let { result ->
            checkResponseStatus(
                ADMIN_LOGIN_SUCCESS,
                HttpStatusCode.OK,
                result
            )
        } ?: checkResponseStatus(
            message = ADMIN_LOGIN_SUCCESS,
            statusCode = HttpStatusCode.BadRequest
        )

    override suspend fun findAdminByEmail(email: String)= authService.findAdminByEmail(email)

}