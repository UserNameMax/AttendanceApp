package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AttendanceApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AttendanceApiImpl
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.TokenStorage

val dataModule = module {
    val tokenStorage = TokenStorage()
    single { tokenStorage }
    factory<AttendanceApi> {
        AttendanceApiImpl(
            client = httpClient(tokenStorage.token),
            baseUrl = "https://d5d0dv0bdev4ofgma92d.apigw.yandexcloud.net"
        )
    }
}

private fun httpClient(token: String) = HttpClient(CIO) {
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
    install(Auth) {
        bearer {
            loadTokens {
                BearerTokens(token, "")
            }
        }
    }
}