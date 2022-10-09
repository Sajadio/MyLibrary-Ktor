package com.example.data.repository

import com.example.domain.repository.AdminRepository
import com.example.domain.request.Admin
import com.example.service.admin.AdminService
import com.example.utils.*
import io.ktor.http.*

class AdminRepositoryImpl(
    private val adminService: AdminService
) : AdminRepository {
    override suspend fun getAdminById(adminId: Int) = adminService.getAdminById(adminId)?.let { admin ->
        checkResponseStatus(
            SUCCESS,
            HttpStatusCode.OK,
            admin
        )
    } ?: checkResponseStatus(
        message = MESSAGE_ADMIN_ID,
        statusCode = HttpStatusCode.NotFound
    )
    override suspend fun updateAdminInfo(admin: Admin) =
        adminService.updateAdminInfo(admin).takeIf { it }?.run {
            checkResponseStatus(
                SUCCESS,
                HttpStatusCode.OK,
                admin
            )
        } ?: checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.NotFound
        )
    override suspend fun getAllUsers(): Response<Any> {
        val users = adminService.getAllUsers()
        return if (users.isNotEmpty()) {
            checkResponseStatus(
                SUCCESS,
                HttpStatusCode.OK,
                users
            )
        } else checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.NotFound
        )
    }
    override suspend fun getUserById(userId: Int) = adminService.getUserById(userId)?.let { user ->
        checkResponseStatus(
            SUCCESS,
            HttpStatusCode.OK,
            user
        )
    } ?: checkResponseStatus(
        message = MESSAGE_USER_ID,
        statusCode = HttpStatusCode.NotFound
    )
    override suspend fun getUserByEmail(email: String) = adminService.getUserByEmail(email)?.let { user ->
        checkResponseStatus(
            SUCCESS,
            HttpStatusCode.OK,
            user
        )
    } ?: checkResponseStatus(
        message = MESSAGE_USER_ID,
        statusCode = HttpStatusCode.NotFound
    )
    override suspend fun deleteAllUsers() = if (adminService.deleteAllUsers())
        checkResponseStatus(
            SUCCESS,
            HttpStatusCode.OK
        ) else
        checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.BadRequest
        )
    override suspend fun deleteUserById(userId: Int) = if (adminService.deleteUserById(userId))
        checkResponseStatus(
            SUCCESS,
            HttpStatusCode.OK
        ) else
        checkResponseStatus(
            message = MESSAGE_USER_ID,
            statusCode = HttpStatusCode.BadRequest
        )
    override suspend fun deleteLibraryById(libraryId: Int) = if (adminService.deleteUserById(libraryId))
        checkResponseStatus(
            SUCCESS,
            HttpStatusCode.OK
        ) else
        checkResponseStatus(
            message = MESSAGE_USER_ID,
            statusCode = HttpStatusCode.BadRequest
        )
    override suspend fun getAllLibrariesNotAccepted(): Response<Any> {
        val libraries = adminService.getAllLibrariesNotAccepted()
        return if (libraries.isNotEmpty()) {
            checkResponseStatus(
                SUCCESS,
                HttpStatusCode.OK,
                libraries
            )
        } else checkResponseStatus(
            message = EMPTY_RESULT,
            statusCode = HttpStatusCode.NotFound
        )
    }
    override suspend fun deleteAllLibraries() = if (adminService.deleteAllLibraries())
        checkResponseStatus(
            message = SUCCESS,
            statusCode = HttpStatusCode.OK
        )
    else checkResponseStatus(
        message = GENERIC_ERROR,
        statusCode = HttpStatusCode.BadRequest
    )
    override suspend fun acceptLibrary(libraryId: Int) = if (adminService.acceptLibrary(libraryId))
        checkResponseStatus(
            ACCEPT_LIBRARY,
            HttpStatusCode.OK
        )
    else
        checkResponseStatus(
            message = GENERIC_ERROR,
            statusCode = HttpStatusCode.NotFound
        )
    override suspend fun rejectLibrary(libraryId: Int) =
        if (adminService.rejectLibrary(libraryId))
            checkResponseStatus(
                REJECT_LIBRARY,
                HttpStatusCode.OK
            )
        else
            checkResponseStatus(
                message = GENERIC_ERROR,
                statusCode = HttpStatusCode.NotFound
            )
}