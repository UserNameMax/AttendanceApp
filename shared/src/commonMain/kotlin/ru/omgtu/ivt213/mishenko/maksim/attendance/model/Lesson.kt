package ru.omgtu.ivt213.mishenko.maksim.attendance.model

data class Lesson(
    val id: Int,
    val name: String,
    val teacher: String,
    val type: LessonType
)
