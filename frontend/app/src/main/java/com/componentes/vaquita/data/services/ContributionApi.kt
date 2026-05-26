package com.componentes.vaquita.data.services

import com.componentes.vaquita.dominio.model.Contribution
import com.componentes.vaquita.dominio.model.Goal
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

    // Crea contribución (Retorna la meta actualizada)
    @POST("$BASE/{$GOAL_ID}/contributions")
    suspend fun createContribution(
        @Path(GOAL_ID) goalId: String,
        @Body contribution: Contribution
    ): Goal

    // Obtiene una contribución específica
    @GET("$BASE/{$GOAL_ID}/contributions/{$CONTRIB_ID}")
    suspend fun findById(
        @Path(GOAL_ID) goalId: String,
        @Path(CONTRIB_ID) contributionId: String
    ): Contribution

    // Obtiene todas las contribuciones de una meta
    @GET("$BASE/{$GOAL_ID}/contributions")
    suspend fun findAll(@Path(GOAL_ID) goalId: String): List<Contribution>

    // Actualiza una contribución
    @PUT("$BASE/{$GOAL_ID}/contributions/{$CONTRIB_ID}")
    suspend fun updateContribution(
        @Path(GOAL_ID) goalId: String,
        @Path(CONTRIB_ID) contributionId: String,
        @Body contribution: Contribution
    ): Contribution

    // Elimina una contribución
    @DELETE("$BASE/{$GOAL_ID}/contributions/{$CONTRIB_ID}")
    suspend fun deleteContribution(
        @Path(GOAL_ID) goalId: String,
        @Path(CONTRIB_ID) contributionId: String
    ) : Contribution
}
