package com.example.service.auth

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.table.AdminTable
import com.example.database.table.UserTable
import com.example.database.table.toAdminDto
import com.example.database.table.toUserDto
import com.example.domain.model.*
import com.example.security.hashPWS
import org.jetbrains.exposed.sql.*

class AuthServiceImpl : AuthService {
    override suspend fun userSignUp(newUser: NewUser) = dbQuery {
        UserTable.insert {
            it[fullName] = newUser.fullName
            it[urlPhoto] = ""
            it[email] = newUser.email
            it[password] = hashPWS(newUser.password)
            it[phoneNumber] = ""
        }.resultedValues?.map { result ->
            result.toUserDto()
        }?.singleOrNull()
    }

    override suspend fun userLogIn(userCredentials: UserCredentials): UserDto? {
        val hashedPassword = hashPWS(userCredentials.password)
        val userRow = dbQuery {
            UserTable.select { UserTable.email eq userCredentials.email and (UserTable.password eq hashedPassword) }
                .firstOrNull()
        }
        return userRow.toUserDto()
    }

    override suspend fun findUserByEmail(email: String) = dbQuery {
        UserTable.select { UserTable.email.eq(email) }.map { result ->
            result.toUserDto()
        }.singleOrNull()
    }

    override suspend fun adminSignUp(newAdmin: NewAdmin) = dbQuery {
        AdminTable.insert {
            it[fullName] = newAdmin.fullName
            it[urlPhoto] = ""
            it[email] = newAdmin.email
            it[password] = hashPWS(newAdmin.password)
            it[phoneNumber] = ""
        }.resultedValues?.map { result ->
            result.toAdminDto()
        }?.singleOrNull()
    }

    override suspend fun adminLogIn(adminCredentials: AdminCredentials): AdminDto? {
        val hashedPassword = hashPWS(adminCredentials.password)
        val adminRow = dbQuery {
            AdminTable.select {
                AdminTable.email eq
                        adminCredentials.email and
                        (AdminTable.password eq hashedPassword)
            }
                .firstOrNull()
        }
        return adminRow.toAdminDto()
    }

    override suspend fun findAdminByEmail(email: String) = dbQuery {
        AdminTable.select { AdminTable.email.eq(email) }.map { result ->
            result.toAdminDto()
        }.singleOrNull()
    }


}