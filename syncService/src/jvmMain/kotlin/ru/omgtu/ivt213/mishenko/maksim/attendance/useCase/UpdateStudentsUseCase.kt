package ru.omgtu.ivt213.mishenko.maksim.attendance.useCase

import ru.omgtu.ivt213.mishenko.maksim.attendance.data.StudentRepository

class UpdateStudentsUseCase(
    private val truthRepository: StudentRepository,
    private val secondaryRepository: StudentRepository
) {
    suspend fun update() {
        val studentsList = truthRepository.getStudents().toMutableList()
        val savedStudents = secondaryRepository.getStudents()
        studentsList.removeIf { student -> savedStudents.any { it.name == student.name } }
        secondaryRepository.addStudents(studentsList)
    }
}