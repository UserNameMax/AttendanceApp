package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeConverter {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    fun LocalDateTime.formatDto() = format(dateTimeFormatter)
    fun LocalDate.formatDto() = format(dateFormatter)
    fun String.getLocalDateTime() = LocalDateTime.parse(this, dateTimeFormatter)
    fun String.getLocalDate() = LocalDate.parse(this, dateFormatter)
}