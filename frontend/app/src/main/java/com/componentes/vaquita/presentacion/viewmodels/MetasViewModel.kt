package com.componentes.vaquita.presentacion.viewmodels

import com.componentes.vaquita.data.repositories.GoalRepository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.dominio.models.Goal
import com.componentes.vaquita.dominio.models.Image
import com.componentes.vaquita.dominio.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class MetasViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = GoalRepository()
    private val _uiState = MutableStateFlow<UiState<List<Goal>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Goal>>> = _uiState.asStateFlow()

    private val _creationState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val creationState: StateFlow<UiState<Unit>> = _creationState.asStateFlow()

    fun getMetas() {
        Log.d("VAQUITA_APP", "Iniciando la carga de metas desde el servidor...")
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.getAllGoals()
            
            if (result.isSuccess) {
                val listaMetas = result.getOrNull() ?: emptyList()
                Log.d("VAQUITA_APP", "Se cargaron correctamente ${listaMetas.size} metas")
                _uiState.value = UiState.Success(listaMetas)
            } else {
                val error = result.exceptionOrNull()?.message ?: "Error desconocido"
                Log.e("VAQUITA_APP", "Error al traer las metas: $error")
                _uiState.value = UiState.Error(error)
            }
        }
    }

    fun createGoal(name: String, amount: String, dueDate: String, urlImage: String) {
        Log.d("VAQUITA_APP", "Intentando crear nueva meta: $name")
        val montoInt = amount.toIntOrNull()
        
        if (name.isBlank() || montoInt == null || montoInt <= 0 || dueDate.isBlank()) {
            Log.w("VAQUITA_APP", "Validación fallida: Campos vacíos o monto inválido")
            _creationState.value = UiState.Error("Por favor, llena todos los campos correctamente")
            return
        }

        _creationState.value = UiState.Loading
        viewModelScope.launch {
            try {
                // Creamos el registro de la imagen directamente con la URL proporcionada (del banco de imágenes)
                val imgResult = repository.createImage(urlImage, "Imagen para $name")
                val imageObject = if (imgResult.isSuccess) imgResult.getOrNull() else null

                val newGoal = Goal(
                    _id = null,
                    name = name,
                    amount = montoInt,
                    dueDate = dueDate,
                    contributions = mutableListOf(),
                    image = imageObject
                )
                
                val result = repository.createGoal(newGoal)
                
                if (result.isSuccess) {
                    Log.d("VAQUITA_APP", "Meta creada con éxito!")
                    _creationState.value = UiState.Success(Unit)
                    getMetas() // Refrescamos la lista automáticamente
                } else {
                    val errorException = result.exceptionOrNull()
                    val errorMsg = errorException?.message ?: "Error al guardar"
                    Log.e("VAQUITA_APP", "Error al crear meta: $errorMsg", errorException)
                    _creationState.value = UiState.Error("Error: $errorMsg")
                }
            } catch (e: Exception) {
                Log.e("VAQUITA_APP", "Excepción al crear meta: ${e.message}")
                _creationState.value = UiState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    fun resetCreationState() {
        Log.d("VAQUITA_APP", "Reseteando el estado de creación")
        _creationState.value = UiState.Idle
    }
}
