package com.pixabay.challenge

import android.app.Application
import coil.Coil
import coil.ImageLoader
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        initCoil()
    }

    private fun initCoil() {
        Coil.setImageLoader(imageLoader)
    }
}
