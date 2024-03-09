package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val Dispatchers.UI: CoroutineContext
    get() = IO