package com.componentes.vaquita.presentation.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.data.services.repository.GoalRepository
import com.componentes.vaquita.presentation.ui.states.DetalleMetaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetalleMetaViewModel(
    application: Application,
    private val repository: GoalRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<DetalleMetaUiState>(DetalleMetaUiState.Idle)
    val uiState: StateFlow<DetalleMetaUiState> = _uiState.asStateFlow()

    fun getGoalById(id: String) {
        _uiState.value = DetalleMetaUiState.Loading
        viewModelScope.launch {
            val result = repository.getGoalById(id)
            result.onSuccess { goal ->
                _uiState.value = DetalleMetaUiState.Success(goal)
            }
            result.onFailure { exception ->
                _uiState.value = DetalleMetaUiState.Error(exception.message ?: "Error al cargar la meta")
            }
        }
    }
}
