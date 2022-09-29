package com.example.data.mapper.implement

import com.example.data.mapper.Mapper
import com.example.domain.model.BookDto
import com.example.domain.response.Book

object BooksBodyMapper : Mapper<List<BookDto>, List<Book>> {
    override fun mapTo(input: List<BookDto>): List<Book> {
        val books = mutableListOf<Book>()
        input.forEach {
            books.add(
                Book(
                    bookId = it.bookId,
                    userId = it.userId,
                    libraryId = it.libraryId,
                    bookAuthor = it.bookAuthor,
                    bookTitle = it.bookTitle,
                    bookPoster = it.bookPoster,
                    bookFile = it.bookFile,
                    bookBackground = it.bookBackground,
                    newPrice = it.newPrice,
                    oldPrice = it.oldPrice,
                    createdAt = it.createdAt,
                )
            )
        }
        return books
    }
}