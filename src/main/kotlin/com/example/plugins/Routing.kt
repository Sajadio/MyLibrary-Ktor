package com.example.plugins

import com.example.di.appModule
import com.example.routes.auth.authRoutes
import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureRouting() {
    install(Koin) {
        modules(appModule)
    }
    routing {
        authRoutes()
    }
}
