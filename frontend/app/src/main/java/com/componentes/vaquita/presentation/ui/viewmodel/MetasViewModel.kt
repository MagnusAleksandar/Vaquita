package com.componentes.vaquita.presentation.ui.viewmodel

import com.componentes.vaquita.data.services.repository.GoalRepository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.vaquita.domain.model.Goal
import com.componentes.vaquita.presentation.ui.states.MetasUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
             _uiState.value = MetasUiState.Loading
             viewModelScope.launch {
                 val result = repository.getAllGoals()
                      result.onSuccess { goals ->
                          _uiState.value = MetasUiState.Success(goals)
                      }
                 result.onFailure { exception ->
                     _uiState.value = MetasUiState.Error(exception.message ?: "Error desconocido")
                 }
             }
         }

         fun createGoal(name: String, amount: String) {
             val amountInt = amount.toIntOrNull()
             if (name.isBlank() || amountInt == null || amountInt <= 0) {
                 _creationState.value = GoalCreationState.Error("Datos inválidos")
                 return
             }

             _creationState.value = GoalCreationState.Loading
             viewModelScope.launch {
                 val goal = Goal(
                     _id = null,
                     name = name,
                     amount = amountInt,
                     dueDate = "2026-12-31", // Fecha por defecto
                     contributions = mutableListOf(),
                     image = null
                 )
                 val result = repository.createGoal(goal)
                 result.onSuccess {
                     _creationState.value = GoalCreationState.Success
                     getMetas() // Recargar lista
                 }
                 result.onFailure {
                     _creationState.value = GoalCreationState.Error(it.message ?: "Error al crear meta")
                 }
             }
         }

         fun resetCreationState() {
             _creationState.value = GoalCreationState.Idle
         }
}
