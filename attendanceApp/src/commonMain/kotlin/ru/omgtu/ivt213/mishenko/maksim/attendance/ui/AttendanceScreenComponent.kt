package ru.omgtu.ivt213.mishenko.maksim.attendance.ui

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.AttendanceRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.LessonRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.ScheduleRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.data.StudentRepository
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.AttendanceType
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.ScheduleItem
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student
import java.time.LocalDate

class AttendanceScreenComponent(
    componentContext: ComponentContext,
    private val attendanceRepository: AttendanceRepository,
    private val lessonRepository: LessonRepository,
    private val scheduleRepository: ScheduleRepository,
    private val studentRepository: StudentRepository
) : ComponentContext by componentContext {
    private val mutableState = MutableStateFlow(AttendanceScreenState.default)
    val state = mutableState.asStateFlow()

    fun load(scope: CoroutineScope) = scope.launch {
        val students = studentRepository.getStudents()
        val schedule = scheduleRepository.getSchedule(start = LocalDate.of(2024, 1, 1), finish = LocalDate.now())
        val attendance = attendanceRepository.getAttendance(start = LocalDate.of(2024, 1, 1), finish = LocalDate.now())
        mutableState.update {
            it.copy(
                students = students,
                schedule = schedule,
                attendance = attendance,
                isData = true
            )
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
        mutableState.update {
            it.copy(attendance = it.attendance + trueAttendance)
        }
        attendanceRepository.addAttendance(trueAttendance)
        mutableState.update {
            it.copy(
                attendance = attendanceRepository.getAttendance(
                    start = LocalDate.of(2024, 1, 1),
                    finish = LocalDate.now()
                )
            )
        }
    }
}