package com.componentes.vaquita.data.services

import com.componentes.vaquita.dominio.model.Person
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PersonApi {
    // Constantes
    companion object{
        const val BASE = "api/person"
        const val ID = "mongoId"
        const val TEMPLATE = "$BASE/{$ID}"
    }

    // Crea persona
    @POST(BASE)
    suspend fun createPerson(@Body person: Person): Person

    // Obtiene una persona
    @GET(TEMPLATE)
    suspend fun getOnePerson(@Path(ID) id: String): Person

    // Obtiene todas las personas
    @GET(BASE)
    suspend fun findAll(): List<Person>

    // Actualiza persona
    @PUT(TEMPLATE)
    suspend fun updatePerson(
        @Path(ID) id: String,
        @Body person: Person
    ): Person

    // Elimina persona
    @DELETE(TEMPLATE)
    suspend fun deletePerson(@Path(ID) id: String): Person
}