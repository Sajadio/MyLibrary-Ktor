package com.example.domain.repository

import com.example.domain.request.*
import com.example.utils.Response

interface AuthRepository {
    suspend fun userSignUp(newUser: NewUser): Response<Any>
    suspend fun userLogIn(userCredentials: UserCredentials): Response<Any>
    suspend fun findUserByEmail(email: String): User?

    suspend fun adminSignUp(newAdmin: NewAdmin): Response<Any>
    suspend fun adminLogIn(adminCredentials: AdminCredentials): Response<Any>
    suspend fun findAdminByEmail(email: String): Admin?
}