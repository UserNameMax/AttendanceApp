import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.initDi
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.navigation.NavRoot
import ru.omgtu.ivt213.mishenko.maksim.attendance.ui.navigation.NavView
import javax.swing.SwingUtilities

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val windowState = WindowState()
    val lifecycle = LifecycleRegistry()
    initDi(DefaultComponentContext(lifecycle))
    singleWindowApplication(
        state = windowState,
        title = "title",
    ) {
        LifecycleController(lifecycle, windowState)
        NavView(NavRoot(DefaultComponentContext(lifecycle)))
    }
}

private inline fun <T : Any> runOnMainThreadBlocking(crossinline block: () -> T): T {
    lateinit var result: T
    SwingUtilities.invokeAndWait { result = block() }
    return result
}