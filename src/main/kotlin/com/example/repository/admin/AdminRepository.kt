package com.example.repository.admin

import com.example.utils.Response
import com.example.utils.response.Admin

interface AdminRepository {
    suspend fun getAdminById(adminId: Int): Response<Any>
    suspend fun updateAdminInfo(admin: Admin, adminId: Int): Response<Any>
    suspend fun getAllUsers(): Response<Any>
    suspend fun getUserById(userId: Int): Response<Any>
    suspend fun findUserByEmail(email:String): Response<Any>
    suspend fun deleteAllUsers(): Response<Any>
    suspend fun deleteUserById(userId: Int): Response<Any>
}