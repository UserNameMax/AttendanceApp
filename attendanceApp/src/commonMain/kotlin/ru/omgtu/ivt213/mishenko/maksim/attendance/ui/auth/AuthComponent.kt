package ru.omgtu.ivt213.mishenko.maksim.attendance.ui.auth

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AuthRespond
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.StudentRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.AuthUseCase

class AuthComponent(componentContext: ComponentContext, private val onSuccess: () -> Unit) :
    ComponentContext by componentContext,
    KoinComponent {
    private val authUseCase: AuthUseCase by inject()
    private val studentRepository: StudentRepository by inject()
    private val mutableState = MutableStateFlow(AuthState.default)
    private val scope = CoroutineScope(Dispatchers.IO)
    val state = mutableState.asStateFlow()

    init {
        loadNames()
    }

    private fun loadNames() {
        scope.launch {
            mutableState.update { it.copy(inProgress = true, error = null) }
            val studentNames = studentRepository.getStudents().map { it.name }
            mutableState.update { it.copy(inProgress = false, names = studentNames) }
        }
    }

    fun auth() {
        scope.launch {
            state.value.run {
                mutableState.update { it.copy(inProgress = true, error = null) }
                authUseCase(login, password).also { authRespond ->
                    when (authRespond) {
                        is AuthRespond.IncorrectPassword -> {
                            mutableState.update { it.copy(error = "Неверный пароль", showPassword = true) }
                        }

                        is AuthRespond.NeedPassword -> {
                            mutableState.update { it.copy(showPassword = true) }
                        }

                        is AuthRespond.Success -> {
                            mutableState.update { it.copy(error = null) }
                            onSuccess()
                        }

                        is AuthRespond.UserNotFound -> {
                            mutableState.update { it.copy(error = "Неверное имя") }
                        }
                    }
                }
                mutableState.update { it.copy(inProgress = false) }
            }
        }
    }

    fun selectName(name: String) {
        mutableState.update { it.copy(login = name) }
    }

    fun inputPassword(password: String) {
        mutableState.update { it.copy(password = password) }
    }
}