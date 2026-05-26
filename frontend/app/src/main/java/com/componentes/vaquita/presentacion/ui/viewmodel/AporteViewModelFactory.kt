package com.componentes.vaquita.presentacion.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.componentes.vaquita.data.services.repository.GoalRepository
import com.componentes.vaquita.data.services.repository.PersonRepository

class AporteViewModelFactory(
    private val application: Application,
    private val goalRepository: GoalRepository,
    private val personRepository: PersonRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AporteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AporteViewModel(application, goalRepository, personRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
