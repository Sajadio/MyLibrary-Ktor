package com.example.service.user

import com.example.data.database.DatabaseFactory
import com.example.data.database.table.UserTable
import com.example.data.database.table.toUserDto
import com.example.domain.request.User
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class UserServiceImpl : UserService {

    override suspend fun getUserById(userId: Int) = DatabaseFactory.dbQuery {
        UserTable.select { UserTable.userId.eq(userId) }
            .map { result ->
                result.toUserDto()
            }.singleOrNull()
    }

    override suspend fun updateProfileUser(user: User) = DatabaseFactory.dbQuery {
        UserTable.update({ UserTable.userId eq user.userId }) {
            it[fullName] = user.fullName
            it[email] = user.email
            it[urlPhoto] = user.urlPhoto
            it[phoneNumber] = user.phoneNumber
            it[gander] = gander
            it[dateOfBirth] = dateOfBirth
        }
    } > 0

    override suspend fun updateStatusLibraryForUser(userId: Int) = DatabaseFactory.dbQuery {
        UserTable.update({ UserTable.userId eq userId }) {
            it[doHaveLibrary] = true
        }
    } > 0

}