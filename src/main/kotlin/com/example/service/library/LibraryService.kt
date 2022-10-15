package com.example.service.library

import com.example.domain.request.Library

interface LibraryService {
    suspend fun addLibrary(library: Library): Boolean
    suspend fun getLibraryById(libraryId: Int): Library?
    suspend fun getAllLibrary(): List<Library?>
    suspend fun getLibraryByName(libraryName: String): Library?
    suspend fun deleteUserLibrary(userId: Int): Boolean
    suspend fun updateLibraryInfo(library: Library): Boolean
    suspend fun checkIfUserHasLibrary(userId: Int): Boolean
    suspend fun checkIfTheLibraryIsAccepted(userId: Int): Library?

}