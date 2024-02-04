package ru.omgtu.ivt213.mishenko.maksim.attendance.useCase

import ru.omgtu.ivt213.mishenko.maksim.attendance.data.AttendanceRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbAttendanceRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.executeQuery
import tech.ydb.table.SessionRetryContext
import java.time.LocalDateTime

class SaveAttendanceFromYdb(
    private val sessionRetryContext: SessionRetryContext,
    private val ydbAttendanceRepository: AttendanceRepository,
    private val attendanceRepository: AttendanceRepository
) {
    suspend fun save() {
        val attendance = unsavedAttendance()
        attendanceRepository.addAttendance(attendance)
        attendance.forEach { sessionRetryContext.executeQuery("update attendance set is_saved = true where id = ${it.id}") }
    }

    private suspend fun unsavedAttendance(): List<Attendance> {
        val unsavedIndex = mutableListOf<Pair<Long, LocalDateTime>>()
        sessionRetryContext.executeQuery(
            "select id, is_saved, date from attendance"
        ).getResultSet(0).apply {
            while (next()) {
                if (!getColumn("is_saved").bool) unsavedIndex.add(
                    Pair(
                        getColumn("id").uint64,
                        getColumn("date").datetime
                    )
                )
            }
        }
        val (start, finish) = unsavedIndex.sortedBy { it.second }.run { Pair(first().second, last().second) }
        return ydbAttendanceRepository.getAttendance(start.toLocalDate(), finish.toLocalDate())
            .filter { attendance -> unsavedIndex.any { attendance.id == it.first } }
    }
}