package com.example.data.repository

import com.example.domain.repository.LibraryRepository
import com.example.domain.request.Library
import com.example.service.library.LibraryService
import com.example.utils.*
import io.ktor.http.*

class LibraryRepositoryImpl(
    private val libraryService: LibraryService
) : LibraryRepository {
    override suspend fun addLibrary(library: Library) =
        if (libraryService.addLibrary(library))
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

    override suspend fun updateLibraryInfo(library: Library) =
        if (libraryService.updateLibraryInfo(library))
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