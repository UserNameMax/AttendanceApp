package ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule

import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model.ScheduleResponse
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model.SearchResponse
import java.util.Calendar

interface OmgtuScheduleApi {
    suspend fun search(term: String, type: String): List<SearchResponse>
    suspend fun schedule(id: String, type: String, start: String, finish: String): List<ScheduleResponse>
}