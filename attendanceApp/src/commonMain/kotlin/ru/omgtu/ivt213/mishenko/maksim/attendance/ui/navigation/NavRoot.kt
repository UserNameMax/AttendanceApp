package ru.omgtu.ivt213.mishenko.maksim.attendance.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import kotlinx.serialization.Serializable
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.attendance.AttendanceScreenComponent
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.auth.AuthComponent

class NavRoot(componentContext: ComponentContext) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Child>()
    val stack = childStack(
        source = navigation,
        serializer = Child.serializer(),
        initialConfiguration = Child.Auth,
        childFactory = ::componentFactory
    )

    private fun componentFactory(child: Child, componentContext: ComponentContext): ComponentContext {
        return when (child) {
            Child.Attendance -> AttendanceScreenComponent(componentContext)
            Child.Auth -> AuthComponent(
                componentContext = componentContext,
                onSuccess = { navigation.replaceAll(Child.Attendance) }
            )
        }
    }

    @Serializable
    sealed interface Child {
        @Serializable
        data object Auth : Child

        @Serializable
        data object Attendance : Child
    }
}