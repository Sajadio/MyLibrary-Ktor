package com.example.repository.library

import com.example.domain.model.LibraryDto
import com.example.utils.Response

interface LibraryRepository {
    suspend fun addLibrary(libraryDto: LibraryDto): Response<out Any>
    suspend fun getLibraryById(libraryId:Int): Response<Any>
    suspend fun getLibraryByName(libraryName:String): Response<Any>
    suspend fun getAllLibrary(): Response<Any>
    suspend fun deleteUserLibrary(userId:Int): Response<Any>
    suspend fun updateLibraryInfo(libraryDto: LibraryDto): Response<Any>
    suspend fun checkIfUserHasLibrary(userId: Int, libraryId: Int): Boolean
    suspend fun checkIfTheLibraryIsAccepted(userId: Int): Boolean
}