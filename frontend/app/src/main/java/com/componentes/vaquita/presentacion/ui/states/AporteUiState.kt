package com.componentes.vaquita.presentacion.ui.states

sealed class AporteUiState {
    object Idle : AporteUiState()
    object Loading : AporteUiState()
    object Success : AporteUiState()
    data class Error(val message: String) : AporteUiState()
}
