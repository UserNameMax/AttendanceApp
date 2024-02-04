package ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model

data class LessonDto(
    val name: String,
    val groupType: String,
    val lessonType: String,
    val teacher: String,
    val time: String
)
