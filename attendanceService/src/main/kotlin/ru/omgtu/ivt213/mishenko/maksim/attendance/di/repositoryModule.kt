package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import org.koin.dsl.module
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.*
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.AuthUseCase
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.ScheduleUseCase

val repositoryModule = module {
    factory<ScheduleRepository> { OmgtuScheduleRepository(get()) }
    factory<LessonRepository> { YdbLessonRepository(get()) }
    factory<StudentRepository> { YdbStudentRepository(get()) }
    factory<AttendanceRepository> { YdbAttendanceRepository(get()) }
    factory<UserRepository> { YdbUserRepository(get()) }


    factory<ScheduleUseCase> { ScheduleUseCase(lessonRepository = get(), scheduleRepository = get()) }
    factory<AuthUseCase> { AuthUseCase(userRepository = get()) }
}