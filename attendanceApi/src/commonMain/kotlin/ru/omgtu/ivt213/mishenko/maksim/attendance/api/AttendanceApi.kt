package ru.omgtu.ivt213.mishenko.maksim.attendance.api

import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.AttendanceDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.LessonDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.ScheduleItemDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.StudentDto
import java.time.LocalDate

interface AttendanceApi {
    suspend fun getStudents(): List<StudentDto>
    suspend fun getLessons(): List<LessonDto>
    suspend fun getSchedule(start: LocalDate, finish: LocalDate): List<ScheduleItemDto>
    suspend fun getAttendance(start: LocalDate, finish: LocalDate): List<AttendanceDto>
    suspend fun addAttendance(attendanceDto: AttendanceDto)
    suspend fun auth(login: String, password: String?): AuthRespond
}