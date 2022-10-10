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

    override suspend fun updateProfileUser(user: User,userId: Int) = DatabaseFactory.dbQuery {
        UserTable.update({ UserTable.userId eq userId }) {
            it[fullName] = user.fullName
            it[email] = user.email
            it[phoneNumber] = user.phoneNumber
            it[gander] = user.gander
            it[dateOfBirth] = user.dateOfBirth
        }
    } > 0

    override suspend fun updateStatusLibraryForUser(userId: Int) = DatabaseFactory.dbQuery {
        UserTable.update({ UserTable.userId eq userId }) {
            it[doHaveLibrary] = true
        }
    } > 0

    override suspend fun isTheSameImage(imageURI: String) =
        DatabaseFactory.dbQuery {
            UserTable.select { UserTable.imageURI.eq(imageURI) }.count() > 0
        }

    override suspend fun updateProfileImage(imageURi: String, userId: Int) =
        DatabaseFactory.dbQuery {
        UserTable.update({ UserTable.userId eq userId }) {
            it[imageURI] = imageURi
        }
    } > 0



}