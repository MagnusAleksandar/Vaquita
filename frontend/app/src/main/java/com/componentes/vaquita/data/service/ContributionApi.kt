package com.componentes.vaquita.data.service

import com.componentes.vaquita.dominio.models.Contribution
import com.componentes.vaquita.dominio.models.Goal
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ContributionApi {
    @POST("api/goal/{mongoId}/contributions")
    suspend fun createContribution(
        @Path("mongoId") goalId: String,
        @Body contribution: Contribution
    ): Goal
}
