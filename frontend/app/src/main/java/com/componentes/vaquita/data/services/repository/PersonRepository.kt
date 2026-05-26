package com.componentes.vaquita.data.services.repository

import com.componentes.vaquita.data.services.PersonApi
import com.componentes.vaquita.domain.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersonRepository(private val api: PersonApi) {
    suspend fun getAllPersons(): Result<List<Person>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getEveryone()
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error al obtener personas"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun createPerson(person: Person): Result<Person> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.createPerson(person)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error al crear persona"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
