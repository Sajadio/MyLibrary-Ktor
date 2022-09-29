package com.example.data.mapper.implement

import com.example.data.mapper.Mapper
import com.example.domain.model.BookDto
import com.example.domain.response.Book

object BookBodyMapper : Mapper<BookDto, Book> {

    override fun mapTo(input: BookDto) = Book(
        bookId = input.bookId,
        userId = input.userId,
        libraryId = input.libraryId,
        bookAuthor = input.bookAuthor,
        bookTitle = input.bookTitle,
        bookPoster = input.bookPoster,
        bookFile = input.bookFile,
        bookBackground = input.bookBackground,
        newPrice = input.newPrice,
        oldPrice = input.oldPrice,
        createdAt = input.createdAt,
    )
}