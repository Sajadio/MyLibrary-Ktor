package com.example.service.user

import com.example.database.DatabaseFactory
import com.example.database.table.UserTable
import com.example.database.table.toUserDto
import com.example.domain.model.UserDto
import com.example.domain.response.User
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class UserServiceImpl : UserService {

    override suspend fun getUserById(userId: Int) = DatabaseFactory.dbQuery {
        UserTable.select { UserTable.userId.eq(userId) }
            .map { result ->
                result.toUserDto()
            }.singleOrNull()
    }

    override suspend fun updateProfileUser(user: User, userId: Int) = DatabaseFactory.dbQuery {
        UserTable.update({ UserTable.userId eq userId }) {
            it[fullName] = user.fullName
            it[email] = user.email
            it[phoneNumber] = user.phoneNumber
        }
    } > 0

    override suspend fun updateStatusLibraryForUser(userId: Int) = DatabaseFactory.dbQuery {
        UserTable.update({ UserTable.userId eq userId }) {
            it[doHaveLibrary] = true
        }
    } > 0

}