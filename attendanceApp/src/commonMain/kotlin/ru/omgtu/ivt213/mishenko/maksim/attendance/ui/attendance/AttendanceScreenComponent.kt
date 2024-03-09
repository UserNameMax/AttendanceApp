package ru.omgtu.ivt213.mishenko.maksim.attendance.ui.attendance

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.AttendanceRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.LessonRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.ScheduleRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.StudentRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.AttendanceType
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.ScheduleItem
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student
import java.time.LocalDate

class AttendanceScreenComponent(componentContext: ComponentContext) : ComponentContext by componentContext,
    KoinComponent {

    private val attendanceRepository: AttendanceRepository by inject()
    private val lessonRepository: LessonRepository by inject()
    private val scheduleRepository: ScheduleRepository by inject()
    private val studentRepository: StudentRepository by inject()

    private val mutableState = MutableStateFlow(AttendanceScreenState.default)
    val state = mutableState.asStateFlow()

    fun load(scope: CoroutineScope) = scope.launch {
        mutableState.update {
            it.copy(
                isData = false,
                isError = false
            )
        }
        try {
            val students = studentRepository.getStudents()
            val schedule = scheduleRepository.getSchedule(start = LocalDate.of(2024, 1, 1), finish = LocalDate.now())
            val attendance =
                attendanceRepository.getAttendance(start = LocalDate.of(2024, 1, 1), finish = LocalDate.now())
            mutableState.update {
                it.copy(
                    students = students,
                    schedule = schedule,
                    attendance = attendance,
                    isData = true,
                    isError = false
                )
            }
        } catch (e: Throwable) {
            mutableState.update {
                it.copy(
                    isData = false,
                    isError = true
                )
            }
        }

    }

    fun changeAttendance(
        attendance: Attendance?,
        student: Student,
        scheduleItem: ScheduleItem,
        attendanceType: AttendanceType,
        scope: CoroutineScope
    ) = scope.launch() {
        val trueAttendance = attendance?.copy(type = attendanceType) ?: Attendance(
            id = -1,
            student = student,
            type = attendanceType,
            lesson = scheduleItem.lesson,
            date = scheduleItem.date
        )
        val original = mutableState.value.attendance
        mutableState.update {
            it.copy(attendance = it.attendance + trueAttendance - (attendance?.run { listOf(this) }
                ?: listOf()).toSet())
        }
        try {
            attendanceRepository.addAttendance(trueAttendance)
        } catch (e: Throwable) {
            mutableState.update {
                it.copy(attendance = original)
            }
        }
    }
}