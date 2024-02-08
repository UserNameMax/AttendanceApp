package ru.omgtu.ivt213.mishenko.maksim.attendance.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Attendance
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.AttendanceType
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.ScheduleItem
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Student
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.DateTimeConverter.formatDto

@Composable
fun AttendanceScreenView(component: AttendanceScreenComponent = koinInject()) {
    val state by component.state.collectAsState()
    val scope = rememberCoroutineScope()
    when {
        state.isData -> {
            AttendanceScreenView(
                students = state.students,
                schedule = state.schedule,
                attendances = state.attendance
            ) { rowIndex, columnIndex, value ->
                val student = state.students[rowIndex - 1]
                val scheduleItem = state.schedule[columnIndex - 1]
                val attendance =
                    state.attendance.firstOrNull { it.student == student && it.lesson == scheduleItem.lesson && it.date == scheduleItem.date }
                component.changeAttendance(
                    attendance = attendance,
                    student = student,
                    scheduleItem = scheduleItem,
                    attendanceType = if (value) AttendanceType.ATTENDED else AttendanceType.NOT_SHOW_RESPECT,
                    scope = scope
                )
            }
        }

        state.isError -> {
            Button(onClick = { component.load(scope) }) { Text("Update") }
        }

        else -> {
            Text("Load")
        }
    }
    LaunchedEffect(Unit) {
        component.load(this)
    }
}

@Composable
fun AttendanceScreenView(
    students: List<Student>,
    schedule: List<ScheduleItem>,
    attendances: List<Attendance>,
    onAttendanceChange: (Int, Int, Boolean) -> Unit
) {
    val rowScrollState = rememberScrollState()
    val lessonWidth = 200.dp
    Column() {
        Row {
            Text(modifier = Modifier.width(300.dp), text = "text")
            Row(modifier = Modifier.horizontalScroll(rowScrollState)) {
                for (columnIndex in 1..schedule.size) {
                    Column {
                        val scheduleItem = schedule[columnIndex - 1]
                        Text(
                            modifier = Modifier.width(lessonWidth),
                            text = scheduleItem.lesson.name
                        )
                        Text(
                            modifier = Modifier.width(lessonWidth),
                            text = scheduleItem.lesson.teacher
                        )
                        Text(
                            modifier = Modifier.width(lessonWidth),
                            text = scheduleItem.lesson.type.name
                        )
                        Text(
                            modifier = Modifier.width(lessonWidth),
                            text = scheduleItem.date.formatDto()
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            for (rowIndex in 1..students.size) {
                Row(modifier = Modifier.border(width = 1.dp, color = Color.Black)) {
                    Text(modifier = Modifier.width(300.dp), text = students[rowIndex - 1].name)
                    Row(modifier = Modifier.horizontalScroll(rowScrollState)) {
                        for (columnIndex in 1..schedule.size) {
                            val student = students[rowIndex - 1]
                            val scheduleItem = schedule[columnIndex - 1]
                            val attendance =
                                attendances.firstOrNull { it.student == student && it.lesson == scheduleItem.lesson && it.date == scheduleItem.date }
                            Checkbox(
                                modifier = Modifier.width(lessonWidth),
                                checked = attendance?.type == AttendanceType.ATTENDED,
                                onCheckedChange = {
                                    onAttendanceChange(rowIndex, columnIndex, it)
                                })
                        }
                    }
                }
            }
        }
    }
}