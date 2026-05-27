package com.componentes.vaquita.data.service

import com.componentes.vaquita.dominio.models.Person
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PersonApi {
    @POST("api/person")
    suspend fun createPerson(@Body person: Person): Person

    @GET("api/person")
    suspend fun findAll(): List<Person>
}
