package com.example.repository.auth

import com.example.domain.model.NewUser
import com.example.domain.model.UserCredentials
import com.example.domain.model.UserDto
import com.example.utils.Response

interface AuthRepository {
    suspend fun signUp(newUser: NewUser): Response<Any>
    suspend fun signIn(userCredentials: UserCredentials): Response<Any>
    suspend fun findUserByEmail(email: String): UserDto?
    suspend fun findUserById(id: Int): Response<Any>
}