package ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model.AttendanceDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model.LessonDto
import java.time.LocalDate

interface OmgtuCapitanApi {
    fun selectDate(date: LocalDate)
    fun getStudentList(): List<String>
    fun getAttendance(): List<AttendanceDto>
    fun setAttendance(attendance: AttendanceDto)
    fun getLessons(): List<LessonDto>
    fun save()
}