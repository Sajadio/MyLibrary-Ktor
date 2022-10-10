package com.example.data.database.table

import com.example.domain.request.User
import com.example.utils.BASE_URL
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserTable : Table("users") {
    val userId = integer("user_id").autoIncrement()
    val fullName = varchar("full_name", 256).nullable()
    val imageURI = text("image_uri").nullable()
    val email = varchar("email", 256).nullable()
    val password = text("password").nullable()
    val phoneNumber = text("phone_number").default("0").nullable()
    val gander = varchar("gander", 256).nullable()
    val dateOfBirth = varchar("date_of_birth", 256).default("00/00/0000").nullable()
    val doHaveLibrary = bool("doHaveLibrary").default(false)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(userId)
}

fun ResultRow?.toUserDto(): User? {
    return this?.let {
        User(
            userId = this[UserTable.userId],
            fullName = this[UserTable.fullName],
            imageURL = "$BASE_URL${this[UserTable.imageURI]}",
            email = this[UserTable.email],
            phoneNumber = this[UserTable.phoneNumber],
            gander = this[UserTable.gander],
            dateOfBirth = this[UserTable.dateOfBirth],
            doHaveLibrary = this[UserTable.doHaveLibrary],
            createdAt = this[UserTable.createdAt].toString(),
        )
    } ?: return null
}