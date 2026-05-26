package com.componentes.vaquita.data.services

import com.componentes.vaquita.dominio.model.Goal
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GoalApi {
    // Constantes
    companion object{
        const val BASE = "api/goal"
        const val ID = "goalId"
        const val TEMPLATE = "$BASE/{$ID}"
    }

    // Crea meta
    @POST(BASE)
    suspend fun createGoal(@Body goal: Goal): Goal

    // Obtiene una meta
    @GET(TEMPLATE)
    suspend fun findById(@Path(ID) id: String): Goal

    // Obtiene todas las metas
    @GET(BASE)
    suspend fun findAll(): List<Goal>

    // Actualiza meta
    @PUT(TEMPLATE)
    suspend fun updateGoal(
        @Path(ID) id: String,
        @Body goal: Goal
    ): Goal

    // Elimina meta
    @DELETE(TEMPLATE)
    suspend fun deleteGoal(@Path(ID) id: String): Goal
}