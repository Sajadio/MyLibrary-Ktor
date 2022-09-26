package com.example.service.admin

import com.example.database.DatabaseFactory
import com.example.database.table.AdminTable
import com.example.database.table.UserTable
import com.example.database.table.toAdminDto
import com.example.database.table.toUserDto
import com.example.utils.response.Admin
import org.jetbrains.exposed.sql.*

class AdminServiceImpl : AdminService {
    override suspend fun getAdminById(adminId: Int) = DatabaseFactory.dbQuery {
        AdminTable.select { AdminTable.adminId.eq(adminId) }
            .map { result ->
                result.toAdminDto()
            }.singleOrNull()
    }


    override suspend fun updateAdminInfo(admin: Admin, adminId: Int) = DatabaseFactory.dbQuery {
        AdminTable.update({ AdminTable.adminId eq adminId }) {
            it[fullName] = admin.fullName
            it[email] = admin.email
            it[phoneNumber] = admin.phoneNumber
        }
    } > 0

    override suspend fun getAllUsers() = DatabaseFactory.dbQuery {
        UserTable.selectAll()
            .map { result ->
                result.toUserDto()
            }
    }


    override suspend fun getUserById(userId: Int) = DatabaseFactory.dbQuery {
        UserTable.select {
            UserTable.userId.eq(userId)
        }.map { result ->
            result.toUserDto()
        }.singleOrNull()
    }


    override suspend fun findUserByEmail(email: String) = DatabaseFactory.dbQuery {
        UserTable.select {
            UserTable.email.eq(email)
        }.map { result ->
            result.toUserDto()
        }.singleOrNull()
    }

    override suspend fun deleteAllUsers() = DatabaseFactory.dbQuery {
        UserTable.deleteAll()
    } > 0

    override suspend fun deleteUserById(userId: Int) = DatabaseFactory.dbQuery {
        UserTable.deleteWhere {
            UserTable.userId.eq(userId)
        } > 0
    }
}