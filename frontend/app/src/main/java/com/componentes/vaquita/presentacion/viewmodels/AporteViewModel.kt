package com.componentes.vaquita.presentacion.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.data.repositories.GoalRepository
import com.componentes.vaquita.data.repositories.PersonRepository
import com.componentes.vaquita.dominio.models.Contribution
import com.componentes.vaquita.dominio.models.Goal
import com.componentes.vaquita.dominio.models.Person
import com.componentes.vaquita.dominio.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import android.util.Log

class AporteViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val goalRepository = GoalRepository()
    private val personRepository = PersonRepository()

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState.asStateFlow()

    private val _members = MutableStateFlow<List<Person>>(emptyList())
    val members: StateFlow<List<Person>> = _members.asStateFlow()

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals: StateFlow<List<Goal>> = _goals.asStateFlow()

    init {
        Log.d("VAQUITA_APP", "AporteViewModel iniciado, cargando datos iniciales...")
        loadData()
    }

    fun loadData() {
        Log.d("VAQUITA_APP", "Refrescando lista de metas y miembros...")
        viewModelScope.launch {
            // Cargamos metas
            val goalsResult = goalRepository.getAllGoals()
            if (goalsResult.isSuccess) {
                val listaMetas = goalsResult.getOrNull() ?: emptyList()
                Log.d("VAQUITA_APP", "Metas cargadas: ${listaMetas.size}")
                _goals.value = listaMetas
            } else {
                Log.e("VAQUITA_APP", "Error al cargar metas para aportes")
            }

            // Cargamos personas (miembros)
            val personsResult = personRepository.getAllPersons()
            if (personsResult.isSuccess) {
                val listaMiembros = personsResult.getOrNull() ?: emptyList()
                Log.d("VAQUITA_APP", "Miembros cargados para aportes: ${listaMiembros.size}")
                _members.value = listaMiembros
            } else {
                Log.e("VAQUITA_APP", "Error al cargar miembros para aportes")
            }
        }
    }

    fun registrarAporte(goalId: String, person: Person, monto: String, descripcion: String) {
        Log.d("VAQUITA_APP", "Intentando registrar aporte de ${person.persName} a meta $goalId")
        val amountInt = monto.toIntOrNull()
        if (amountInt == null || amountInt <= 0) {
            _uiState.value = UiState.Error("Monto inválido")
            return
        }

        if (goalId.isBlank()) {
            _uiState.value = UiState.Error("Debe seleccionar una meta")
            return
        }

        // Buscamos la meta seleccionada en nuestra lista local para validar el monto
        val metaSeleccionada = _goals.value.find { it._id == goalId }
        if (metaSeleccionada != null) {
            val totalAhorrado = metaSeleccionada.contributions?.sumOf { it.amount ?: 0 } ?: 0
            val montoObjetivo = metaSeleccionada.amount ?: 0
            val restante = montoObjetivo - totalAhorrado

            if (restante <= 0) {
                _uiState.value = UiState.Error("Esta meta ya se ha completado al 100%")
                return
            }

            if (amountInt > restante) {
                _uiState.value = UiState.Error("El monto debe ser menor o igual al valor restante ($$restante)")
                return
            }
        }

        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val contribution = Contribution(
                    _id = null,
                    contributor = person,
                    amount = amountInt,
                    description = descripcion
                )
                val result = goalRepository.addContribution(goalId, contribution)
                
                if (result.isSuccess) {
                    Log.d("VAQUITA_APP", "Aporte registrado correctamente en el servidor")
                    _uiState.value = UiState.Success(Unit)
                } else {
                    val errorMsg = result.exceptionOrNull()?.message ?: "Error al registrar aporte"
                    Log.e("VAQUITA_APP", "Error al registrar aporte: $errorMsg")
                    _uiState.value = UiState.Error(errorMsg)
                }
            } catch (e: Exception) {
                Log.e("VAQUITA_APP", "Excepción al registrar aporte: ${e.message}")
                _uiState.value = UiState.Error("Error de conexión: ${e.message}")
            }
        }
    }
    
    fun resetState() {
        _uiState.value = UiState.Idle
    }
}
