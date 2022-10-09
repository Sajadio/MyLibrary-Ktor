package com.example.service.auth.user

import com.example.data.database.DatabaseFactory
import com.example.data.database.table.UserTable
import com.example.data.database.table.toUserDto
import com.example.domain.request.NewUser
import com.example.domain.request.UserCredentials
import com.example.security.hashPWS
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserAuthImpl : UserAuth {
    override suspend fun userSignUp(newUser: NewUser) = DatabaseFactory.dbQuery {
        UserTable.insert {
            it[fullName] = newUser.fullName
            it[email] = newUser.email
            it[password] = hashPWS(newUser.password)
        }.resultedValues?.map { result ->
            result.toUserDto()
        }?.singleOrNull()
    }

    override suspend fun userLogIn(userCredentials: UserCredentials) =
        DatabaseFactory.dbQuery {
            UserTable.select {
                UserTable.email eq userCredentials.email and
                        (UserTable.password eq hashPWS(userCredentials.password))
            }.firstOrNull()
        }.toUserDto()

    override suspend fun findUserByEmail(email: String) = DatabaseFactory.dbQuery {
        UserTable.select { UserTable.email.eq(email) }.map { result ->
            result.toUserDto()
        }.singleOrNull()
    }
}