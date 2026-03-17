package com.arthur.rukiasvet.core

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.arthur.rukiasvet.core.di.AppContainer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RukiasVetApp : Application(), ImageLoaderFactory {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun newImageLoader(): ImageLoader = imageLoader
}