package com.example.di

import com.example.domain.repository.AdminRepository
import com.example.data.repository.AdminRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.data.repository.AuthRepositoryImpl
import com.example.domain.repository.BookRepository
import com.example.data.repository.BookRepositoryImpl
import com.example.domain.repository.LibraryRepository
import com.example.data.repository.LibraryRepositoryImpl
import com.example.domain.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import com.example.service.admin.AdminService
import com.example.service.admin.AdminServiceImpl
import com.example.service.auth.admin.AdminAuth
import com.example.service.auth.admin.AdminAuthImpl
import com.example.service.auth.user.UserAuth
import com.example.service.auth.user.UserAuthImpl
import com.example.service.book.BookService
import com.example.service.book.BookServiceImpl
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

    single<BookService> {
        BookServiceImpl()
    }
    single<BookRepository> {
        BookRepositoryImpl(get())
    }
}