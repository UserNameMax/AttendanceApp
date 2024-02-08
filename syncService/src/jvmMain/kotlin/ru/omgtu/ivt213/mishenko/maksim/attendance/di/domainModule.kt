package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.*
import tech.ydb.core.impl.YdbSchedulerFactory
import java.time.LocalDate

val domainModule = module {
    factory<StudentRepository>(named("omgtu")) {
        OmgtuStudentRepository(capitanApi = get()).apply {
            day = LocalDate.of(2024, 2, 3) //TODO
        }
    }
    factory<ScheduleRepository>(named("omgtu")) { OmgtuScheduleRepository(omgtuScheduleApi = get()) }
    factory<LessonRepository>(named("omgtu")) { OmgtuLessonRepository(omgtuScheduleApi = get()) }
    factory<AttendanceRepository>(named("omgtu")) { OmgtuAttendanceRepository(capitanApi = get()) }

    factory<StudentRepository>(named("ydb")) { YdbStudentRepository(sessionRetryContext = get()) }
    factory<LessonRepository>(named("ydb")) { YdbLessonRepository(sessionRetryContext = get()) }
    factory<AttendanceRepository>(named("ydb")) { YdbAttendanceRepository(sessionRetryContext = get()) }
}