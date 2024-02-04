package ru.omgtu.ivt213.mishenko.maksim.attendance

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.OmgtuScheduleApiImpl
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.OmgtuScheduleRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbAttendanceRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbLessonRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbStudentRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.AttendanceType
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.createSessionRetryContext
import java.time.LocalDate

suspend fun main() {
    try {
        val connectionString = System.getenv("YDB_CONNECTION_STRING")
        val token = System.getenv("YDB_KEY_JSON")
        val client = httpClient()
        val omgtuScheduleApi = OmgtuScheduleApiImpl("https://rasp.omgtu.ru/api", client)
        val scheduleRepository = OmgtuScheduleRepository(omgtuScheduleApi)
        val sessionRetryContext = createSessionRetryContext(token, connectionString)
        val lessonRepository = YdbLessonRepository(sessionRetryContext)
        val studentRepository = YdbStudentRepository(sessionRetryContext)
        val attendanceRepository = YdbAttendanceRepository(sessionRetryContext)

        val lesson = lessonRepository.getLessons()
        val student = studentRepository.getStudents().first()
        val schedule = scheduleRepository.getSchedule(LocalDate.of(2024, 2, 1), LocalDate.of(2024, 3, 1)).first()
        val attendance = Attendance(
            id = -1,
            student = student,
            type = AttendanceType.ATTENDED,
            lesson = schedule.lesson.run { lesson.first { it.name == name && it.type == type && it.teacher == teacher } },
            date = schedule.date
        )
        attendanceRepository.addAttendance(attendance)
    } catch (e: Exception) {
        println(e.stackTraceToString())
    }
}

fun httpClient() = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("ktorClient: $message")
            }
        }
        level = LogLevel.HEADERS
    }
}