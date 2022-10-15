package com.example.data.database.table

import com.example.domain.request.Library
import com.example.utils.BASE_URL
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object LibraryTable : Table("library") {
    val libraryId = integer("library_id").autoIncrement()
    val userId = integer("user_id").references(ref = UserTable.userId, onDelete = ReferenceOption.CASCADE)
    val libraryName = varchar("library_name", 256).nullable()
    val libraryAddress = varchar("library_address", 256).nullable()
    val libraryImage = text("library_image").nullable()
    val library_rate = double("library_rate").nullable().default(0.0)
    val libraryPhone = text("library_phone").nullable()
    val isAccept = bool("is_accept").default(false)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(libraryId)
}

fun ResultRow?.toLibraryDto(): Library? {
    return this?.let {
        Library(
            libraryId = this[LibraryTable.libraryId],
            userId = this[LibraryTable.userId],
            libraryName = this[LibraryTable.libraryName],
            libraryAddress = this[LibraryTable.libraryAddress],
            libraryImage = "$BASE_URL${this[LibraryTable.libraryImage]}",
            library_rate = this[LibraryTable.library_rate],
            libraryPhone = this[LibraryTable.libraryPhone].toString(),
            isAccept = this[LibraryTable.isAccept],
            createdAt = this[LibraryTable.createdAt].toString(),
        )
    } ?: return null
}