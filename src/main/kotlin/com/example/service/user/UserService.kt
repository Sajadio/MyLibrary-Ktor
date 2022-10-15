package com.example.service.user

import com.example.domain.request.User

interface UserService {
    suspend fun getUserById(userId: Int): User?
    suspend fun updateProfileUser(user: User,userId: Int): Boolean
    suspend fun updateStatusLibraryForUser(userId: Int):Boolean
    suspend fun isTheSameImage(imageURI:String): Boolean
    suspend fun updateProfileImage(imageURi:String,userId: Int):Boolean
}