package com.senati.cv

import android.app.Application
import com.senati.cv.di.AppContainer

class CvApplication : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
