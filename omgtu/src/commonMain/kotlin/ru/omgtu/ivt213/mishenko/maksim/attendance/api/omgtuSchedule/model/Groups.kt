package ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model

import kotlinx.serialization.Serializable

@Serializable
data class Groups(
    val group: String,
    val groupGUID: String,
    val groupGid: Long,
    val groupOid: Int,
    val groupUID: String?,
    val group_facultyname: String,
    val group_facultyoid: Int
)