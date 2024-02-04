package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Lesson
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.LessonType
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.executeQuery
import tech.ydb.table.SessionRetryContext
import tech.ydb.table.result.ResultSetReader
import java.lang.IllegalArgumentException

class YdbLessonRepository(private val sessionRetryContext: SessionRetryContext) : LessonRepository {
    override suspend fun getLessons(): List<Lesson> {
        val result = mutableListOf<Lesson>()
        sessionRetryContext.executeQuery(
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

    override suspend fun addLessons(lessons: List<Lesson>) {
        val id = sessionRetryContext.executeQuery("select * from lesson").getRowCount(0) + 1
        lessons.forEachIndexed() { index, lesson -> addLesson(lesson.copy(id = id + index)) }
    }

    fun addLesson(lesson: Lesson) {
        val type = lesson.type.id
        val teacher = lesson.getTeacherId()
        sessionRetryContext.executeQuery("UPSERT INTO `lesson`( `id`, `name`, `teacher`, `type` ) VALUES (${lesson.id}, \"${lesson.name}\", $teacher, $type );")
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

    private val teachers: MutableList<Pair<Int, String>> by lazy { teachers().toMutableList() }

    private fun teachers(): List<Pair<Int, String>> {
        val result = mutableListOf<Pair<Int, String>>()
        sessionRetryContext.executeQuery("select * from teacher").getResultSet(0).apply {
            while (next()) {
                result.add(Pair(getColumn("id").uint64.toInt(), getColumn("name").text))
            }
        }
        return result.sortedBy { it.first }
    }

    private fun Lesson.getTeacherId(): String {
        val id = teachers.firstOrNull { it.second == this.teacher }?.first
        if (id == null) {
            val teacher = toTeacher()
            teachers.add(teacher)
            sessionRetryContext.executeQuery("UPSERT INTO `teacher` ( `id`, `name` ) VALUES (${teacher.first}, \"${teacher.second}\");")
            return teacher.first.toString()
        }
        return id.toString()
    }

    private fun Lesson.toTeacher(): Pair<Int, String> {
        return Pair((teachers.lastOrNull()?.first ?: 0) + 1, teacher)
    }
}
