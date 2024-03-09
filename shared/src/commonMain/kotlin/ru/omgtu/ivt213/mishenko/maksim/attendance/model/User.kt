package ru.omgtu.ivt213.mishenko.maksim.attendance.model

data class User(
    val login: String,
    val password: String?,
    val isCanMakeMark: Boolean
)
