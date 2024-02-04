package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.OmgtuCapitanApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student
import java.lang.UnsupportedOperationException
import java.time.LocalDate

class OmgtuStudentRepository(private val capitanApi: OmgtuCapitanApi) : StudentRepository {
    var day = LocalDate.now()
    override suspend fun getStudents(): List<Student> {
        capitanApi.selectDate(day)
        return capitanApi.getStudentList().map { Student(id = -1, name = it) }
    }

    override suspend fun addStudents(students: List<Student>) {
        throw UnsupportedOperationException("Omgtu api not support create operations")
    }
}