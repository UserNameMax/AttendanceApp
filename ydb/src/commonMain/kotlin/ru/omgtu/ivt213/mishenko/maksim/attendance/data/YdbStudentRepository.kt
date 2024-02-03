package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.executeResultQuery
import tech.ydb.table.SessionRetryContext
import tech.ydb.table.result.ResultSetReader

class YdbStudentRepository(private val sessionRetryContext: SessionRetryContext) : StudentRepository {
    override suspend fun getStudents(): List<Student> {
        val result = mutableListOf<Student>()
        sessionRetryContext.executeResultQuery("select * from student").getResultSet(0).apply {
            while (next()) {
                result.add(getStudent())
            }
        }
        return result
    }

    private fun ResultSetReader.getStudent() = Student(
        id = getColumn("id").uint64.toInt(),
        name = getColumn("name").text
    )
}