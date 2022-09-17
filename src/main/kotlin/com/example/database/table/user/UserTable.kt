package com.example.database.table.user

import com.example.domain.model.UserDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserTable : Table("users") {
    val userId = integer("id").autoIncrement()
    val fullName = varchar("full_name", 256)
    val photo = text("photo")
    val email = varchar("email", 256)
    val password = text("password")
    val phoneNumber = integer("phoneNumber")
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(userId)
}

fun ResultRow?.toUserDto(): UserDto? {
    return this?.let {
        UserDto(
            userId = this[UserTable.userId],
            fullName = this[UserTable.fullName],
            photo = this[UserTable.photo],
            email = this[UserTable.email],
            password = this[UserTable.password].toString(),
            phoneNumber = this[UserTable.phoneNumber],
            createdAt = this[UserTable.createdAt].toString(),
        )
    } ?: return null
}
