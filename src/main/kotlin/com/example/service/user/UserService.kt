package com.example.service.user

import com.example.domain.model.UserDto
import com.example.domain.response.User

interface UserService {
    suspend fun getUserById(userId: Int): UserDto?
    suspend fun updateProfileUser(user: User, userId: Int): Boolean
    suspend fun updateStatusLibraryForUser(userId: Int):Boolean
}