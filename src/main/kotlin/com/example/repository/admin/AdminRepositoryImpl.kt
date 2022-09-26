package com.example.repository.admin

import com.example.service.admin.AdminService
import com.example.utils.*
import com.example.utils.response.Admin
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

    override suspend fun updateAdminInfo(admin: Admin, adminId: Int) =
        adminService.updateAdminInfo(admin, adminId).takeIf { it }?.run {
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

    override suspend fun findUserByEmail(email: String) = adminService.findUserByEmail(email)?.let { user ->
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
            statusCode = HttpStatusCode.NotFound
        )

    override suspend fun deleteUserById(userId: Int) = if (adminService.deleteUserById(userId))
        checkResponseStatus(
            SUCCESS,
            HttpStatusCode.OK
        ) else
        checkResponseStatus(
            message = MESSAGE_USER_ID,
            statusCode = HttpStatusCode.NotFound
        )
}