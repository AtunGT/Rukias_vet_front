package com.arthur.rukiasvet.core

import android.app.Application
import com.arthur.rukiasvet.core.di.AppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RukiasVetApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}