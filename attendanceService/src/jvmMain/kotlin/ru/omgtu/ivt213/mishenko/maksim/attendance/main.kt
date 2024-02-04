package ru.omgtu.ivt213.mishenko.maksim.attendance

import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbAttendanceRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbLessonRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbStudentRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.createSessionRetryContext
import java.time.LocalDate

suspend fun main() {
    try {
        val connectionString = System.getenv("YDB_CONNECTION_STRING")
        val token = System.getenv("YDB_KEY_JSON")
        val sessionRetryContext = createSessionRetryContext(token, connectionString)
        val lessonRepository = YdbLessonRepository(sessionRetryContext)
        val studentRepository = YdbStudentRepository(sessionRetryContext)
        val attendanceRepository = YdbAttendanceRepository(sessionRetryContext)
        println(lessonRepository.getLessons())
        println(studentRepository.getStudents())
        println(
            attendanceRepository.getAttendance(
                start = LocalDate.of(2024, 2, 3),
                finish = LocalDate.of(2024, 2, 4)
            ).apply { attendanceRepository.addAttendance(first().copy(id = -1)) })
    } catch (e: Exception) {
        println(e.message)
    }
}