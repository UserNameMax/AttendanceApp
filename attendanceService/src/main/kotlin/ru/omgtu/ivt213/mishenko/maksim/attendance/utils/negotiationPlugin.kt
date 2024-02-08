package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.negotiation() {
    install(ContentNegotiation) {
        json()
    }
}