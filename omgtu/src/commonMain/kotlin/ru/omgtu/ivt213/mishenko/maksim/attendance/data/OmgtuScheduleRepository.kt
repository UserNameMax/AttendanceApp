package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.OmgtuScheduleApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model.ScheduleResponse
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.ScheduleItem
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OmgtuScheduleRepository(private val omgtuScheduleApi: OmgtuScheduleApi) : ScheduleRepository {
    override suspend fun getSchedule(startDate: LocalDate, finishDate: LocalDate): List<ScheduleItem> {
        val id = omgtuScheduleApi.search("ИВТ-213", "group").first().id.toString() //TODO inject term and type
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val start = startDate.format(formatter)
        val finish = finishDate.format(formatter)
        val schedule = omgtuScheduleApi.schedule(id, "group", start, finish)
        return schedule.map { it.toScheduleItem() }.sortedBy { it.date }
    }

    private fun ScheduleResponse.toScheduleItem(): ScheduleItem {
        return ScheduleItem(lesson = toLesson(), date = getDate())
    }

    private fun ScheduleResponse.getDate(): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
        return LocalDateTime.parse("$date $beginLesson", formatter)
    }
}

