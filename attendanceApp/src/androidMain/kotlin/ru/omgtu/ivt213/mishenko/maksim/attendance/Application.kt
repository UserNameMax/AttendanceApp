package ru.omgtu.ivt213.mishenko.maksim.attendance

import android.app.Application
import ru.omgtu.ivt213.mishenko.maksim.attendance.di.initDi

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initDi()
    }
}