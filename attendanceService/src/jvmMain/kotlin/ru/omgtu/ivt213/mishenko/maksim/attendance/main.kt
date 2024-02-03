package ru.omgtu.ivt213.mishenko.maksim.attendance

import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbAttendanceRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbLessonRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.YdbStudentRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.createSessionRetryContext

suspend fun main() {
    val connectionString = System.getenv("YDB_CONNECTION_STRING")
    val token = System.getenv("IRM_TOKEN")
    val sessionRetryContext = createSessionRetryContext(token, connectionString)
    val lessonRepository = YdbLessonRepository(sessionRetryContext)
    val studentRepository = YdbStudentRepository(sessionRetryContext)
    val attendanceRepository = YdbAttendanceRepository(sessionRetryContext)
    println(lessonRepository.getLessons())
    println(studentRepository.getStudents())
    try {
        println(attendanceRepository.getAttendance().apply { attendanceRepository.addAttendance(first().copy(id = -1)) })
    } catch (e:Exception){
        println(e.message)
    }
}