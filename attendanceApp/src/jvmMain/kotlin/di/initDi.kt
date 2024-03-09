package di

import org.koin.core.context.startKoin
import ru.omgtu.ivt213.mishenko.maksim.attendance.di.dataModule
import ru.omgtu.ivt213.mishenko.maksim.attendance.di.uiModule

fun initDi() = startKoin {
    modules(dataModule, uiModule())
}