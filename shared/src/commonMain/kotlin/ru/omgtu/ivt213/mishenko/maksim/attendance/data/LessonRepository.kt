package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Lesson

interface LessonRepository {
    suspend fun getLessons(): List<Lesson>
    suspend fun addLessons(lessons: List<Lesson>)
}