package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import org.koin.dsl.module
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.*
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.AuthUseCase

fun uiModule() = module {
    factory<AttendanceRepository> { ApiAttendanceRepository(get()) }
    factory<LessonRepository> { ApiLessonRepository(get()) }
    factory<ScheduleRepository> { ApiScheduleRepository(get()) }
    factory<StudentRepository> { ApiStudentRepository(get()) }

    factory { AuthUseCase(api = get(), tokenStorage = get()) }
}