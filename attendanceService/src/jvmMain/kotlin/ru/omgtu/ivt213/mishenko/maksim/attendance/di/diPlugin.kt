package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.di() {
    install(Koin) {
        modules(dataModule, repositoryModule)
    }
}