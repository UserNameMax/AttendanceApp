package ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val description: String,
    val id: Int,
    val label: String,
    val type: String
)