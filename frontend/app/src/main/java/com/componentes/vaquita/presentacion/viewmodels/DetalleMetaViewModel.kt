package com.componentes.vaquita.presentacion.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.data.repositories.GoalRepository
import com.componentes.vaquita.dominio.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import android.util.Log

class DetalleMetaViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = GoalRepository()

    private val _uiState = MutableStateFlow<UiState<com.componentes.vaquita.dominio.models.Goal>>(UiState.Idle)
    val uiState: StateFlow<UiState<com.componentes.vaquita.dominio.models.Goal>> = _uiState.asStateFlow()

    // Obtener los detalles de una meta específica y calcular su progreso
    fun getGoalById(id: String) {
        Log.d("VAQUITA_APP", "Cargando detalles de la meta con ID: $id")
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.getGoalById(id)
            
            if (result.isSuccess) {
                val goal = result.getOrNull()
                if (goal != null) {
                    // Cálculo manual de progreso para mostrar en los Logs
                    val totalMeta = goal.amount ?: 0
                    val totalAportado = goal.contributions?.sumOf { it.amount ?: 0 } ?: 0
                    val porcentaje = if (totalMeta > 0) (totalAportado.toFloat() / totalMeta.toFloat()) * 100 else 0f
                    
                    Log.d("VAQUITA_APP", "Meta: ${goal.name}")
                    Log.d("VAQUITA_APP", "Progreso calculado: $totalAportado de $totalMeta ($porcentaje%)")
                    
                    _uiState.value = UiState.Success(goal)
                } else {
                    Log.e("VAQUITA_APP", "La meta llegó nula desde el repositorio")
                    _uiState.value = UiState.Error("No se encontró la información")
                }
            } else {
                val error = result.exceptionOrNull()?.message ?: "Error desconocido"
                Log.e("VAQUITA_APP", "Error al cargar detalle de meta: $error")
                _uiState.value = UiState.Error(error)
            }
        }
    }
}
