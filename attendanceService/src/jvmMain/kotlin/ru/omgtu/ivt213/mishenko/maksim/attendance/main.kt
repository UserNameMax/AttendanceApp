package ru.omgtu.ivt213.mishenko.maksim.attendance

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.OmgtuScheduleApiImpl
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.OmgtuScheduleRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbAttendanceRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbLessonRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbStudentRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.di.di
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.AttendanceType
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.createSessionRetryContext
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.negotiation
import java.time.LocalDate

fun main() {
    try {
        embeddedServer(factory = Netty, host = "0.0.0.0", port = 8080) {
            di()
            negotiation()
            routes()
        }.start(wait = true)
    } catch (e: Exception) {
        println(e.stackTraceToString())
    }
}