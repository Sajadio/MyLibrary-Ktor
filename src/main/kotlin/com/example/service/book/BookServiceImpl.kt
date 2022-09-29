package com.example.service.book

import com.example.database.DatabaseFactory
import com.example.database.table.BookTable
import com.example.database.table.toBookDto
import com.example.domain.model.BookDto
import com.example.domain.response.BookId
import org.jetbrains.exposed.sql.*

class BookServiceImpl : BookService {

    override suspend fun addBook(bookDto: BookDto) = DatabaseFactory.dbQuery {
        BookTable.insert {
            it[userId] = bookDto.userId
            it[libraryId] = bookDto.libraryId
            it[bookAuthor] = bookDto.bookAuthor
            it[bookTitle] = bookDto.bookTitle
            it[bookPoster] = bookDto.bookPoster
            it[bookFile] = bookDto.bookFile
            it[bookBackground] = bookDto.bookBackground
            it[newPrice] = bookDto.newPrice
            it[oldPrice] = bookDto.oldPrice
        }.insertedCount > 0
    }

    override suspend fun getAllBooks() = DatabaseFactory.dbQuery {
        BookTable.selectAll().map { result ->
            result.toBookDto()
        }
    }

    override suspend fun getBookByTitle(titleBook: String) = DatabaseFactory.dbQuery {
        BookTable.select {
            BookTable.bookTitle like titleBook or (BookTable.bookTitle eq titleBook)
        }.toList()
    }

    override suspend fun getBookById(bookId: Int) = DatabaseFactory.dbQuery {
        BookTable.select {
            BookTable.bookId eq bookId
        }.map { result ->
            result.toBookDto()
        }.singleOrNull()
    }

    override suspend fun updateBookInfo(bookDto: BookDto) = DatabaseFactory.dbQuery {
        BookTable.update({
            BookTable.userId eq bookDto.userId and
                    (BookTable.libraryId eq bookDto.libraryId) and
                    (BookTable.bookId eq bookDto.bookId)
        }) {
            it[bookAuthor] = bookDto.bookAuthor
            it[bookTitle] = bookDto.bookTitle
            it[bookPoster] = bookDto.bookPoster
            it[bookFile] = bookDto.bookFile
            it[bookBackground] = bookDto.bookBackground
            it[newPrice] = bookDto.newPrice
            it[oldPrice] = bookDto.oldPrice
        }
    } > 0

    override suspend fun deleteBookById(bookId: Int, userId: Int) = DatabaseFactory.dbQuery {
        BookTable.deleteWhere {
            BookTable.userId eq userId and (BookTable.bookId eq bookId)
        } > 0
    }

    override suspend fun deleteAllBooks(booksId: BookId): Boolean {
        var result = 0
        DatabaseFactory.dbQuery {
            booksId.bookId.forEach { id ->
                result = BookTable.deleteWhere {
                    BookTable.userId eq booksId.userId and (BookTable.bookId eq id)
                }
            }
        }
        return result > 0
    }

}