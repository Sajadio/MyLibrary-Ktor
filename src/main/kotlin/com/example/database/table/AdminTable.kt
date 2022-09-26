package com.example.database.table

import com.example.domain.model.AdminDto
import com.example.domain.model.UserDto
import com.example.utils.BASE_URL
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object AdminTable : Table("admins") {
    val adminId = integer("admin_id").autoIncrement()
    val fullName = varchar("full_name", 256).nullable()
    val urlPhoto = text("path_photo").nullable()
    val email = varchar("email", 256).nullable()
    val password = text("password").nullable()
    val phoneNumber = text("phone_number").nullable()
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(adminId)
}

fun ResultRow?.toAdminDto(): AdminDto? {
    return this?.let {
        AdminDto(
            adminId = this[AdminTable.adminId],
            fullName = this[AdminTable.fullName],
            urlPhoto = "$BASE_URL${this[AdminTable.urlPhoto]}",
            email = this[AdminTable.email],
            password = this[AdminTable.password].toString(),
            phoneNumber = this[AdminTable.phoneNumber],
            createdAt = this[AdminTable.createdAt].toString(),
        )
    } ?: return null
}
