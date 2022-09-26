package com.example.service.auth.admin

import com.example.domain.model.AdminCredentials
import com.example.domain.model.AdminDto
import com.example.domain.model.NewAdmin

interface AdminAuth {
    suspend fun adminSignUp(newAdmin: NewAdmin): AdminDto?
    suspend fun adminLogIn(adminCredentials: AdminCredentials): AdminDto?
    suspend fun findAdminByEmail(email: String): AdminDto?
}