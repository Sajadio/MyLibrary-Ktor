package com.example.data.mapper.implement

import com.example.data.mapper.Mapper
import com.example.domain.model.LibraryDto
import com.example.domain.response.Library

object LibrariesBodyMapper : Mapper<List<LibraryDto>, List<Library>> {

    override fun mapTo(input: List<LibraryDto>): List<Library> {
        val libraries = mutableListOf<Library>()
        input.forEach {
            libraries.add(
                Library(
                    libraryId = it.libraryId,
                    userId = it.userId,
                    libraryName = it.libraryName,
                    libraryAddress = it.libraryAddress,
                    library_poster = it.library_poster,
                    library_rate = it.library_rate,
                    libraryPhone = it.libraryPhone,
                    isVerify = it.isAccept,
                    createdAt = it.createdAt
                )
            )
        }
        return libraries
    }
}