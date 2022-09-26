package com.example.plugins

import com.example.di.appModule
import com.example.repository.admin.AdminRepository
import com.example.repository.auth.AuthRepository
import com.example.repository.user.UserRepository
import com.example.routes.admin.*
import com.example.routes.auth.login
import com.example.routes.auth.signUp
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
        route("/user") {
            authenticate("auth-user") {
                val userRepo by inject<UserRepository>()
                getUserInfo(userRepo)
                updateUserInfo(userRepo, gson)
            }
        }

        route("/admin") {
            authenticate("auth-admin") {
                val adminRepo by inject<AdminRepository>()
                getAdminInfo(adminRepo)
                getAllUser(adminRepo)
                deleteAllUser(adminRepo)
                deleteUserById(adminRepo)
                findUserByEmail(adminRepo)
                updateAdminInfo(adminRepo, gson)
            }
        }
    }
}
