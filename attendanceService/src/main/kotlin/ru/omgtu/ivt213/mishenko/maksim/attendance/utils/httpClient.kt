package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*

fun httpClient() = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("ktorClient: $message")
            }
        }
        level = LogLevel.HEADERS
    }
}