package com.example.service.library

import com.example.data.database.DatabaseFactory
import com.example.data.database.table.LibraryTable
import com.example.data.database.table.toLibraryDto
import com.example.domain.request.Library
import org.jetbrains.exposed.sql.*

class LibraryServiceImpl : LibraryService {

    override suspend fun addLibrary(library: Library) = DatabaseFactory.dbQuery {
        LibraryTable.insert {
            it[userId] = library.userId
            it[libraryName] = library.libraryName
            it[libraryAddress] = library.libraryAddress
            it[library_poster] = library.library_poster
            it[libraryPhone] = library.libraryPhone
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

    override suspend fun updateLibraryInfo(library: Library) = DatabaseFactory.dbQuery {
        LibraryTable.update({
            LibraryTable.userId eq library.userId and (LibraryTable.libraryId eq library.libraryId)
        }) {
            it[libraryName] = library.libraryName
            it[libraryAddress] = library.libraryAddress
            it[library_poster] = library.library_poster
            it[libraryPhone] = library.libraryPhone
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