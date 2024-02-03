package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student

interface StudentRepository {
    suspend fun getStudents(): List<Student>
}