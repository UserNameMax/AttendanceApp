package di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.context.startKoin
import ru.omgtu.ivt213.mishenko.maksim.attendance.di.dataModule
import ru.omgtu.ivt213.mishenko.maksim.attendance.di.uiModule

fun initDi(componentContext: ComponentContext) = startKoin {
    modules(dataModule, uiModule(componentContext))
}