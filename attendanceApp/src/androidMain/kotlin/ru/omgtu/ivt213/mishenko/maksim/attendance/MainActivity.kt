package ru.omgtu.ivt213.mishenko.maksim.attendance

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext
import ru.omgtu.ivt213.mishenko.maksim.attendance.di.initDi
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.attendance.AttendanceScreenView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!(application as Application).diIsInti) {
            initDi(defaultComponentContext())
            (application as Application).diIsInti = true
        }
        setContent {
            AttendanceScreenView()
        }
    }
}