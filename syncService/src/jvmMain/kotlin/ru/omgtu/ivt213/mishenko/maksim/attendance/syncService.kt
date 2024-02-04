package ru.omgtu.ivt213.mishenko.maksim.attendance

import kotlinx.coroutines.delay
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.OmgtuCapitanApiImpl
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model.AttendanceDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model.LessonDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.OmgtuScheduleApiImpl
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.OmgtuAttendanceRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.OmgtuLessonRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.OmgtuStudentRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.createWebDriver
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.httpClient
import java.time.LocalDate
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
    try {
        val client = httpClient()
        val sheduleApi = OmgtuScheduleApiImpl("https://rasp.omgtu.ru/api", client) //TODO inject url
        val lessonRepository = OmgtuLessonRepository(sheduleApi)
        val webDriver = createWebDriver(
            chromeDriverPath = "/home/max/Downloads/kotlin-wasm-examples/attendance/chromedriver",
            width = 1920 / 2 + 400,
            height = 1080 - 300
        )
        val capitanApi = OmgtuCapitanApiImpl(
            driver = webDriver,
            login = System.getenv("CAPITAN_LOGIN"),
            password = System.getenv("CAPITAN_PASSWORD"),
            baseUrl = "https://www.omgtu.ru/"
        )
        val attendanceRepository = OmgtuAttendanceRepository(capitanApi)
        val studentRepository = OmgtuStudentRepository(capitanApi).apply { day = LocalDate.of(2024, 2, 3) }
        println(studentRepository.getStudents())

    } catch (e: Throwable) {
        println("${e.javaClass} ${e.message}")
        println(e.stackTraceToString())
    }
}