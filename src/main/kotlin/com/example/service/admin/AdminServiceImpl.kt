package com.example.service.admin

import com.example.data.database.DatabaseFactory
import com.example.data.database.table.*
import com.example.domain.request.Admin
import org.jetbrains.exposed.sql.*

class AdminServiceImpl : AdminService {
    override suspend fun getAdminById(adminId: Int) = DatabaseFactory.dbQuery {
        AdminTable.select { AdminTable.adminId.eq(adminId) }
            .map { result ->
                result.toAdminDto()
            }.singleOrNull()
    }
    override suspend fun getUserById(userId: Int) = DatabaseFactory.dbQuery {
        UserTable.select {
            UserTable.userId.eq(userId)
        }.map { result ->
            result.toUserDto()
        }.singleOrNull()
    }
    override suspend fun getUserByEmail(email: String) = DatabaseFactory.dbQuery {
        UserTable.select {
            UserTable.email.eq(email)
        }.map { result ->
            result.toUserDto()
        }.singleOrNull()
    }
    override suspend fun getAllUsers() = DatabaseFactory.dbQuery {
        UserTable.selectAll()
            .map { result ->
                result.toUserDto()
            }
    }
    override suspend fun getAllLibrariesNotAccepted() = DatabaseFactory.dbQuery {
        LibraryTable.select {
            LibraryTable.isAccept eq false
        }.map { result ->
            result.toLibraryDto()
        }
    }
    override suspend fun deleteAllUsers() = DatabaseFactory.dbQuery {
        UserTable.deleteAll()
    } > 0
    override suspend fun deleteUserById(userId: Int) = DatabaseFactory.dbQuery {
        UserTable.deleteWhere {
            UserTable.userId.eq(userId)
        } > 0
    }
    override suspend fun deleteLibraryById(libraryId: Int) =
        DatabaseFactory.dbQuery {
            LibraryTable.deleteWhere {
                LibraryTable.libraryId eq libraryId
            }
        } > 0
    override suspend fun deleteAllLibraries() = DatabaseFactory.dbQuery {
        LibraryTable.deleteAll() > 0
    }
    override suspend fun updateAdminInfo(admin: Admin) = DatabaseFactory.dbQuery {
        AdminTable.update({ AdminTable.adminId eq admin.adminId }) {
            it[fullName] = admin.fullName
            it[email] = admin.email
            it[phoneNumber] = admin.phoneNumber
        }
    } > 0
    override suspend fun acceptLibrary(libraryId: Int) =
        DatabaseFactory.dbQuery {
            LibraryTable.update({ LibraryTable.libraryId eq libraryId }) {
                it[isAccept] = true
            } > 0
        }
    override suspend fun rejectLibrary(libraryId: Int) =
        DatabaseFactory.dbQuery {
            LibraryTable.deleteWhere {
                LibraryTable.libraryId eq libraryId
            }
        } > 0

}