package com.componentes.vaquita.data.services

import com.componentes.vaquita.domain.model.Image
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ImageApi {
    // Constantes
    companion object{
        const val BASE = "api/image"
        const val ID = "mongoId"
        const val TEMPLATE = "$BASE/{$ID}"
    }

    // Crea meta
    @POST(BASE)
    suspend fun createImage(@Body image: Image): Response<Image>

    // Obtiene una meta
    @GET(TEMPLATE)
    suspend fun getOneImage(@Path(ID) id: String): Response<Image>

    // Obtiene todas las metas
    @GET(BASE)
    suspend fun getAllImages(): Response<Image>

    // Actualiza meta
    @PUT(TEMPLATE)
    suspend fun updateImage(
        @Path(ID) id: String,
        @Body image: Image
    ): Response<Image>

    // Elimina meta
    @DELETE(TEMPLATE)
    suspend fun deleteImage(@Path(ID) id: String): Response<Image>
}