package com.example.service.admin

import com.example.domain.model.AdminDto
import com.example.domain.model.LibraryDto
import com.example.domain.model.UserDto
import com.example.domain.response.Admin

interface AdminService {
    suspend fun getAdminById(adminId: Int): AdminDto?
    suspend fun updateAdminInfo(admin: Admin, adminId: Int): Boolean
    suspend fun getAllUsers(): List<UserDto?>
    suspend fun getUserById(userId: Int): UserDto?
    suspend fun findUserByEmail(email: String): UserDto?
    suspend fun deleteAllUsers(): Boolean
    suspend fun deleteUserById(userId: Int): Boolean
    suspend fun getAllLibrariesNotAccepted(): List<LibraryDto?>
    suspend fun acceptLibrary(libraryId: Int):Boolean
    suspend fun rejectLibrary(libraryId: Int):Boolean
}