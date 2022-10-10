package com.example.domain.repository

import com.example.domain.request.User
import com.example.utils.Response

interface UserRepository {
    suspend fun getUserById(userId: Int): Response<Any>
    suspend fun updateProfileUser(user: User,userId: Int): Response<Any>
    suspend fun updateStatusLibraryForUser(userId: Int):Boolean
    suspend fun isTheSameImage(imageURi:String):Boolean
    suspend fun updateProfileImage(imageURi:String,userId: Int): Response<Any>

}