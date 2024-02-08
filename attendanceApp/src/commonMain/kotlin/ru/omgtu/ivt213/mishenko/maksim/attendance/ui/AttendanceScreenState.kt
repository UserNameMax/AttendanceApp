package ru.omgtu.ivt213.mishenko.maksim.attendance.ui

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.ScheduleItem
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student
import javax.swing.text.StyledEditorKit.BoldAction

data class AttendanceScreenState(
    val students: List<Student>,
    val schedule: List<ScheduleItem>,
    val attendance: List<Attendance>,
    val isData: Boolean
) {
    companion object {
        val default = AttendanceScreenState(
            students = listOf(),
            schedule = listOf(),
            attendance = listOf(),
            isData = false
        )
    }
}
