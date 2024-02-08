package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AttendanceApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student
import java.lang.UnsupportedOperationException

class ApiStudentRepository(private val api: AttendanceApi) : StudentRepository {
    override suspend fun getStudents(): List<Student> = api.getStudents().map { it.toStudent() }

    override suspend fun addStudents(students: List<Student>) {
        throw UnsupportedOperationException("Attendance api not support it feature")
    }
}