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
            statusCode = HttpStatusCode.BadRequest
        )

    override suspend fun getLibraryById(libraryId: Int) = libraryService.getLibraryById(libraryId)?.let { library ->
        checkResponseStatus(
            message = SUCCESS,
            statusCode = HttpStatusCode.OK,
            data = library
        )
    } ?: checkResponseStatus(
        message = EMPTY_RESULT,
        statusCode = HttpStatusCode.OK
    )

    override suspend fun getAllLibrary(): Response<Any> {
        val libraries = libraryService.getAllLibrary()
        return if (libraries.isNotEmpty())
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK,
                data = libraries
            )
        else checkResponseStatus(
            message = EMPTY_RESULT,
            statusCode = HttpStatusCode.BadRequest
        )
    }

    override suspend fun getLibraryByName(libraryName: String) =
        libraryService.getLibraryByName(libraryName)?.let { library ->
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK,
                data = library
            )
        } ?: checkResponseStatus(
            message = EMPTY_RESULT,
            statusCode = HttpStatusCode.NotFound
        )

    override suspend fun deleteUserLibrary(userId: Int) =
        if (libraryService.deleteUserLibrary(userId))
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK
            )
        else checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.BadRequest
        )

    override suspend fun updateLibraryInfo(libraryDto: LibraryDto) =
        if (libraryService.updateLibraryInfo(libraryDto))
            checkResponseStatus(
                message = SUCCESS,
                statusCode = HttpStatusCode.OK
            )
        else checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.BadRequest
        )

    override suspend fun checkIfUserHasLibrary(userId: Int, libraryId: Int) =
        libraryService.checkIfUserHasLibrary(userId, libraryId)

    override suspend fun checkIfTheLibraryIsAccepted(userId: Int) =
        libraryService.checkIfTheLibraryIsAccepted(userId)?.isAccept == true

}