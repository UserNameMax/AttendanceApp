package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import com.arkivanov.decompose.ComponentContext
import org.koin.dsl.module
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.*
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.AttendanceScreenComponent

fun uiModule(componentContext: ComponentContext) = module {
    factory<AttendanceRepository> { ApiAttendanceRepository(get()) }
    factory<LessonRepository> { ApiLessonRepository(get()) }
    factory<ScheduleRepository> { ApiScheduleRepository(get()) }
    factory<StudentRepository> { ApiStudentRepository(get()) }
    factory<AttendanceScreenComponent> {
        AttendanceScreenComponent(
            componentContext = componentContext,
            attendanceRepository = get(),
            lessonRepository = get(),
            scheduleRepository = get(),
            studentRepository = get()
        )
    } //TODO
}