package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.executeQuery
import tech.ydb.table.SessionRetryContext
import tech.ydb.table.result.ResultSetReader

class YdbStudentRepository(private val sessionRetryContext: SessionRetryContext) : StudentRepository {
    override suspend fun getStudents(): List<Student> {
        val result = mutableListOf<Student>()
        sessionRetryContext.executeQuery("select * from student").getResultSet(0).apply {
            while (next()) {
                result.add(getStudent())
            }
        }
        return result
    }

    override suspend fun addStudents(students: List<Student>) {
        val id = getStudents().maxBy { it.id }.id + 1
        students.forEachIndexed() { index, student -> addStudent(student = student.copy(id + index)) }
    }

    fun addStudent(student: Student) {
        sessionRetryContext.executeQuery("UPSERT INTO `student` ( `id`, `name` ) VALUES (${student.id}, \"${student.name}\");")
    }

    private fun ResultSetReader.getStudent() = Student(
        id = getColumn("id").uint64.toInt(),
        name = getColumn("name").text
    )
}