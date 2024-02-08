package ru.omgtu.ivt213.mishenko.maksim.attendance.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.AttendanceType

@Composable
fun AttendanceScreenView(component: AttendanceScreenComponent = koinInject()) {
    val state by component.state.collectAsState()
    if (state.isData) {
        Column {
            for (rowIndex in 0..state.students.size) {
                Row(modifier = Modifier.border(width = 1.dp, color = Color.Black)) {
                    for (columnIndex in 0..state.schedule.size) {
                        when {
                            rowIndex == 0 && columnIndex == 0 -> {
                                Text(modifier = Modifier.width(300.dp), text = "text")
                            }

                            columnIndex == 0 -> {
                                Text(modifier = Modifier.width(300.dp), text = state.students[rowIndex - 1].name)
                            }

                            rowIndex == 0 -> {
                                Text(
                                    modifier = Modifier.width(100.dp),
                                    text = state.schedule[columnIndex - 1].lesson.name
                                )
                            }

                            else -> {
                                val student = state.students[rowIndex - 1]
                                val scheduleItem = state.schedule[columnIndex - 1]
                                val attendance =
                                    state.attendance.firstOrNull { it.student == student && it.lesson == scheduleItem.lesson && it.date == scheduleItem.date }
                                val scope = rememberCoroutineScope()
                                Checkbox(
                                    modifier = Modifier.width(100.dp),
                                    checked = attendance?.type == AttendanceType.ATTENDED,
                                    onCheckedChange = {
                                        component.changeAttendance(
                                            attendance = attendance,
                                            student = student,
                                            scheduleItem = scheduleItem,
                                            attendanceType = if (it) AttendanceType.ATTENDED else AttendanceType.NOT_SHOW_RESPECT,
                                            scope = scope
                                        )
                                    })
                            }
                        }
                    }
                }
            }
        }
    } else {
        Text("Load")
    }
    LaunchedEffect(Unit) {
        component.load(this)
    }
}