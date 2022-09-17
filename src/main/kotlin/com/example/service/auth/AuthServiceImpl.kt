package com.example.service.auth

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.table.user.UserTable
import com.example.database.table.user.toUserDto
import com.example.domain.model.NewUser
import com.example.domain.model.UserDto
import com.example.domain.model.UserCredentials
import com.example.security.hashPWS
import org.jetbrains.exposed.sql.*

class AuthServiceImpl : AuthService {
    override suspend fun signUp(newUser: NewUser): UserDto? {
        return dbQuery {
            UserTable.insert {
                it[fullName] = newUser.fullName
                it[photo] = ""
                it[email] = newUser.email
                it[password] = hashPWS(newUser.password)
                it[phoneNumber] = 0
            }.resultedValues?.map { result ->
                result.toUserDto()
            }?.singleOrNull()
        }
    }

    override suspend fun findUserByEmail(email: String): UserDto? {
       val userDto =  dbQuery {
            UserTable.select { UserTable.email.eq(email) }.map { result ->
                    result.toUserDto()
                }.singleOrNull()
        }
        return userDto
    }

    override suspend fun signIn(userCredentials: UserCredentials): UserDto? {
        val hashedPassword = hashPWS(userCredentials.password)
        val userRow = dbQuery {
            UserTable.select { UserTable.email eq userCredentials.email and (UserTable.password eq hashedPassword) }
                .firstOrNull()
        }
        return userRow.toUserDto()
    }

    override suspend fun findUserById(id: Int): UserDto? {
        return dbQuery {
            UserTable.select { UserTable.userId.eq(id) }
                .map { result ->
                    result.toUserDto()
                }.singleOrNull()
        }
    }
}