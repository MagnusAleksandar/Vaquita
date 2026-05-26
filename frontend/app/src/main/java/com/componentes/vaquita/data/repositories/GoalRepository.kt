package com.componentes.vaquita.data.repositories

import com.componentes.vaquita.data.service.GoalApi
import com.componentes.vaquita.data.service.ContributionApi
import com.componentes.vaquita.data.service.ImageApi
import com.componentes.vaquita.data.service.RetrofitClient
import com.componentes.vaquita.dominio.models.Goal
import com.componentes.vaquita.dominio.models.Contribution
import com.componentes.vaquita.dominio.models.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoalRepository {
    private val goalApi = RetrofitClient.create(GoalApi::class.java)
    private val contributionApi = RetrofitClient.create(ContributionApi::class.java)
    private val imageApi = RetrofitClient.create(ImageApi::class.java)

    // Obtener todas las metas del servidor
    suspend fun getAllGoals(): Result<List<Goal>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = goalApi.findAll()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Buscar una meta por su ID
    suspend fun getGoalById(id: String): Result<Goal> {
        return withContext(Dispatchers.IO) {
            try {
                val response = goalApi.findById(id)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Crear una nueva meta de ahorro
    suspend fun createGoal(goal: Goal): Result<Goal> {
        return withContext(Dispatchers.IO) {
            try {
                val response = goalApi.createGoal(goal)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Registrar el aporte de un miembro
    suspend fun addContribution(goalId: String, contribution: Contribution): Result<Goal> {
        return withContext(Dispatchers.IO) {
            try {
                val metaActualizada = contributionApi.createContribution(goalId, contribution)
                Result.success(metaActualizada)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Crear registro de imagen
    suspend fun createImage(url: String, name: String): Result<Image> {
        return withContext(Dispatchers.IO) {
            try {
                val response = imageApi.createImage(Image(_id = null, url = url, name = name))
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
