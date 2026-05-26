package com.componentes.vaquita.data.services

import com.componentes.vaquita.dominio.model.Image
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ImageApi {
    companion object {
        const val BASE = "api/image"
        const val ID = "mongoId"
        const val TEMPLATE = "$BASE/{$ID}"
    }

    @POST(BASE)
    suspend fun createImage(@Body image: Image): Image

    @GET(TEMPLATE)
    suspend fun getOneImage(@Path(ID) id: String): Image

    @GET(BASE)
    suspend fun getAllImages(): List<Image>

    @PUT(TEMPLATE)
    suspend fun updateImage(
        @Path(ID) id: String,
        @Body image: Image
    ): Image

    @DELETE(TEMPLATE)
    suspend fun deleteImage(@Path(ID) id: String): Image
}
