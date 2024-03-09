package ru.omgtu.ivt213.mishenko.maksim.attendance.exception

class RequiredPasswordException(name: String): Exception("for user $name required password") {}