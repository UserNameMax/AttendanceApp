package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.auth(apiKey: String) {
    install(Authentication) {
        bearer("auth-bearer") {
            authenticate { tokenCredential ->
                when (tokenCredential.token) {
                    apiKey -> UserIdPrincipal("capitan")
                    apiKey.reversed() -> UserIdPrincipal("user")
                    else -> null
                }
            }
        }
    }
}