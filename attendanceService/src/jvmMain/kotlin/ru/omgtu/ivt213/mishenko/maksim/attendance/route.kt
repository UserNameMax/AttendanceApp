package ru.omgtu.ivt213.mishenko.maksim.attendance

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.*
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.AttendanceDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.AttendanceDto.Companion.toDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.LessonDto.Companion.toDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.ScheduleItemDto.Companion.toDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.dto.StudentDto.Companion.toDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.ScheduleUseCase
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.DateTimeConverter.getLocalDate
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.saveGet
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.savePost

fun Application.routes() {
    val scheduleUseCase: ScheduleUseCase by inject()
    val lessonRepository: LessonRepository by inject()
    val studentRepository: StudentRepository by inject()
    val attendanceRepository: AttendanceRepository by inject()
    routing {
        saveGet("/schedule") {
            val start = call.request.queryParameters["start"]?.getLocalDate()
            val finish = call.request.queryParameters["finish"]?.getLocalDate()
            when {
                start == null -> call.respond(HttpStatusCode.BadRequest, "Need put start")
                finish == null -> call.respond(HttpStatusCode.BadRequest, "Need put finish")
                else -> call.respond(scheduleUseCase.getSchedule(start, finish).map { it.toDto() })
            }
        }
        saveGet("/lesson") {
            call.respond(lessonRepository.getLessons().map { it.toDto().apply { println(it) } })
        }
        saveGet("/student") {
            call.respond(studentRepository.getStudents().map { it.toDto() })
        }
        route("/attendance") {
            saveGet {
                val start = call.request.queryParameters["start"]?.getLocalDate()
                val finish = call.request.queryParameters["finish"]?.getLocalDate()
                when {
                    start == null -> call.respond(HttpStatusCode.BadRequest, "Need put start")
                    finish == null -> call.respond(HttpStatusCode.BadRequest, "Need put finish")
                    else -> call.respond(attendanceRepository.getAttendance(start, finish).map { it.toDto() })
                }
            }
            savePost {
                val attendance = call.receive<AttendanceDto>().toAttendance()
                attendanceRepository.addAttendance(attendance)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}