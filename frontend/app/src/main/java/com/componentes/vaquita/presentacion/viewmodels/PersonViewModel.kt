package com.componentes.vaquita.presentacion.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.data.repositories.PersonRepository
import com.componentes.vaquita.dominio.models.Person
import com.componentes.vaquita.dominio.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class PersonViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = PersonRepository()

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState.asStateFlow()

    private val _miembrosState = MutableStateFlow<UiState<List<Person>>>(UiState.Idle)
    val miembrosState: StateFlow<UiState<List<Person>>> = _miembrosState.asStateFlow()

    // Obtener la lista de todos los miembros desde el repositorio
    fun getPersons() {
        Log.d("VAQUITA_APP", "Cargando lista de miembros...")
        _miembrosState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.getAllPersons()
            
            if (result.isSuccess) {
                val lista = result.getOrNull() ?: emptyList()
                Log.d("VAQUITA_APP", "Se obtuvieron ${lista.size} miembros exitosamente")
                _miembrosState.value = UiState.Success(lista)
            } else {
                val error = result.exceptionOrNull()?.message ?: "Error al obtener miembros"
                Log.e("VAQUITA_APP", "Fallo al cargar miembros: $error")
                _miembrosState.value = UiState.Error(error)
            }
        }
    }

    // Crear un nuevo miembro con los datos del formulario
    fun createPerson(nombre: String, cedula: String, telefono: String) {
        Log.d("VAQUITA_APP", "Intentando registrar a: $nombre")
        if (nombre.isBlank() || cedula.isBlank() || telefono.isBlank()) {
            Log.w("VAQUITA_APP", "Validación fallida: campos incompletos")
            _uiState.value = UiState.Error("Todos los campos son obligatorios")
            return
        }

        _uiState.value = UiState.Loading
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
                    _uiState.value = UiState.Success(Unit)
                } else {
                    val errorMsg = result.exceptionOrNull()?.message ?: "Error al crear persona"
                    Log.e("VAQUITA_APP", "Error devuelto por el repositorio: $errorMsg")
                    _uiState.value = UiState.Error(errorMsg)
                }
            } catch (e: Exception) {
                Log.e("VAQUITA_APP", "Excepción al crear persona: ${e.message}")
                _uiState.value = UiState.Error("Error de red: ${e.message}")
            }
        }
    }

    fun resetState() {
        Log.d("VAQUITA_APP", "Reseteando estado de PersonViewModel")
        _uiState.value = UiState.Idle
    }
}
