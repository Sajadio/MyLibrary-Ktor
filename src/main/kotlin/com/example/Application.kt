package com.example

import com.example.database.DatabaseFactory
import com.example.di.appModule
import io.ktor.server.application.*
import com.example.plugins.*
import org.koin.core.context.startKoin
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    DatabaseFactory.init()

    configureStatusPage()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()

}
