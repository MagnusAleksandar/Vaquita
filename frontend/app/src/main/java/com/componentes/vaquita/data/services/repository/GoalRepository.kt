package com.componentes.vaquita.data.services.repository

import com.componentes.vaquita.data.services.GoalApi
import com.componentes.vaquita.data.services.ContributionApi
import com.componentes.vaquita.domain.model.Goal
import com.componentes.vaquita.domain.model.Contribution
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoalRepository(
    private val goalApi: GoalApi,
    private val contributionApi: ContributionApi? = null
) {
    suspend fun getAllGoals(): Result<List<Goal>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = goalApi.getAllGoals()
                if(response.isSuccessful && response.body() != null){
                    Result.success(response.body()!!)
                }else{
                    Result.failure(Exception("Error al obtener metas"))
                }
            }catch (e: Exception){
                Result.failure(e)
            }
        }
    }

    suspend fun getGoalById(id: String): Result<Goal> {
        return withContext(Dispatchers.IO){
            try{
                val response = goalApi.getOneGoal(id)
                if (response.isSuccessful && response.body() != null){
                    Result.success( response.body()!!)
                }else{
                    Result.failure(Exception("No se encontró la meta"))
                }
            }catch (e:Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun createGoal(goal: Goal): Result<Goal> {
        return withContext(Dispatchers.IO) {
            try {
                val response = goalApi.createGoal(goal)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error al crear meta"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun addContribution(goalId: String, contribution: Contribution): Result<Goal> {
        return withContext(Dispatchers.IO) {
            try {
                if (contributionApi == null) {
                    return@withContext Result.failure(Exception("Servicio de aportes no disponible"))
                }
                val response = contributionApi.createContribution(goalId, contribution)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Error desconocido en el servidor"
                    Result.failure(Exception("Error al registrar el aporte: $errorMsg"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error de conexión: ${e.message}"))
            }
        }
    }
}
