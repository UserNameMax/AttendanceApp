package ru.omgtu.ivt213.mishenko.maksim.attendance

import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.OmgtuCapitanApiImpl
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model.AttendanceDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model.LessonDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.OmgtuScheduleApiImpl
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.OmgtuAttendanceRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.OmgtuLessonRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.OmgtuScheduleRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.OmgtuStudentRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.di.initDi
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.SaveAttendanceFromYdb
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.UpdateLessonsUseCase
import ru.omgtu.ivt213.mishenko.maksim.attendance.useCase.UpdateStudentsUseCase
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.createSessionRetryContext
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.createWebDriver
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.httpClient
import java.time.LocalDate
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
    initDi()
    val koinComponent = object : KoinComponent {
        val updateStudentsUseCase: UpdateStudentsUseCase by inject()
        val updateLessonsUseCase: UpdateLessonsUseCase by inject()
        val saveAttendanceFromYdb: SaveAttendanceFromYdb by inject()
    }
    createSessionRetryContext( authorizedKeyJson = System.getenv("YDB_KEY_JSON"),
        connectionString = System.getenv("YDB_CONNECTION_STRING"))
    //koinComponent.updateStudentsUseCase.update()
    //koinComponent.updateLessonsUseCase.update()
    koinComponent.saveAttendanceFromYdb.save()
    println("hello world")
}