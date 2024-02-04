package ru.omgtu.ivt213.mishenko.maksim.attendance.useCase

import ru.omgtu.ivt213.mishenko.maksim.attendance.data.LessonRepository

class UpdateLessonsUseCase(
    private val truthRepository: LessonRepository,
    private val secondaryRepository: LessonRepository
) {
    suspend fun update() {
        val lessons = truthRepository.getLessons().toMutableList()
        val savedRepository = secondaryRepository.getLessons()
        lessons.removeIf { lesson -> savedRepository.any { lesson.name == it.name && lesson.teacher == it.teacher && lesson.type == it.type } }
        secondaryRepository.addLessons(lessons)
    }
}