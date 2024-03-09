package ru.omgtu.ivt213.mishenko.maksim.attendance.api

sealed interface AuthRespond {
    data object UserNotFound : AuthRespond
    data object NeedPassword : AuthRespond
    data object IncorrectPassword : AuthRespond
    data class Success(val token: String) : AuthRespond
}