package com.componentes.vaquita.presentacion.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.data.services.repository.GoalRepository
import com.componentes.vaquita.data.services.repository.PersonRepository
import com.componentes.vaquita.dominio.model.Contribution
import com.componentes.vaquita.dominio.model.Goal
import com.componentes.vaquita.dominio.model.Person
import com.componentes.vaquita.presentacion.ui.states.AporteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import android.util.Log

class AporteViewModel(
    application: Application,
    private val goalRepository: GoalRepository,
    private val personRepository: PersonRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<AporteUiState>(AporteUiState.Idle)
    val uiState: StateFlow<AporteUiState> = _uiState.asStateFlow()

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

    fun registrarAporte(goalId: String, person: Person, monto: String) {
        Log.d("VAQUITA_APP", "Intentando registrar aporte de ${person.persName} a meta $goalId")
        val amountInt = monto.toIntOrNull()
        if (amountInt == null || amountInt <= 0) {
            _uiState.value = AporteUiState.Error("Monto inválido")
            return
        }

        if (goalId.isBlank()) {
            _uiState.value = AporteUiState.Error("Debe seleccionar una meta")
            return
        }

        // Buscamos la meta seleccionada en nuestra lista local para validar el monto
        val metaSeleccionada = _goals.value.find { it._id == goalId }
        if (metaSeleccionada != null) {
            val totalAhorrado = metaSeleccionada.contributions?.sumOf { it.amount ?: 0 } ?: 0
            val montoObjetivo = metaSeleccionada.amount ?: 0
            val restante = montoObjetivo - totalAhorrado

            if (restante <= 0) {
                _uiState.value = AporteUiState.Error("Esta meta ya se ha completado al 100%")
                return
            }

            if (amountInt > restante) {
                _uiState.value = AporteUiState.Error("El monto debe ser menor o igual al valor restante ($$restante)")
                return
            }
        }

        _uiState.value = AporteUiState.Loading
        viewModelScope.launch {
            try {
                val contribution = Contribution(
                    _id = null,
                    contributor = person,
                    amount = amountInt
                )
                val result = goalRepository.addContribution(goalId, contribution)
                
                if (result.isSuccess) {
                    Log.d("VAQUITA_APP", "Aporte registrado correctamente en el servidor")
                    _uiState.value = AporteUiState.Success
                } else {
                    val errorMsg = result.exceptionOrNull()?.message ?: "Error al registrar aporte"
                    Log.e("VAQUITA_APP", "Error al registrar aporte: $errorMsg")
                    _uiState.value = AporteUiState.Error(errorMsg)
                }
            } catch (e: Exception) {
                Log.e("VAQUITA_APP", "Excepción al registrar aporte: ${e.message}")
                _uiState.value = AporteUiState.Error("Error de conexión: ${e.message}")
            }
        }
    }
    
    private val _selectedContribution = MutableStateFlow<Contribution?>(null)
    val selectedContribution: StateFlow<Contribution?> = _selectedContribution.asStateFlow()

    private val _selectedGoalId = MutableStateFlow<String?>(null)
    private val _selectedGoalName = MutableStateFlow<String?>(null)
    val selectedGoalName: StateFlow<String?> = _selectedGoalName.asStateFlow()

    fun seleccionarAporte(contribution: Contribution, goalId: String, goalName: String) {
        Log.d("VAQUITA_APP", "Aporte seleccionado para editar: ${contribution._id}")
        _selectedContribution.value = contribution
        _selectedGoalId.value = goalId
        _selectedGoalName.value = goalName
    }

    fun actualizarAporte(monto: String) {
        val contribution = _selectedContribution.value ?: return
        val goalId = _selectedGoalId.value ?: return
        val amountInt = monto.toIntOrNull() ?: return

        // Validación de tope máximo al editar
        val metaSeleccionada = _goals.value.find { it._id == goalId }
        if (metaSeleccionada != null) {
            val montoObjetivo = metaSeleccionada.amount ?: 0
            // Sumamos todos los aportes menos el que estamos editando actualmente
            val otrosAportes = metaSeleccionada.contributions
                ?.filter { it._id != contribution._id }
                ?.sumOf { it.amount ?: 0 } ?: 0
            
            val restante = montoObjetivo - otrosAportes

            if (amountInt > restante) {
                _uiState.value = AporteUiState.Error("El monto excede el valor restante de la meta ($$restante)")
                return
            }
        }

        Log.d("VAQUITA_APP", "Actualizando monto de aporte a: $amountInt")
        _uiState.value = AporteUiState.Loading
        viewModelScope.launch {
            try {
                val updatedContribution = contribution.copy(amount = amountInt)
                val result = goalRepository.addContribution(goalId, updatedContribution)
                
                if (result.isSuccess) {
                    Log.d("VAQUITA_APP", "Aporte actualizado con éxito")
                    _uiState.value = AporteUiState.Success
                } else {
                    _uiState.value = AporteUiState.Error("Error al actualizar")
                }
            } catch (e: Exception) {
                Log.e("VAQUITA_APP", "Fallo al actualizar: ${e.message}")
                _uiState.value = AporteUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    fun resetState() {
        _uiState.value = AporteUiState.Idle
    }
}
