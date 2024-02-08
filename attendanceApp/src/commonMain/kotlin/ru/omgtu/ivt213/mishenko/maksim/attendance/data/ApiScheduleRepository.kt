package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AttendanceApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.ScheduleItem
import java.time.LocalDate

class ApiScheduleRepository(private val api: AttendanceApi) : ScheduleRepository {
    override suspend fun getSchedule(start: LocalDate, finish: LocalDate): List<ScheduleItem> =
        api.getSchedule(start, finish).map { it.toScheduleItem() }
}