package ru.omgtu.ivt213.mishenko.maksim.attendance.dto

import kotlinx.serialization.Serializable
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.LessonDto.Companion.toDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.ScheduleItem
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.DateTimeConverter.formatDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.DateTimeConverter.getLocalDateTime

@Serializable
data class ScheduleItemDto(
    val lesson: LessonDto,
    val date: String
) {
    companion object {
        fun ScheduleItem.toDto() = ScheduleItemDto(lesson = lesson.toDto(), date = date.formatDto())
    }

    fun toScheduleItem() = ScheduleItem(lesson = lesson.toLesson(), date = date.getLocalDateTime())
}
