package com.example.domain.repository

import com.example.domain.request.Library
import com.example.utils.Response

interface LibraryRepository {
    suspend fun addLibrary(library: Library): Response<out Any>
    suspend fun getLibraryById(libraryId:Int): Response<Any>
    suspend fun getLibraryByName(libraryName:String): Response<Any>
    suspend fun getAllLibrary(): Response<Any>
    suspend fun deleteUserLibrary(userId:Int): Response<Any>
    suspend fun updateLibraryInfo(library: Library): Response<Any>
    suspend fun checkIfUserHasLibrary(userId: Int, libraryId: Int): Boolean
    suspend fun checkIfTheLibraryIsAccepted(userId: Int): Boolean
}