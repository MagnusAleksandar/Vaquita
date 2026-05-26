package com.componentes.vaquita.data.services.repository

import com.componentes.vaquita.data.services.GoalApi
import com.componentes.vaquita.data.services.ContributionApi
import com.componentes.vaquita.data.services.ImageApi
import com.componentes.vaquita.dominio.model.Goal
import com.componentes.vaquita.dominio.model.Contribution
import com.componentes.vaquita.dominio.model.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoalRepository(
    private val goalApi: GoalApi,
    private val contributionApi: ContributionApi? = null,
    private val imageApi: ImageApi? = null
) {
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
                if (contributionApi == null) {
                    throw Exception("El servicio de aportes no está disponible")
                }
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
                if (imageApi == null) throw Exception("Servicio de imágenes no disponible")
                val response = imageApi.createImage(Image(_id = null, url = url, name = name))
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
