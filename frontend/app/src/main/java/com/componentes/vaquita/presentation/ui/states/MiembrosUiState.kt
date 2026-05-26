package com.componentes.vaquita.presentation.ui.states

import com.componentes.vaquita.domain.model.Person

sealed class MiembrosUiState {
    object Idle : MiembrosUiState()
    object Loading : MiembrosUiState()
    data class Success(val members: List<Person>) : MiembrosUiState()
    data class Error(val message: String) : MiembrosUiState()
}
