package com.example.di


import com.example.data.remote.NotificationAPI
import com.example.data.repository.*
import com.example.domain.repository.*
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
import com.example.service.notifications.NotificationService
import com.example.service.notifications.NotificationServiceImpl
import com.example.service.user.UserService
import com.example.service.user.UserServiceImpl
import com.example.utils.BASE_URL_FIREBASE
import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL_FIREBASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        get<Retrofit>().create(NotificationAPI::class.java)
    }

    single<NotificationService> {
        NotificationServiceImpl(get())
    }

    single<NotificationRepository> {
        NotificationRepositoryImpl(get())
    }

}