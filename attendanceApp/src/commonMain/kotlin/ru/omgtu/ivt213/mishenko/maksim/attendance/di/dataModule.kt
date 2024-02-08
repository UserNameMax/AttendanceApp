package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AttendanceApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AttendanceApiImpl

val dataModule = module {
    single<AttendanceApi> { AttendanceApiImpl(client = httpClient(), baseUrl = "https://bba1a624p8k4nnlmb5a0.containers.yandexcloud.net") }
}

private fun httpClient() = HttpClient(CIO) {
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