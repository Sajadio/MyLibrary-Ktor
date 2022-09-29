package com.example.data.mapper.implement

import com.example.data.mapper.Mapper
import com.example.domain.model.LibraryDto
import com.example.domain.response.Library

object LibraryBodyMapper : Mapper<LibraryDto, Library> {
    override fun mapTo(input: LibraryDto): Library {
        return Library(
            libraryId = input.libraryId,
            userId = input.userId,
            libraryName = input.libraryName,
            libraryAddress = input.libraryAddress,
            library_poster = input.library_poster,
            library_rate = input.library_rate,
            libraryPhone = input.libraryPhone,
            isVerify = input.isAccept,
            createdAt = input.createdAt
        )
    }
}