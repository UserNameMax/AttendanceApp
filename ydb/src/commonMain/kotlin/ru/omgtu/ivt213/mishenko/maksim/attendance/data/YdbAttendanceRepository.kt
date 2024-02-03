package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.*
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.executeResultQuery
import tech.ydb.table.SessionRetryContext
import tech.ydb.table.result.ResultSetReader
import java.lang.IllegalArgumentException

class YdbAttendanceRepository(private val sessionRetryContext: SessionRetryContext) : AttendanceRepository {
    override suspend fun getAttendance(): List<Attendance> {
        val result = mutableListOf<Attendance>()
        sessionRetryContext.executeResultQuery(
            "select attendance.id, attendance.`date`, lesson.name, student.id, student.name, `attendance-type`.name, teacher.name, `lesson-type`.name\n" +
                    "from attendance\n" +
                    "inner join lesson\n" +
                    "on lesson.id = attendance.lesson\n" +
                    "inner join student\n" +
                    "on student.id = attendance.student\n" +
                    "inner join `attendance-type`\n" +
                    "on `attendance-type`.id = attendance.type\n" +
                    "inner join teacher\n" +
                    "on teacher.id = lesson.teacher\n" +
                    "inner join `lesson-type`\n" +
                    "on `lesson-type`.id = lesson.type"
        ).getResultSet(0).apply {
            while (next()) {
                result.add(getAttendance())
            }
        }
        return result
    }

    private fun ResultSetReader.getAttendance() = Attendance(
        id = getColumn("attendance.id").uint64,
        student = getStudent(),
        lesson = getLesson(),
        type = try {
            AttendanceType.valueOf(getColumn("attendance-type.name").text)
        } catch (e: IllegalArgumentException) {
            AttendanceType.UNKNOW
        },
        date = getColumn("attendance.date").datetime
    )

    private fun ResultSetReader.getStudent() = Student(
        id = getColumn("student.id").uint64.toInt(),
        name = getColumn("student.name").text
    )

    private fun ResultSetReader.getLesson() =
        Lesson(
            name = getColumn("lesson.name").text,
            teacher = getColumn("teacher.name").text,
            type = try {
                LessonType.valueOf(getColumn("lesson-type.name").bytes.decodeToString())
            } catch (e: IllegalArgumentException) {
                LessonType.UNKNOW
            }
        )
}