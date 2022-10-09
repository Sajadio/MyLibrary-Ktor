package com.example.service.auth.admin

import com.example.domain.request.AdminCredentials
import com.example.domain.request.Admin
import com.example.domain.request.NewAdmin

interface AdminAuth {
    suspend fun adminSignUp(newAdmin: NewAdmin): Admin?
    suspend fun adminLogIn(adminCredentials: AdminCredentials): Admin?
    suspend fun findAdminByEmail(email: String): Admin?
}