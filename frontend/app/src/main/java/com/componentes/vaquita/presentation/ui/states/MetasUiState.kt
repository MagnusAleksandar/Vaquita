package com.componentes.vaquita.presentation.ui.states

import com.componentes.vaquita.domain.model.Goal

sealed class MetasUiState {
    object Idle : MetasUiState()
    object Loading: MetasUiState()
    data class Success(val goals: List<Goal>) : MetasUiState()
    data class Error(val message: String) : MetasUiState()
}