import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.initDi
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.navigation.NavRoot
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.navigation.NavView

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val windowState = WindowState()
    val lifecycle = LifecycleRegistry()
    initDi()
    singleWindowApplication(
        state = windowState,
        title = "title",
    ) {
        LifecycleController(lifecycle, windowState)
        NavView(NavRoot(DefaultComponentContext(lifecycle)))
    }
}