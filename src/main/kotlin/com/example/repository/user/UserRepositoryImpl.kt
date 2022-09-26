package com.example.repository.user

import com.example.utils.response.User
import com.example.service.user.UserService
import com.example.utils.*
import io.ktor.http.*

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {

    override suspend fun getUserById(userId: Int): Response<Any> {
        return userService.getUserById(userId)?.let { user ->
            checkResponseStatus(
                SUCCESS,
                HttpStatusCode.OK,
                user
            )
        } ?: checkResponseStatus(
            message = MESSAGE_USER_ID,
            statusCode = HttpStatusCode.NotFound
        )
    }

    override suspend fun updateUserInfo(user: User, userId: Int): Response<Any> {
        return try {
            if (userService.updateProfileUser(user, userId))
                Response.SuccessResponse(
                    message = SUCCESS,
                    statusCode = HttpStatusCode.OK,
                    data = null
                )
            else
                Response.ErrorResponse(
                    message = GENERIC_ERROR,
                    statusCode = HttpStatusCode.BadRequest,
                )
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.message.toString(), statusCode = HttpStatusCode.BadRequest)
        }
    }

}