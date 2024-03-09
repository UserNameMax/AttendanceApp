package ru.omgtu.ivt213.mishenko.maksim.attendance.ui.auth

data class AuthState(
    val login: String,
    val password: String,
    val inProgress: Boolean,
    val error: String?,
    val showPassword: Boolean,
    val names: List<String>
) {
    companion object {
        val default = AuthState(login = "", password = "", inProgress = false, error = null, showPassword = false, names = listOf())
    }
}
