package com.example.database.table

import com.example.domain.model.BookDto
import com.example.utils.BASE_URL
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object BookTable : Table("book") {
    val bookId = integer("book_id").autoIncrement()
    val userId = integer("user_id").references(ref = UserTable.userId, onDelete = ReferenceOption.CASCADE)
    val libraryId = integer("library_id").references(ref = LibraryTable.libraryId, onDelete = ReferenceOption.CASCADE)
    val bookAuthor = varchar("book_author", 256).nullable()
    val bookTitle = varchar("book_title", 256).nullable()
    val bookPoster = text("book_poster").nullable()
    val bookFile = text("book_file").nullable()
    val bookBackground = integer("book_background").default(0).nullable()
    val newPrice = double("new_price").nullable()
    val oldPrice = double("old_price").default(0.0).nullable()
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(bookId)
}

fun ResultRow?.toBookDto(): BookDto? {
    return this?.let {
        BookDto(
            userId = this[BookTable.userId],
            libraryId = this[BookTable.libraryId],
            bookAuthor = this[BookTable.bookAuthor],
            bookTitle = this[BookTable.bookTitle],
            bookPoster = "$BASE_URL${this[BookTable.bookPoster]}",
            bookFile = "$BASE_URL${this[BookTable.bookFile]}",
            bookBackground = this[BookTable.bookBackground],
            newPrice = this[BookTable.newPrice],
            oldPrice = this[BookTable.oldPrice],
            createdAt = this[BookTable.createdAt].toString(),
        )
    } ?: return null
}