package com.componentes.vaquita.data.services

import com.componentes.vaquita.domain.model.Contribution
import com.componentes.vaquita.domain.model.Goal
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContributionApi {
    // Constantes
    companion object{
        const val BASE = "api/goal"
        const val CONTRIB_ID = "contributionId"
        const val GOAL_ID = "mongoId"
    }

    // Crea contribución
    @POST("$BASE/{$GOAL_ID}/contributions")
    suspend fun createContribution(
        @Path(GOAL_ID) goalId: String,
        @Body contribution: Contribution
    ): Response<Goal>

    // Obtiene una contribución
    @GET("$BASE/{$GOAL_ID}/contributions/{$CONTRIB_ID}")
    suspend fun getOneContribution(
        @Path(GOAL_ID) goalId: String,
        @Path(CONTRIB_ID) contributionId: String
    ): Response<Contribution>

    // Obtiene todas las contribuciones
    @GET("$BASE/{$GOAL_ID}/contributions")
    suspend fun getAllContributions(@Path(GOAL_ID) goalId: String): Response<List<Contribution>>

    // Actualiza contribución
    @PUT("$BASE/{$GOAL_ID}/contributions/{$CONTRIB_ID}")
    suspend fun updateContribution(
        @Path(GOAL_ID) goalId: String,
        @Path(CONTRIB_ID) contributionId: String,
        @Body contribution: Contribution
    ): Response<Contribution>

    // Elimina contribución
    @DELETE("$BASE/{$GOAL_ID}/contributions/{$CONTRIB_ID}")
    suspend fun deleteContribution(
        @Path(GOAL_ID) goalId: String,
        @Path(CONTRIB_ID) contributionId: String
    ) : Response<Contribution>
}
