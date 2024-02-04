package ru.omgtu.ivt213.mishenko.maksim.attendance.model

import java.time.LocalDateTime

data class ScheduleItem(
    val lesson: Lesson,
    val date: LocalDateTime
)