package com.example.repository.auth

import com.example.domain.model.*
import com.example.service.admin.AdminService
import com.example.service.auth.admin.AdminAuth
import com.example.service.auth.user.UserAuth
import com.example.utils.*
import io.ktor.http.*

class AuthRepositoryImpl(
    private val adminAuth: AdminAuth,
    private val userAuth: UserAuth,
) : AuthRepository {
    override suspend fun userSignUp(newUser: NewUser) = userAuth.userSignUp(newUser)?.let { result ->
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
        userAuth.userLogIn(userCredentials)?.let { result ->
            checkResponseStatus(
                message = USER_LOGIN_SUCCESS,
                statusCode = HttpStatusCode.OK,
                data = result
            )
        } ?: checkResponseStatus(
            message = LOGIN_FAILURE,
            statusCode = HttpStatusCode.BadRequest
        )

    override suspend fun findUserByEmail(email: String) = userAuth.findUserByEmail(email)

    override suspend fun adminSignUp(newAdmin: NewAdmin) = adminAuth.adminSignUp(newAdmin)?.let { result ->
        checkResponseStatus(
            message = ADMIN_REGISTRATION_SUCCESS,
            statusCode = HttpStatusCode.OK,
            data = result
        )
    } ?: checkResponseStatus(
        message = MESSAGE_EMAIL_ALREADY_REGISTERED,
        statusCode = HttpStatusCode.BadRequest
    )

    override suspend fun adminLogIn(adminCredentials: AdminCredentials) =
        adminAuth.adminLogIn(adminCredentials)?.let { result ->
            checkResponseStatus(
                message = ADMIN_LOGIN_SUCCESS,
                statusCode = HttpStatusCode.OK,
                data = result
            )
        } ?: checkResponseStatus(
            message = ADMIN_LOGIN_SUCCESS,
            statusCode = HttpStatusCode.BadRequest
        )

    override suspend fun findAdminByEmail(email: String) = adminAuth.findAdminByEmail(email)

}