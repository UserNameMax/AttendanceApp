package ru.omgtu.ivt213.mishenko.maksim.attendance.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.attendance.AttendanceScreenComponent
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.attendance.AttendanceScreenView
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.auth.AuthComponent
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.auth.AuthScreen

@Composable
fun NavView(navRoot: NavRoot) {
    Children(navRoot.stack) {
        when (it.configuration) {
            NavRoot.Child.Attendance -> AttendanceScreenView(it.instance as AttendanceScreenComponent)
            NavRoot.Child.Auth -> AuthScreen(it.instance as AuthComponent)
        }
    }
}