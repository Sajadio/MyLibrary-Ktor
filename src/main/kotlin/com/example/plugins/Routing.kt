package com.example.plugins

import com.example.di.appModule
import com.example.domain.repository.*
import com.example.routes.admin.*
import com.example.routes.auth.login
import com.example.routes.auth.signUp
import com.example.routes.book.*
import com.example.routes.library.*
import com.example.routes.user.getProfileUser
import com.example.routes.user.updateProfileUserImage
import com.example.routes.user.updateUserProfile
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
        val adminRepo by inject<AdminRepository>()
        val libraryRepo by inject<LibraryRepository>()
        val userRepo by inject<UserRepository>()
        val bookRepo by inject<BookRepository>()
        val notifyRepo by inject<NotificationRepository>()

        static {
            resources("static")
        }

        route("/user") {
            authenticate("auth-user") {
                getProfileUser(userRepo)
                updateProfileUserImage(userRepo)
                updateUserProfile(userRepo)
                addLibrary(libraryRepo, gson)
                getLibrariesThatAccepted(libraryRepo)
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
                getAdminInfo(adminRepo)
                updateProfileAdminImage(adminRepo)
                getAllUser(adminRepo)
                deleteAllUser(adminRepo)
                deleteUserById(adminRepo)
                deleteLibraryById(adminRepo)
                getUserByEmail(adminRepo)
                updateAdminInfo(adminRepo)
                getAllLibrariesNotAccepted(adminRepo)
                deleteAllLibraries(adminRepo)
                acceptLibrary(adminRepo, userRepo, libraryRepo,notifyRepo)
                rejectLibrary(adminRepo)
            }
        }
    }
}
