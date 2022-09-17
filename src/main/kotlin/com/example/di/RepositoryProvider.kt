package com.example.di


import com.example.repository.auth.AuthRepository
import com.example.repository.auth.AuthRepositoryImpl
import com.example.service.auth.AuthServiceImpl

object RepositoryProvider {

    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(AuthServiceImpl())
}