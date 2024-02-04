package ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student

data class AttendanceDto(
    val student: String,
    val lessonDto: LessonDto,
    val typeNumber: Int
)
