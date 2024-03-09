package ru.omgtu.ivt213.mishenko.maksim.attendance.useCase

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AttendanceApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AuthRespond
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.TokenStorage

class AuthUseCase(private val api: AttendanceApi, private val tokenStorage: TokenStorage) {
    suspend operator fun invoke(user: String, password: String? = null): AuthRespond {
        return api.auth(user, password).also { response ->
            if (response is AuthRespond.Success) {
                tokenStorage.token = response.token
            }
        }
    }
}