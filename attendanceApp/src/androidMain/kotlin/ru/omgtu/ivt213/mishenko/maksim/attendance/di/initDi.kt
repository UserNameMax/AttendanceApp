package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.context.startKoin

fun initDi(componentContext: ComponentContext) = startKoin {
    modules(dataModule, uiModule(componentContext))
}