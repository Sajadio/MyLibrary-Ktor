package com.example.di

import com.example.repository.admin.AdminRepository
import com.example.repository.admin.AdminRepositoryImpl
import com.example.repository.auth.AuthRepository
import com.example.repository.auth.AuthRepositoryImpl
import com.example.repository.library.LibraryRepository
import com.example.repository.library.LibraryRepositoryImpl
import com.example.repository.user.UserRepository
import com.example.repository.user.UserRepositoryImpl
import com.example.service.admin.AdminService
import com.example.service.admin.AdminServiceImpl
import com.example.service.auth.admin.AdminAuth
import com.example.service.auth.admin.AdminAuthImpl
import com.example.service.auth.user.UserAuth
import com.example.service.auth.user.UserAuthImpl
import com.example.service.library.LibraryService
import com.example.service.library.LibraryServiceImpl
import com.example.service.user.UserService
import com.example.service.user.UserServiceImpl
import com.google.gson.Gson
import org.koin.dsl.module


val appModule = module {
    single<AdminAuth> {
        AdminAuthImpl()
    }
    single<UserAuth> {
        UserAuthImpl()
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get(), get())
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

    single<LibraryService> {
        LibraryServiceImpl()
    }
    single<LibraryRepository> {
        LibraryRepositoryImpl(get())
    }
    single { Gson() }


}