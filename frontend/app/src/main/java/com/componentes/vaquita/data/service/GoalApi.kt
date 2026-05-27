package com.componentes.vaquita.data.service

import com.componentes.vaquita.dominio.models.Goal
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoalApi {
    @POST("api/goal")
    suspend fun createGoal(@Body goal: Goal): Goal

    @GET("api/goal/{goalId}")
    suspend fun findById(@Path("goalId") id: String): Goal

    @GET("api/goal")
    suspend fun findAll(): List<Goal>
}
