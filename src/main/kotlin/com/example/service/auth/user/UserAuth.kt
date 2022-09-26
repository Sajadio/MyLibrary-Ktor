package com.example.service.auth.user

import com.example.domain.model.NewUser
import com.example.domain.model.UserCredentials
import com.example.domain.model.UserDto

interface UserAuth {
    suspend fun userSignUp(newUser: NewUser): UserDto?
    suspend fun userLogIn(userCredentials: UserCredentials): UserDto?
    suspend fun findUserByEmail(email: String): UserDto?
}