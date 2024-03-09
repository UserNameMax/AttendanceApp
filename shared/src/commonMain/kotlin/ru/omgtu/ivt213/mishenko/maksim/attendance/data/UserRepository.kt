package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}