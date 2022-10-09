package com.example.domain.repository

import com.example.domain.request.User
import com.example.utils.Response

interface UserRepository {
    suspend fun getUserById(userId: Int): Response<Any>
    suspend fun updateUserInfo(user: User): Response<Any>
    suspend fun updateStatusLibraryForUser(userId: Int):Boolean

}