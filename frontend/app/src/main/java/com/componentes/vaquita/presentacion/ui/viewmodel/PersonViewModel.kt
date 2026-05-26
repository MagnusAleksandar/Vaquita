package com.componentes.vaquita.presentacion.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.data.services.repository.PersonRepository
import com.componentes.vaquita.dominio.model.Person
import com.componentes.vaquita.presentacion.ui.states.MiembrosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

sealed class PersonUiState {
    object Idle : PersonUiState()
    object Loading : PersonUiState()
    object Success : PersonUiState()
    data class Error(val message: String) : PersonUiState()
}


class PersonViewModel(
    application: Application,
    private val repository: PersonRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<PersonUiState>(PersonUiState.Idle)
    val uiState: StateFlow<PersonUiState> = _uiState.asStateFlow()

    private val _miembrosState = MutableStateFlow<MiembrosUiState>(MiembrosUiState.Idle)
    val miembrosState: StateFlow<MiembrosUiState> = _miembrosState.asStateFlow()

    // Obtener la lista de todos los miembros desde el repositorio
    fun getPersons() {
        Log.d("VAQUITA_APP", "Cargando lista de miembros...")
        _miembrosState.value = MiembrosUiState.Loading
        viewModelScope.launch {
            val result = repository.getAllPersons()
            
            if (result.isSuccess) {
                val lista = result.getOrNull() ?: emptyList()
                Log.d("VAQUITA_APP", "Se obtuvieron ${lista.size} miembros exitosamente")
                _miembrosState.value = MiembrosUiState.Success(lista)
            } else {
                val error = result.exceptionOrNull()?.message ?: "Error al obtener miembros"
                Log.e("VAQUITA_APP", "Fallo al cargar miembros: $error")
                _miembrosState.value = MiembrosUiState.Error(error)
            }
        }
    }

    // Crear un nuevo miembro con los datos del formulario
    fun createPerson(nombre: String, cedula: String, telefono: String) {
        Log.d("VAQUITA_APP", "Intentando registrar a: $nombre")
        if (nombre.isBlank() || cedula.isBlank() || telefono.isBlank()) {
            Log.w("VAQUITA_APP", "Validación fallida: campos incompletos")
            _uiState.value = PersonUiState.Error("Todos los campos son obligatorios")
            return
        }

        _uiState.value = PersonUiState.Loading
        viewModelScope.launch {
            try {
                val person = Person(
                    _id = null,
                    persName = nombre,
                    idNum = cedula,
                    persPhone = telefono
                )
                val result = repository.createPerson(person)
                
                if (result.isSuccess) {
                    Log.d("VAQUITA_APP", "Miembro creado con éxito en el servidor")
                    _uiState.value = PersonUiState.Success
                } else {
                    val errorMsg = result.exceptionOrNull()?.message ?: "Error al crear persona"
                    Log.e("VAQUITA_APP", "Error devuelto por el repositorio: $errorMsg")
                    _uiState.value = PersonUiState.Error(errorMsg)
                }
            } catch (e: Exception) {
                Log.e("VAQUITA_APP", "Excepción al crear persona: ${e.message}")
                _uiState.value = PersonUiState.Error("Error de red: ${e.message}")
            }
        }
    }

    fun resetState() {
        Log.d("VAQUITA_APP", "Reseteando estado de PersonViewModel")
        _uiState.value = PersonUiState.Idle
    }
}
