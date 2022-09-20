package com.example.di

import com.example.repository.auth.AuthRepository
import com.example.repository.auth.AuthRepositoryImpl
import com.example.service.auth.AuthService
import com.example.service.auth.AuthServiceImpl
import org.koin.dsl.module


val appModule = module {
    single<AuthService> {
        AuthServiceImpl()
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
}