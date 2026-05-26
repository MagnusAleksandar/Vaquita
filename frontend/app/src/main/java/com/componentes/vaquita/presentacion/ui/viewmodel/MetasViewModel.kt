package com.componentes.vaquita.presentacion.ui.viewmodel

import com.componentes.vaquita.data.services.repository.GoalRepository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.dominio.model.Goal
import com.componentes.vaquita.presentacion.ui.states.MetasUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

sealed class GoalCreationState {
    object Idle : GoalCreationState()
    object Loading : GoalCreationState()
    object Success : GoalCreationState()
    data class Error(val message: String) : GoalCreationState()
}

class MetasViewModel(
    application: Application,
    private val repository: GoalRepository
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<MetasUiState>(MetasUiState.Idle)
    val uiState: StateFlow<MetasUiState> = _uiState.asStateFlow()

    private val _creationState = MutableStateFlow<GoalCreationState>(GoalCreationState.Idle)
    val creationState: StateFlow<GoalCreationState> = _creationState.asStateFlow()

    fun getMetas() {
        Log.d("VAQUITA_APP", "Iniciando la carga de metas desde el servidor...")
        _uiState.value = MetasUiState.Loading
        viewModelScope.launch {
            val result = repository.getAllGoals()
            
            if (result.isSuccess) {
                val listaMetas = result.getOrNull() ?: emptyList()
                Log.d("VAQUITA_APP", "Se cargaron correctamente ${listaMetas.size} metas")
                _uiState.value = MetasUiState.Success(listaMetas)
            } else {
                val error = result.exceptionOrNull()?.message ?: "Error desconocido"
                Log.e("VAQUITA_APP", "Error al traer las metas: $error")
                _uiState.value = MetasUiState.Error(error)
            }
        }
    }

    fun createGoal(name: String, amount: String, dueDate: String, urlImage: String) {
        Log.d("VAQUITA_APP", "Intentando crear nueva meta: $name")
        val montoInt = amount.toIntOrNull()
        
        if (name.isBlank() || montoInt == null || montoInt <= 0 || dueDate.isBlank()) {
            Log.w("VAQUITA_APP", "Validación fallida: Campos vacíos o monto inválido")
            _creationState.value = GoalCreationState.Error("Por favor, llena todos los campos correctamente")
            return
        }

        _creationState.value = GoalCreationState.Loading
        viewModelScope.launch {
            try {
                var imageId: String? = null
                
                // Si hay URL, primero creamos el registro de la imagen
                if (urlImage.isNotBlank()) {
                    val imgResult = repository.createImage(urlImage, "Imagen para $name")
                    if (imgResult.isSuccess) {
                        imageId = imgResult.getOrNull()?._id
                        Log.d("VAQUITA_APP", "Imagen creada con ID: $imageId")
                    } else {
                        Log.e("VAQUITA_APP", "Error al guardar imagen, continuando sin ella")
                    }
                }

                val newGoal = Goal(
                    _id = null,
                    name = name,
                    amount = montoInt,
                    dueDate = dueDate,
                    contributions = mutableListOf(),
                    image = imageId
                )
                
                val result = repository.createGoal(newGoal)
                
                if (result.isSuccess) {
                    Log.d("VAQUITA_APP", "Meta creada con éxito!")
                    _creationState.value = GoalCreationState.Success
                    getMetas() // Refrescamos la lista automáticamente
                } else {
                    val errorException = result.exceptionOrNull()
                    val errorMsg = errorException?.message ?: "Error al guardar"
                    Log.e("VAQUITA_APP", "Error al crear meta: $errorMsg", errorException)
                    _creationState.value = GoalCreationState.Error("Error: $errorMsg")
                }
            } catch (e: Exception) {
                Log.e("VAQUITA_APP", "Excepción al crear meta: ${e.message}")
                _creationState.value = GoalCreationState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    fun resetCreationState() {
        Log.d("VAQUITA_APP", "Reseteando el estado de creación")
        _creationState.value = GoalCreationState.Idle
    }
}
