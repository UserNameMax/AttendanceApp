package ru.omgtu.ivt213.mishenko.maksim.attendance

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.navigation.NavRoot
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.navigation.NavView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavView(NavRoot(defaultComponentContext()))
        }
    }
}