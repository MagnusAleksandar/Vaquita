package com.componentes.vaquita.presentation.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.data.services.repository.PersonRepository
import com.componentes.vaquita.domain.model.Person
import com.componentes.vaquita.presentation.ui.states.MiembrosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

    fun getPersons() {
        _miembrosState.value = MiembrosUiState.Loading
        viewModelScope.launch {
            val result = repository.getAllPersons()
            result.onSuccess {
                _miembrosState.value = MiembrosUiState.Success(it)
            }
            result.onFailure {
                _miembrosState.value = MiembrosUiState.Error(it.message ?: "Error al obtener miembros")
            }
        }
    }

    fun createPerson(name: String, idNum: String, phone: String) {
        if (name.isBlank() || idNum.isBlank() || phone.isBlank()) {
            _uiState.value = PersonUiState.Error("Todos los campos son obligatorios")
            return
        }

        _uiState.value = PersonUiState.Loading
        viewModelScope.launch {
            val person = Person(
                _id = null,
                persName = name,
                idNum = idNum,
                persPhone = phone
            )
            val result = repository.createPerson(person)
            result.onSuccess {
                _uiState.value = PersonUiState.Success
            }
            result.onFailure {
                _uiState.value = PersonUiState.Error(it.message ?: "Error al crear persona")
            }
        }
    }

    fun resetState() {
        _uiState.value = PersonUiState.Idle
    }
}
