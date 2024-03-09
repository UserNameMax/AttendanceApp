package ru.omgtu.ivt213.mishenko.maksim.attendance.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.AttendanceDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.LessonDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.ScheduleItemDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.StudentDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.DateTimeConverter.formatDto
import java.time.LocalDate

class AttendanceApiImpl(private val client: HttpClient, private val baseUrl: String) : AttendanceApi {
    override suspend fun getStudents(): List<StudentDto> = client.get("$baseUrl/student").body()

    override suspend fun getLessons(): List<LessonDto> = client.get("$baseUrl/lesson").body()

    override suspend fun getSchedule(start: LocalDate, finish: LocalDate): List<ScheduleItemDto> =
        client.get("$baseUrl/schedule") {
            url {
                parameters.append("start", start.formatDto())
                parameters.append("finish", finish.formatDto())
            }
        }.body()

    override suspend fun getAttendance(start: LocalDate, finish: LocalDate): List<AttendanceDto> =
        client.get("$baseUrl/attendance") {
            url {
                parameters.append("start", start.formatDto())
                parameters.append("finish", finish.formatDto())
            }
        }.body()

    override suspend fun addAttendance(attendanceDto: AttendanceDto) {
        client.post("$baseUrl/attendance") {
            contentType(ContentType.Application.Json)
            setBody(attendanceDto)
        }.apply {
            when(status){
                HttpStatusCode.Forbidden -> throw Exception("Permission denied")
            }
        }
    }

    override suspend fun auth(login: String, password: String?) =
        client.get("$baseUrl/auth") {
            url {
                parameters.append("login", login)
                if (password != null) {
                    parameters.append("password", password)
                }
            }
        }.run {
            when (status) {
                HttpStatusCode.NotFound -> AuthRespond.UserNotFound
                HttpStatusCode.PaymentRequired -> AuthRespond.NeedPassword
                HttpStatusCode.BadRequest -> AuthRespond.IncorrectPassword
                HttpStatusCode.OK -> AuthRespond.Success(bodyAsText())
                else -> throw Exception("Unknown status: ${status.value}: ${status.description}")
            }
        }
}