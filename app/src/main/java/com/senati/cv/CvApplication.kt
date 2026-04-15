package com.senati.cv

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase de aplicación necesaria para que Hilt pueda generar
 * el grafo de dependencias en tiempo de compilación.
 */
@HiltAndroidApp
class CvApplication : Application()
