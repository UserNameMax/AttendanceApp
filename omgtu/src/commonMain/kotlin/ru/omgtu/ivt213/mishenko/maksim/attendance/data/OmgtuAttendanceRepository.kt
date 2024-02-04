package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.OmgtuCapitanApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model.AttendanceDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model.LessonDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.*
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.LessonType.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class OmgtuAttendanceRepository(private val capitanApi: OmgtuCapitanApi) : AttendanceRepository {
    override suspend fun getAttendance(start: LocalDate, finish: LocalDate): List<Attendance> {
        return List(ChronoUnit.DAYS.between(start, finish).toInt() + 1) {
            val date = start.plusDays(it.toLong())
            capitanApi.selectDate(date)
            capitanApi.getAttendance().mapNotNull {
                try {
                    it.toAttendance(date)
                } catch (e: Throwable) {
                    null
                }
            }
        }.flatten()
    }

    override suspend fun addAttendance(attendance: Attendance) {
        val date = attendance.date.toLocalDate()
        val dto = attendance.toDto()
        capitanApi.selectDate(date)
        capitanApi.setAttendance(dto)
        capitanApi.save()
    }

    override suspend fun addAttendance(attendance: List<Attendance>) {
        attendance.groupBy { it.date.toLocalDate() }.forEach { (date, attendance) ->
            capitanApi.selectDate(date)
            attendance.map { it.toDto() }.forEach { capitanApi.setAttendance(it) }
            capitanApi.save()
        }
    }

    private fun AttendanceDto.toAttendance(date: LocalDate): Attendance {
        return Attendance(
            id = -1,
            student = Student(id = -1, name = student),
            type = typeNumber.toAttendanceType(),
            lesson = getLesson(),
            date = getLocalDateTime(date)
        )
    }

    private fun Int.toAttendanceType(): AttendanceType { //TODO update
        return when (this) {
            1 -> AttendanceType.ATTENDED
            else -> AttendanceType.UNKNOW
        }
    }

    private fun AttendanceDto.getLocalDateTime(date: LocalDate): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val time = LocalTime.parse(lessonDto.time, formatter)
        return date.atTime(time)
    }

    private fun AttendanceDto.getLesson(): Lesson {
        return Lesson(id = -1, name = lessonDto.name, teacher = lessonDto.teacher, type = lessonDto.lessonType.toEnum())
    }

    private fun String.toEnum(): LessonType {
        return when (this) {
            "Лекция" -> LECTION
            "Лабораторные работы" -> LABS
            "Практические занятия" -> PRACTICAL
            else -> UNKNOW
        }
    }

    private fun Attendance.toDto(): AttendanceDto {
        return AttendanceDto(
            student = student.name, typeNumber = type.id, lessonDto = LessonDto(
                name = lesson.name,
                groupType = "",
                lessonType = lesson.type.toDto(),
                teacher = lesson.teacher,
                time = date.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
            )
        )
    }

    private fun LessonType.toDto(): String {
        return when (this) {
            LECTION -> "Лекция"
            LABS -> "Лабораторные работы"
            PRACTICAL -> "Практические занятия"
            UNKNOW -> ""
        }
    }
}

