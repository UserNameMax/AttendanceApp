package ru.omgtu.ivt213.mishenko.maksim.attendance.useCase

import ru.omgtu.ivt213.mishenko.maksim.attendance.data.UserRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.exception.PasswordException
import ru.omgtu.ivt213.mishenko.maksim.attendance.exception.RequiredPasswordException
import ru.omgtu.ivt213.mishenko.maksim.attendance.exception.UserNotFoundException

class AuthUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(login: String, password: String?): Boolean {
        return userRepository.getUsers().firstOrNull { it.login == login }.run {
            when {
                this == null -> throw UserNotFoundException(login)
                this.password != null && this.password != "" ->
                    when {
                        password == null -> throw RequiredPasswordException(login)
                        password != this.password -> throw PasswordException(login)
                        else -> isCanMakeMark
                    }
                else -> isCanMakeMark
            }
        }
    }

}