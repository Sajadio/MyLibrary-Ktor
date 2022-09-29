package com.example.service.library

import com.example.domain.model.LibraryDto

interface LibraryService {
    suspend fun addLibrary(libraryDto: LibraryDto): Boolean
    suspend fun getLibraryById(libraryId: Int): LibraryDto?
    suspend fun getAllLibrary(): List<LibraryDto?>
    suspend fun getLibraryByName(libraryName: String): LibraryDto?
    suspend fun deleteUserLibrary(userId: Int): Boolean
    suspend fun updateLibraryInfo(libraryDto: LibraryDto): Boolean
    suspend fun checkIfUserHasLibrary(userId: Int, libraryId: Int): Boolean
    suspend fun checkIfTheLibraryIsAccepted(userId: Int): LibraryDto?

}