package com.example.routes.admin

import com.example.data.mapper.implement.LibraryBodyMapper
import com.example.domain.model.LibraryDto
import com.example.domain.response.AdminResponse
import com.example.domain.response.LibraryResponse
import com.example.repository.admin.AdminRepository
import com.example.repository.library.LibraryRepository
import com.example.repository.user.UserRepository
import com.example.routes.adminId
import com.example.utils.ERROR
import com.example.utils.INVALID_AUTHENTICATION_TOKEN
import com.example.utils.OK
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.acceptLibrary(
    adminRepo: AdminRepository,
    userRepo: UserRepository,
    libraryRepo: LibraryRepository
) {
    put("library/accept") {
        try {

            if (call.adminId.isEmpty()) {
                call.respond(
                    HttpStatusCode.BadRequest, LibraryResponse(
                        status = ERROR,
                        message = INVALID_AUTHENTICATION_TOKEN
                    )
                )
                return@put
            }

            val libraryId = call.request.queryParameters["libraryId"]?.toIntOrNull()
            libraryId?.let {
                when (val result = adminRepo.acceptLibrary(libraryId)) {
                    is Response.SuccessResponse -> {

                        updateStatusLibraryForUser(libraryRepo, userRepo, libraryId)

                        call.respond(
                            result.statusCode, LibraryResponse(
                                status = OK,
                                message = result.message
                            )
                        )
                    }

                    is Response.ErrorResponse -> {
                        call.respond(
                            result.statusCode, LibraryResponse(
                                status = ERROR,
                                message = result.message,
                            )
                        )
                    }
                }
            } ?: call.respond(
                HttpStatusCode.InternalServerError, LibraryResponse(
                    status = ERROR,
                    message = "The library id is null",
                )
            )

        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest, AdminResponse(
                    status = ERROR,
                    message = e.message
                )
            )
        }
    }
}

suspend fun updateStatusLibraryForUser(libraryRepo: LibraryRepository, userRepo: UserRepository, libraryId: Int?) {
    val result = libraryRepo.getLibraryById(libraryId!!)
    if (result is Response.SuccessResponse) {
        val library = LibraryBodyMapper.mapTo(result.data as LibraryDto)
        userRepo.updateStatusLibraryForUser(library.userId)
    }
}