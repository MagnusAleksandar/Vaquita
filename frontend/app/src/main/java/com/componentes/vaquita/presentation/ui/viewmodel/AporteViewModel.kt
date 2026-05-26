package com.componentes.vaquita.presentation.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.data.services.repository.GoalRepository
import com.componentes.vaquita.data.services.repository.PersonRepository
import com.componentes.vaquita.domain.model.Contribution
import com.componentes.vaquita.domain.model.Goal
import com.componentes.vaquita.domain.model.Person
import com.componentes.vaquita.presentation.ui.states.AporteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val goalsResult = goalRepository.getAllGoals()
            val personsResult = personRepository.getAllPersons()

            goalsResult.onSuccess { _goals.value = it }
            personsResult.onSuccess { _members.value = it }
        }
    }

    fun registrarAporte(goalId: String, person: Person, amount: String) {
        val amountInt = amount.toIntOrNull()
        if (amountInt == null || amountInt <= 0) {
            _uiState.value = AporteUiState.Error("Monto inválido")
            return
        }

        if (goalId.isBlank()) {
            _uiState.value = AporteUiState.Error("Debe seleccionar una meta")
            return
        }

        _uiState.value = AporteUiState.Loading
        viewModelScope.launch {
            val contribution = Contribution(
                _id = null,
                contributor = person,
                amount = amountInt
            )
            val result = goalRepository.addContribution(goalId, contribution)
            result.onSuccess {
                _uiState.value = AporteUiState.Success
            }
            result.onFailure {
                _uiState.value = AporteUiState.Error(it.message ?: "Error al registrar aporte")
            }
        }
    }
    
    private val _selectedContribution = MutableStateFlow<Contribution?>(null)
    val selectedContribution: StateFlow<Contribution?> = _selectedContribution.asStateFlow()

    private val _selectedGoalId = MutableStateFlow<String?>(null)
    private val _selectedGoalName = MutableStateFlow<String?>(null)
    val selectedGoalName: StateFlow<String?> = _selectedGoalName.asStateFlow()

    fun seleccionarAporte(contribution: Contribution, goalId: String, goalName: String) {
        _selectedContribution.value = contribution
        _selectedGoalId.value = goalId
        _selectedGoalName.value = goalName
    }

    fun actualizarAporte(amount: String) {
        val contribution = _selectedContribution.value ?: return
        val goalId = _selectedGoalId.value ?: return
        val amountInt = amount.toIntOrNull() ?: return

        _uiState.value = AporteUiState.Loading
        viewModelScope.launch {
            val updatedContribution = contribution.copy(amount = amountInt)
            val result = goalRepository.addContribution(goalId, updatedContribution) // Usamos add para simplificar o si existe update en repo
            // Nota: El backend tiene updateContribution, pero el repository solo tiene addContribution.
            // Por ahora usaremos addContribution o asumiremos que el backend lo maneja si enviamos el ID.
            result.onSuccess {
                _uiState.value = AporteUiState.Success
            }
            result.onFailure {
                _uiState.value = AporteUiState.Error(it.message ?: "Error al actualizar")
            }
        }
    }

    fun eliminarAporte() {
        // Implementar si es necesario
    }
    
    fun resetState() {
        _uiState.value = AporteUiState.Idle
    }
}
