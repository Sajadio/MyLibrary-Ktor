package com.example.di

import com.example.repository.admin.AdminRepository
import com.example.repository.admin.AdminRepositoryImpl
import com.example.repository.auth.AuthRepository
import com.example.repository.auth.AuthRepositoryImpl
import com.example.repository.user.UserRepository
import com.example.repository.user.UserRepositoryImpl
import com.example.service.admin.AdminService
import com.example.service.admin.AdminServiceImpl
import com.example.service.auth.AuthService
import com.example.service.auth.AuthServiceImpl
import com.example.service.user.UserService
import com.example.service.user.UserServiceImpl
import com.google.gson.Gson
import org.koin.dsl.module


val appModule = module {
    single<AuthService> {
        AuthServiceImpl()
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
    single<UserService> {
        UserServiceImpl()
    }
    single<UserRepository> {
        UserRepositoryImpl(get())
    }

    single<AdminService> {
        AdminServiceImpl()
    }
    single<AdminRepository> {
        AdminRepositoryImpl(get())
    }

    single { Gson() }


}