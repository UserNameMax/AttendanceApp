package ru.omgtu.ivt213.mishenko.maksim.attendance.model

enum class AttendanceType(val id: Int) {
    ATTENDED(1), UNKNOW(-1), NOT_SHOW(0), NOT_SHOW_RESPECT(3)
}