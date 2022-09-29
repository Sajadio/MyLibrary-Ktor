package com.example.service.library

import com.example.database.DatabaseFactory
import com.example.database.table.LibraryTable
import com.example.database.table.toLibraryDto
import com.example.domain.model.LibraryDto
import org.jetbrains.exposed.sql.*

class LibraryServiceImpl : LibraryService {

    override suspend fun addLibrary(libraryDto: LibraryDto) = DatabaseFactory.dbQuery {
        LibraryTable.insert {
            it[userId] = libraryDto.userId
            it[libraryName] = libraryDto.libraryName
            it[libraryAddress] = libraryDto.libraryAddress
            it[library_poster] = libraryDto.library_poster
            it[libraryPhone] = libraryDto.libraryPhone
        }.insertedCount > 0
    }

    override suspend fun getLibraryById(libraryId: Int) = DatabaseFactory.dbQuery {
        LibraryTable.select {
            LibraryTable.libraryId eq libraryId
        }.map { result ->
            result.toLibraryDto()
        }.singleOrNull()
    }

    override suspend fun getLibraryByName(libraryName: String) = DatabaseFactory.dbQuery {
        LibraryTable.select {
            LibraryTable.libraryName eq libraryName
        }.map { result ->
            result.toLibraryDto()
        }.singleOrNull()
    }

    override suspend fun getAllLibrary() = DatabaseFactory.dbQuery {
        LibraryTable.selectAll().map { result ->
            result.toLibraryDto()
        }
    }

    override suspend fun deleteUserLibrary(userId: Int) = DatabaseFactory.dbQuery {
        LibraryTable.deleteWhere { LibraryTable.userId eq userId } > 0
    }

    override suspend fun updateLibraryInfo(libraryDto: LibraryDto) = DatabaseFactory.dbQuery {
        LibraryTable.update({
            LibraryTable.userId eq libraryDto.userId and (LibraryTable.libraryId eq libraryDto.libraryId)
        }) {
            it[libraryName] = libraryDto.libraryName
            it[libraryAddress] = libraryDto.libraryAddress
            it[library_poster] = libraryDto.library_poster
            it[libraryPhone] = libraryDto.libraryPhone
        }
    } > 0

    override suspend fun checkIfUserHasLibrary(userId: Int, libraryId: Int) = DatabaseFactory.dbQuery {
        LibraryTable.select {
            LibraryTable.userId eq userId and (LibraryTable.libraryId eq libraryId)
        }.count() > 0
    }

    override suspend fun checkIfTheLibraryIsAccepted(userId: Int) = DatabaseFactory.dbQuery {
        LibraryTable.select {
            LibraryTable.userId eq userId and (LibraryTable.isAccept eq true)
        }.map { result ->
            result.toLibraryDto()
        }.singleOrNull()
    }
}