package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import org.koin.core.context.startKoin

fun initDi() = startKoin {
    modules(dataModule, domainModule(), presentationModule)
}