package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.SaveAttendanceFromYdb
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.UpdateLessonsUseCase
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.UpdateStudentsUseCase

val presentationModule = module {
    single<UpdateStudentsUseCase> {
        UpdateStudentsUseCase(
            truthRepository = get(qualifier = named("omgtu")),
            secondaryRepository = get(qualifier = named("ydb"))
        )
    }
    single<UpdateLessonsUseCase> {
        UpdateLessonsUseCase(
            truthRepository = get(qualifier = named("omgtu")),
            secondaryRepository = get(qualifier = named("ydb"))
        )
    }
    single<SaveAttendanceFromYdb> {
        SaveAttendanceFromYdb(
            sessionRetryContext = get(),
            ydbAttendanceRepository = get(qualifier = named("ydb")),
            attendanceRepository = get(qualifier = named("omgtu"))
        )
    }
}