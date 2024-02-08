package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AttendanceApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.AttendanceDto.Companion.toDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import java.time.LocalDate

class ApiAttendanceRepository(private val api: AttendanceApi) : AttendanceRepository {
    override suspend fun getAttendance(start: LocalDate, finish: LocalDate): List<Attendance> =
        api.getAttendance(start, finish).map { it.toAttendance() }

    override suspend fun addAttendance(attendance: Attendance) {
        api.addAttendance(attendance.toDto())
    }

    override suspend fun addAttendance(attendance: List<Attendance>) {
        attendance.forEach { addAttendance(it) }
    }
}