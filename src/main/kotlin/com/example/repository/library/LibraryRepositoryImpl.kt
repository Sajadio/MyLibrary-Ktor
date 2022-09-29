package com.example.repository.library

import com.example.domain.model.LibraryDto
import com.example.service.library.LibraryService
import com.example.utils.*
import io.ktor.http.*

class LibraryRepositoryImpl(
    private val libraryService: LibraryService
) : LibraryRepository {
    override suspend fun addLibrary(libraryDto: LibraryDto) =
        if (libraryService.addLibrary(libraryDto))
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK
            )
        else checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.NotFound
        )


    override suspend fun deleteLibrary(libraryId: Int) =
        if (libraryService.deleteLibrary(libraryId))
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK
            )
        else checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.NotFound
        )

    override suspend fun getLibraryById(libraryId: Int) = libraryService.getLibraryById(libraryId)?.let { library ->
        checkResponseStatus(
            message = SUCCESS,
            statusCode = HttpStatusCode.OK,
            data = library
        )
    } ?: checkResponseStatus(
        message = MESSAGE_LIBRARY_NAME,
        statusCode = HttpStatusCode.NotFound
    )

    override suspend fun getAllLibrary(): Response<Any> {
        val libraries = libraryService.getAllLibrary()
        return if (libraries.isNotEmpty()) {
            checkResponseStatus(
                SUCCESS,
                HttpStatusCode.OK,
                libraries
            )
        } else checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.NotFound
        )
    }

    override suspend fun findLibraryByName(libraryName: String) =
        libraryService.findLibraryByName(libraryName)?.let { library ->
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK,
                data = library
            )
        } ?: checkResponseStatus(
            message = MESSAGE_LIBRARY_NAME,
            statusCode = HttpStatusCode.NotFound
        )

    override suspend fun updateLibraryInfo(libraryDto: LibraryDto) =
        if (libraryService.updateLibraryInfo(libraryDto))
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK
            )
        else checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.NotFound
        )

    override suspend fun checkIfUserHasLibrary(userId: Int) = libraryService.checkIfUserHasLibrary(userId)
}