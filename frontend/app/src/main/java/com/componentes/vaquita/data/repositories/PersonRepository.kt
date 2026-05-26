package com.componentes.vaquita.data.repositories

import com.componentes.vaquita.data.service.PersonApi
import com.componentes.vaquita.data.service.RetrofitClient
import com.componentes.vaquita.dominio.models.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersonRepository {
    private val api = RetrofitClient.create(PersonApi::class.java)

    // Traer a todos los miembros registrados
    suspend fun getAllPersons(): Result<List<Person>> {
        return withContext(Dispatchers.IO) {
            try {
                // Llamamos directamente a la API
                val lista = api.findAll()
                Result.success(lista)
            } catch (e: Exception) {
                // Si hay error (ej: no hay internet), devolvemos el fallo
                Result.failure(e)
            }
        }
    }

    // Crear un nuevo miembro (persona)
    suspend fun createPerson(person: Person): Result<Person> {
        return withContext(Dispatchers.IO) {
            try {
                val nuevaPersona = api.createPerson(person)
                Result.success(nuevaPersona)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
