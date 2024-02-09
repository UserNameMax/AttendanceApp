package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.auth(apiKey: String) {
    install(Authentication) {
        bearer("auth-bearer") {
            authenticate { tokenCredential ->
                if (tokenCredential.token == apiKey) {
                    UserIdPrincipal("user")
                } else {
                    null
                }
            }
        }
    }
}