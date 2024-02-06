package ru.omgtu.ivt213.mishenko.maksim.attendance.dto

import kotlinx.serialization.Serializable
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student

@Serializable
data class StudentDto(
    val id: Int,
    val name: String
) {
    companion object {
        fun Student.toDto() = StudentDto(id = id, name = name)
    }

    fun toStudent() = Student(id = id, name = name)
}
