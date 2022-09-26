package com.example.service.auth

import com.example.domain.model.*


interface AuthService {
    suspend fun userSignUp(newUser: NewUser): UserDto?
    suspend fun userLogIn(userCredentials: UserCredentials): UserDto?
    suspend fun findUserByEmail(email: String): UserDto?

    suspend fun adminSignUp(newAdmin: NewAdmin): AdminDto?
    suspend fun adminLogIn(adminCredentials: AdminCredentials): AdminDto?
    suspend fun findAdminByEmail(email: String): AdminDto?
}