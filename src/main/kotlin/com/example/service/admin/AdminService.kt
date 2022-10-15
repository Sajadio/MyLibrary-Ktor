package com.example.service.admin

import com.example.domain.request.Admin
import com.example.domain.request.Library
import com.example.domain.request.User

interface AdminService {
    suspend fun getAdminById(adminId: Int): Admin?
    suspend fun updateAdminInfo(admin: Admin,adminId:Int): Boolean
    suspend fun isTheSameImage(imageURI:String): Boolean
    suspend fun updateProfileImage(imageURi:String,adminId: Int):Boolean
    suspend fun getAllUsers(): List<User?>
    suspend fun getUserById(userId: Int): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun getAllLibrariesNotAccepted(): List<Library?>
    suspend fun deleteUserById(userId: Int): Boolean
    suspend fun deleteAllUsers(): Boolean
    suspend fun deleteLibraryById(libraryId: Int): Boolean
    suspend fun deleteAllLibraries(): Boolean
    suspend fun acceptLibrary(libraryId: Int):Boolean
    suspend fun rejectLibrary(libraryId: Int):Boolean

}