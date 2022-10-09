package com.example.plugins

import com.example.di.appModule
import com.example.domain.repository.AdminRepository
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.BookRepository
import com.example.domain.repository.LibraryRepository
import com.example.domain.repository.UserRepository
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
import io.ktor.server.http.content.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun Application.configureRouting() {
    install(Koin) {
        modules(appModule)
    }
    routing {
        val repository by inject<AuthRepository>()
        signUp(repository)
        login(repository)

        val gson: Gson by inject()
        val libraryRepo by inject<LibraryRepository>()
        val userRepo by inject<UserRepository>()
        val bookRepo by inject<BookRepository>()

        static {
            resources("static")
        }

        route("/user") {
            authenticate("auth-user") {
                updateUserInfo(userRepo,gson)
                getUserInfo(userRepo)
                addLibrary(libraryRepo)
                updateLibraryInfo(libraryRepo)
                addBook(bookRepo, libraryRepo)
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
