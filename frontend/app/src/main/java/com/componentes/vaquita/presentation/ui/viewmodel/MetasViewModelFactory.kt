package com.componentes.vaquita.presentation.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.componentes.vaquita.data.services.repository.GoalRepository

class MetasViewModelFactory(
    private val application: Application,
         private val repository: GoalRepository
     ) : ViewModelProvider.Factory {
         override fun <T : ViewModel> create(modelClass: Class<T>): T {
                 if (modelClass.isAssignableFrom(MetasViewModel::class.java)) {
                         @Suppress("UNCHECKED_CAST")
                         return MetasViewModel(application, repository) as T
                     }
                throw IllegalArgumentException("Unknown ViewModel class")
             }
     }
