package com.example.service.auth

import com.example.domain.model.NewUser
import com.example.domain.model.UserDto
import com.example.domain.model.UserCredentials


interface AuthService {
    suspend fun signUp(newUser: NewUser): UserDto?
    suspend fun signIn(userCredentials: UserCredentials): UserDto?
    suspend fun findUserByEmail(email: String): UserDto?
    suspend fun findUserById(id: Int): UserDto?
}