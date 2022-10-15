package com.example.data.database.table

import com.example.domain.request.Admin
import com.example.utils.BASE_URL
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object AdminTable : Table("admins") {
    val adminId = integer("admin_id").autoIncrement()
    val fullName = varchar("full_name", 256).nullable()
    val imageURI = text("image_uri").nullable()
    val email = varchar("email", 256).nullable()
    val password = text("password").nullable()
    val firebaseToken = text("firebase_token").nullable()
    val phoneNumber = text("phone_number").default("0").nullable()
    val gander = varchar("gander", 256).nullable()
    val dateOfBirth = varchar("date_of_birth", 256).default("00/00/0000").nullable()
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(adminId)
}

fun ResultRow?.toAdminDto(): Admin? {
    return this?.let {
        Admin(
            adminId = this[AdminTable.adminId],
            fullName = this[AdminTable.fullName],
            imageURL = "$BASE_URL${this[AdminTable.imageURI]}",
            email = this[AdminTable.email],
            phoneNumber = this[AdminTable.phoneNumber],
            createdAt = this[AdminTable.createdAt].toString(),
        )
    } ?: return null
}
