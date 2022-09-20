package com.example

import com.example.database.DatabaseFactory
import io.ktor.server.application.*
import com.example.plugins.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    configureStatusPage()

    DatabaseFactory.init()


    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
