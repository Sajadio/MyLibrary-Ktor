package com.example.service.user

import com.example.database.DatabaseFactory
import com.example.database.table.UserTable
import com.example.database.table.toUserDto
import com.example.domain.model.UserDto
import com.example.utils.response.User
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class UserServiceImpl : UserService {

    override suspend fun getUserById(userId: Int): UserDto? {
        return DatabaseFactory.dbQuery {
            UserTable.select { UserTable.userId.eq(userId) }
                .map { result ->
                    result.toUserDto()
                }.singleOrNull()
        }
    }

    override suspend fun updateProfileUser(user: User, userId: Int): Boolean {
        var result = 0
        DatabaseFactory.dbQuery {
            result = UserTable.update({ UserTable.userId eq userId }) {
                it[fullName] = user.fullName
                it[email] = user.email
                it[phoneNumber] = user.phoneNumber
            }
        }
        return result == 1
    }


}