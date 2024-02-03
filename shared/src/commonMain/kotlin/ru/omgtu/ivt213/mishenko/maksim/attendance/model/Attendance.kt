package ru.omgtu.ivt213.mishenko.maksim.attendance.model

import java.time.LocalDateTime

data class Attendance(
    val id: Long,
    val student: Student,
    val type: AttendanceType,
    val lesson: Lesson,
    val date: LocalDateTime
)
