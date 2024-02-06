package ru.omgtu.ivt213.mishenko.maksim.attendance.useCase

import ru.omgtu.ivt213.mishenko.maksim.attendance.data.LessonRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.ScheduleRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.ScheduleItem
import java.time.LocalDate

class ScheduleUseCase(
    private val lessonRepository: LessonRepository,
    private val scheduleRepository: ScheduleRepository
) {
    suspend fun getSchedule(start: LocalDate, finish: LocalDate): List<ScheduleItem> {
        val lessons = lessonRepository.getLessons()
        return scheduleRepository.getSchedule(start, finish).mapNotNull { scheduleItem ->
            val lesson =
                lessons.firstOrNull { it.name == scheduleItem.lesson.name && it.type == scheduleItem.lesson.type && it.teacher == scheduleItem.lesson.teacher }
            if (lesson != null) scheduleItem.copy(lesson = lesson) else null
        }
    }
}