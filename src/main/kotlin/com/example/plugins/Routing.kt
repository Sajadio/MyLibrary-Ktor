package com.example.plugins

import com.example.di.appModule
import com.example.repository.admin.AdminRepository
import com.example.repository.auth.AuthRepository
import com.example.repository.book.BookRepository
import com.example.repository.library.LibraryRepository
import com.example.repository.user.UserRepository
import com.example.routes.admin.*
import com.example.routes.auth.login
import com.example.routes.auth.signUp
import com.example.routes.book.*
import com.example.routes.library.*
import com.example.routes.user.getUserInfo
import com.example.routes.user.updateUserInfo
import com.google.gson.Gson
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun Application.configureRouting() {
    install(Koin) {
        modules(appModule)
    }
    routing {
        route("/auth") {
            val repository by inject<AuthRepository>()
            signUp(repository)
            login(repository)
        }

        val gson: Gson by inject()
        val libraryRepo by inject<LibraryRepository>()
        val userRepo by inject<UserRepository>()
        val bookRepo by inject<BookRepository>()
        route("/user") {
            authenticate("auth-user") {
                getUserInfo(userRepo)
                updateUserInfo(userRepo, gson)
                addLibrary(libraryRepo)
                updateLibraryInfo(libraryRepo)
                addBook(bookRepo,libraryRepo)
                deleteUserLibrary(libraryRepo)
                deleteBookById(bookRepo)
                deleteAllBooks(bookRepo)
                updateBookInfo(bookRepo)
            }
            getLibraryById(libraryRepo)
            getAllLibrary(libraryRepo)
            getBookByTitle(bookRepo)
            getBookById(bookRepo)
            getAllBooks(bookRepo)
        }

        route("/admin") {
            authenticate("auth-admin") {
                val adminRepo by inject<AdminRepository>()
                getAdminInfo(adminRepo)
                getAllUser(adminRepo)
                deleteAllUser(adminRepo)
                deleteUserById(adminRepo)
                deleteLibraryById(adminRepo)
                getUserByEmail(adminRepo)
                updateAdminInfo(adminRepo, gson)
                getAllLibrariesNotAccepted(adminRepo)
                deleteAllLibraries(adminRepo)
                acceptLibrary(adminRepo, userRepo, libraryRepo)
                rejectLibrary(adminRepo)
            }
        }
    }
}
