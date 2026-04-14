package com.senati.cv.di

import android.content.Context
import com.senati.cv.data.local.database.CvDatabase
import com.senati.cv.data.repository.CvRepositoryImpl
import com.senati.cv.domain.repository.CvRepository
import com.senati.cv.viewmodel.CvViewModelFactory

class AppContainer(context: Context) {

    private val database: CvDatabase = CvDatabase.getInstance(context)

    private val repository: CvRepository = CvRepositoryImpl(
        personalDataDao = database.personalDataDao(),
        educationDao = database.educationDao(),
        certificateDao = database.certificateDao()
    )

    val cvViewModelFactory: CvViewModelFactory = CvViewModelFactory(repository)
}
