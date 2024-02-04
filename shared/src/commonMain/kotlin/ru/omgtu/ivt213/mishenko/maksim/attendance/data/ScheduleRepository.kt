package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.ScheduleItem
import java.time.LocalDate

interface ScheduleRepository {
    suspend fun getSchedule(start: LocalDate, finish: LocalDate): List<ScheduleItem>
}