package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import java.time.LocalDate

interface AttendanceRepository {
    suspend fun getAttendance(start: LocalDate, finish: LocalDate): List<Attendance>
    suspend fun addAttendance(attendance: Attendance)
    suspend fun addAttendance(attendance: List<Attendance>)
}