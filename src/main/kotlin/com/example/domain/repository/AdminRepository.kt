package com.example.domain.repository

import com.example.domain.request.Admin
import com.example.utils.Response

interface AdminRepository {
    suspend fun getAdminById(adminId: Int): Response<Any>
    suspend fun getAllUsers(): Response<Any>
    suspend fun getUserById(userId: Int): Response<Any>
    suspend fun getUserByEmail(email: String): Response<Any>
    suspend fun getAllLibrariesNotAccepted(): Response<Any>
    suspend fun deleteAllUsers(): Response<Any>
    suspend fun deleteUserById(userId: Int): Response<Any>
    suspend fun deleteLibraryById(libraryId: Int): Response<Any>
    suspend fun deleteAllLibraries(): Response<Any>
    suspend fun updateAdminInfo(admin: Admin): Response<Any>
    suspend fun acceptLibrary(libraryId: Int): Response<Any>
    suspend fun rejectLibrary(libraryId: Int): Response<Any>
}
