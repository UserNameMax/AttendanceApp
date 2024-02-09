package ru.omgtu.ivt213.mishenko.maksim.attendance

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.omgtu.ivt213.mishenko.maksim.attendance.di.di
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.auth
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.negotiation

fun main() {
    try {
        embeddedServer(factory = Netty, host = "0.0.0.0", port = 8080) {
            di()
            negotiation()
            auth(System.getenv("APIKEY"))
            routes()
        }.start(wait = true)
    } catch (e: Exception) {
        println(e.stackTraceToString())
    }
}