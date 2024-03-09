package ru.omgtu.ivt213.mishenko.maksim.attendance.ui.attendance

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.ScheduleItem
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student

data class AttendanceScreenState(
    val students: List<Student>,
    val schedule: List<ScheduleItem>,
    val attendance: List<Attendance>,
    val isData: Boolean,
    val isError: Boolean
) {
    companion object {
        val default = AttendanceScreenState(
            students = listOf(),
            schedule = listOf(),
            attendance = listOf(),
            isData = false,
            isError = false
        )
    }
}
