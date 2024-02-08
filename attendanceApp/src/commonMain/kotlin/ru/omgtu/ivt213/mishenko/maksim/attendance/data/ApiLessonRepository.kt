package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.AttendanceApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Lesson

class ApiLessonRepository(private val api: AttendanceApi) : LessonRepository {
    override suspend fun getLessons(): List<Lesson> = api.getLessons().map { it.toLesson() }

    override suspend fun addLessons(lessons: List<Lesson>) {
        throw UnsupportedOperationException("Attendance api not support it feature")
    }
}