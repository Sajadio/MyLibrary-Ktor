package com.example.data.repository

import com.example.domain.repository.UserRepository
import com.example.domain.request.User
import com.example.service.user.UserService
import com.example.utils.*
import io.ktor.http.*

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {

    override suspend fun getUserById(userId: Int) =
        userService.getUserById(userId)?.let { user ->
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK,
                data = user
            )
        } ?: checkResponseStatus(
            message = MESSAGE_USER_ID,
            statusCode = HttpStatusCode.NotFound
        )


    override suspend fun updateProfileUser(user: User,userId: Int) =
        if (userService.updateProfileUser(user,userId))
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK,
            )
        else
            checkResponseStatus(
                message = GENERIC_ERROR,
                statusCode = HttpStatusCode.BadRequest,
            )

    override suspend fun updateStatusLibraryForUser(userId: Int) = userService.updateStatusLibraryForUser(userId)
    override suspend fun isTheSameImage(imageURi: String) = userService.isTheSameImage(imageURi)
    override suspend fun updateProfileImage(imageURi: String, userId: Int) =
        if (userService.updateProfileImage(imageURi,userId))
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK,
            )
        else
            checkResponseStatus(
                message = GENERIC_ERROR,
                statusCode = HttpStatusCode.BadRequest,
            )

}