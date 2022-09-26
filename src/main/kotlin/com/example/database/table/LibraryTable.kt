package com.example.database.table

import com.example.domain.model.LibraryDto
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
    val library_poster = text("library_poster").nullable()
    val library_rate = double("library_rate").nullable()
    val libraryPhone = text("library_phone").nullable()
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(libraryId)
}

fun ResultRow?.toLibraryDto(): LibraryDto? {
    return this?.let {
        LibraryDto(
            libraryId = this[LibraryTable.libraryId],
            userId = this[LibraryTable.userId],
            libraryName = this[LibraryTable.libraryName],
            libraryAddress = this[LibraryTable.libraryAddress],
            library_poster = "$BASE_URL${this[LibraryTable.library_poster]}",
            library_rate = this[LibraryTable.library_rate],
            libraryPhone = this[LibraryTable.libraryPhone].toString(),
            createdAt = this[LibraryTable.createdAt].toString(),
        )
    } ?: return null
}