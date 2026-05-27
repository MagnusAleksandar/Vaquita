package com.componentes.vaquita.data.service

import com.componentes.vaquita.dominio.models.Image
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageApi {
    @POST("api/image")
    suspend fun createImage(@Body image: Image): Image
}
