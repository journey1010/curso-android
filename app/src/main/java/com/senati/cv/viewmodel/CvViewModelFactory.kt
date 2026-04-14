package com.senati.cv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.senati.cv.domain.repository.CvRepository

class CvViewModelFactory(private val repository: CvRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(CvViewModel::class.java)) {
            "Unknown ViewModel class: ${modelClass.name}"
        }
        return CvViewModel(repository) as T
    }
}
