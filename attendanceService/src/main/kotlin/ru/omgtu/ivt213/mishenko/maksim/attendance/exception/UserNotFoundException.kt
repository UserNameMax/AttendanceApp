package ru.omgtu.ivt213.mishenko.maksim.attendance.exception

class UserNotFoundException(name:String): Exception("User with name $name not found") {}