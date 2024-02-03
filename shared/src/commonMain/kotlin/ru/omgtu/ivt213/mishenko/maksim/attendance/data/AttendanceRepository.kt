package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance

interface AttendanceRepository {
    suspend fun getAttendance(): List<Attendance>
}