package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Lesson
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.LessonType
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.executeResultQuery
import tech.ydb.table.SessionRetryContext
import tech.ydb.table.result.ResultSetReader
import java.lang.IllegalArgumentException

class YdbLessonRepository(private val sessionRetryContext: SessionRetryContext) : LessonRepository {
    override suspend fun getLessons(): List<Lesson> {
        val result = mutableListOf<Lesson>()
        sessionRetryContext.executeResultQuery(
            "select lesson.id, lesson.name, teacher.name, `lesson-type.name` from lesson\n" +
                    "inner join teacher\n" +
                    "on teacher.id = lesson.teacher\n" +
                    "inner join `lesson-type`\n" +
                    "on `lesson-type`.id = lesson.type\n"
        ).getResultSet(0).apply {
            while (next()) {
                result.add(getLesson())
            }
        }
        return result
    }

    private fun ResultSetReader.getLesson() =
        Lesson(
            id = getColumn("lesson.id").uint64.toInt(),
            name = getColumn("lesson.name").text,
            teacher = getColumn("teacher.name").text,
            type = try {
                LessonType.valueOf(getColumn("lesson-type.name").bytes.decodeToString())
            } catch (e: IllegalArgumentException) {
                LessonType.UNKNOW
            }
        )
}