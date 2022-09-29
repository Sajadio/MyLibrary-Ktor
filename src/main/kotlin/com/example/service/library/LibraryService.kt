package com.example.service.library

import com.example.domain.model.LibraryDto
import com.example.domain.model.UserDto
import org.jetbrains.exposed.sql.ResultRow

interface LibraryService {
    suspend fun addLibrary(libraryDto: LibraryDto): Boolean
    suspend fun deleteLibrary(libraryId:Int):Boolean
    suspend fun getLibraryById(libraryId:Int): LibraryDto?
    suspend fun getAllLibrary(): List<LibraryDto?>
    suspend fun findLibraryByName(libraryName:String): LibraryDto?
    suspend fun updateLibraryInfo(libraryDto: LibraryDto): Boolean
    suspend fun checkIfUserHasLibrary(userId:Int): Boolean
}