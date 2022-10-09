package com.example.service.book

import com.example.data.database.DatabaseFactory
import com.example.data.database.table.BookTable
import com.example.data.database.table.toBookDto
import com.example.domain.request.Book
import com.example.domain.response.BookId
import org.jetbrains.exposed.sql.*

class BookServiceImpl : BookService {

    override suspend fun addBook(book: Book) = DatabaseFactory.dbQuery {
        BookTable.insert {
            it[userId] = book.userId
            it[libraryId] = book.libraryId
            it[bookAuthor] = book.bookAuthor
            it[bookTitle] = book.bookTitle
            it[bookPoster] = book.bookPoster
            it[bookFile] = book.bookFile
            it[bookBackground] = book.bookBackground
            it[newPrice] = book.newPrice
            it[oldPrice] = book.oldPrice
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

    override suspend fun updateBookInfo(book: Book) = DatabaseFactory.dbQuery {
        BookTable.update({
            BookTable.userId eq book.userId and
                    (BookTable.libraryId eq book.libraryId) and
                    (BookTable.bookId eq book.bookId)
        }) {
            it[bookAuthor] = book.bookAuthor
            it[bookTitle] = book.bookTitle
            it[bookPoster] = book.bookPoster
            it[bookFile] = book.bookFile
            it[bookBackground] = book.bookBackground
            it[newPrice] = book.newPrice
            it[oldPrice] = book.oldPrice
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