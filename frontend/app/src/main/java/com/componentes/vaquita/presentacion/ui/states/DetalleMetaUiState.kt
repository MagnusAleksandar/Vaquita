package com.componentes.vaquita.presentacion.ui.states

import android.os.Message
import com.componentes.vaquita.dominio.model.Goal

sealed class DetalleMetaUiState {
    object Idle : DetalleMetaUiState()
    object Loading : DetalleMetaUiState()
    data class Success(val goal: Goal): DetalleMetaUiState()
    data class Error(val message: String) : DetalleMetaUiState()
}