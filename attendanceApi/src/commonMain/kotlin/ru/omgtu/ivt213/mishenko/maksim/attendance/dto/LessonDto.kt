package ru.omgtu.ivt213.mishenko.maksim.attendance.dto

import kotlinx.serialization.Serializable
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Lesson
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.LessonType

@Serializable
data class LessonDto(
    val id: Int,
    val name: String,
    val teacher: String,
    val type: String
) {
    companion object {
        fun Lesson.toDto() = LessonDto(id = id, name = name, teacher = teacher, type = type.name)
    }

    fun toLesson() = Lesson(
        id = id,
        name = name,
        teacher = teacher,
        type = LessonType.valueOf(type)
    )
}
