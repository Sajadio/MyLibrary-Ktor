package com.example.repository.user

import com.example.domain.response.User
import com.example.utils.Response

interface UserRepository {
    suspend fun getUserById(userId: Int): Response<Any>
    suspend fun updateUserInfo(user: User, userId: Int): Response<Any>
    suspend fun updateStatusLibraryForUser(userId: Int):Boolean

}