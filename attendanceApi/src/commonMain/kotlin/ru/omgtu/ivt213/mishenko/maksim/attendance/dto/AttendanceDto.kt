package ru.omgtu.ivt213.mishenko.maksim.attendance.dto

import kotlinx.serialization.Serializable
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.LessonDto.Companion.toDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.StudentDto.Companion.toDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.AttendanceType
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.DateTimeConverter.formatDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.DateTimeConverter.getLocalDateTime

@Serializable
data class AttendanceDto(
    val id: Long,
    val student: StudentDto,
    val type: String,
    val lesson: LessonDto,
    val date: String
) {
    companion object {
        fun Attendance.toDto() = AttendanceDto(
            id = id,
            student = student.toDto(),
            type = type.name,
            lesson = lesson.toDto(),
            date = date.formatDto()
        )
    }

    fun toAttendance() =
        Attendance(
            id = id,
            student = student.toStudent(),
            type = AttendanceType.valueOf(type),
            lesson = lesson.toLesson(),
            date = date.getLocalDateTime()
        )
}
