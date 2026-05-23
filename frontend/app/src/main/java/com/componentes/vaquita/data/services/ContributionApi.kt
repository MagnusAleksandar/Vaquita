package com.componentes.vaquita.data.services

import com.componentes.vaquita.domain.model.Contribution
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
        const val GOAL_ID = "goalId"
        const val TEMPLATE = "$BASE/{$GOAL_ID}/contributions/{$CONTRIB_ID}"
    }

    // Crea contribución
    @POST(BASE)
    suspend fun createContribution(@Body contribution: Contribution): Response<Contribution>

    // Obtiene una contribución
    @GET(TEMPLATE)
    suspend fun getOneContribution(
        @Path(GOAL_ID) goalId: String,
        @Path(CONTRIB_ID) contributionId: String
    ): Response<Contribution>

    // Obtiene todas las contribuciones
    @GET(BASE)
    suspend fun getAllContributions(): Response<Contribution>

    // Actualiza contribución
    @PUT(TEMPLATE)
    suspend fun updateContribution(
        @Path(GOAL_ID) goalId: String,
        @Path(CONTRIB_ID) contributionId: String,
        @Body contribution: Contribution
    ): Response<Contribution>

    // Elimina contribución
    @DELETE(TEMPLATE)
    suspend fun deleteContribution(
        @Path(GOAL_ID) goalId: String,
        @Path(CONTRIB_ID) contributionId: String
    ) : Response<Contribution>
}