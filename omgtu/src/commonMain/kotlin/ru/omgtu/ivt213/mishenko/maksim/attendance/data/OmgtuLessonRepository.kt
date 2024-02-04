package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.OmgtuScheduleApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model.ScheduleResponse
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Lesson
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.LessonType
import java.lang.UnsupportedOperationException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OmgtuLessonRepository(private val omgtuScheduleApi: OmgtuScheduleApi) : LessonRepository {
    override suspend fun getLessons(): List<Lesson> {
        val id = omgtuScheduleApi.search("ИВТ-213", "group").first().id.toString() //TODO inject term and type
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val start = LocalDate.now().format(formatter)
        val finish = LocalDate.now().plusMonths(1).format(formatter)
        val schedule = omgtuScheduleApi.schedule(id, "group", start, finish)
        return schedule.map { it.toLesson() }.distinct()
    }

    override suspend fun addLessons(lessons: List<Lesson>) {
        throw UnsupportedOperationException("Omgtu api not support create operations")
    }
}
