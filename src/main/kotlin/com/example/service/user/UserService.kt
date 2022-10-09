package com.example.service.user

import com.example.domain.request.User

interface UserService {
    suspend fun getUserById(userId: Int): User?
    suspend fun updateProfileUser(user: User): Boolean
    suspend fun updateStatusLibraryForUser(userId: Int):Boolean
}