package com.example.service.auth.user

import com.example.domain.request.NewUser
import com.example.domain.request.UserCredentials
import com.example.domain.request.User

interface UserAuth {
    suspend fun userSignUp(newUser: NewUser): User?
    suspend fun userLogIn(userCredentials: UserCredentials): User?
    suspend fun findUserByEmail(email: String): User?
}