package com.componentes.vaquita

import com.componentes.vaquita.dominio.models.Contribution
import com.componentes.vaquita.dominio.models.Goal
import com.componentes.vaquita.dominio.models.Person
import org.junit.Assert.assertEquals
import org.junit.Test

class GoalProgressTest {

    @Test
    fun `calculo de ahorro total es correcto`() {
        // 1. Preparación (Given)
        val persona = Person(_id = "1", persName = "David", idNum = "123", persPhone = "321")
        val contribuciones = mutableListOf(
            Contribution(_id = "a", contributor = persona, amount = 100000),
            Contribution(_id = "b", contributor = persona, amount = 150000)
        )
        val meta = Goal(
            _id = "goal1",
            name = "Televisor",
            amount = 500000,
            dueDate = "2026-12-31",
            contributions = contribuciones,
            image = com.componentes.vaquita.dominio.models.Image(_id = "img1", url = "url_imagen", name = "Test Image")
        )

        // 2. Ejecución (When)
        val totalAhorrado = meta.contributions?.sumOf { it.amount ?: 0 } ?: 0

        // 3. Verificación (Then)
        // El total debería ser 250,000
        assertEquals(250000, totalAhorrado)
    }

    @Test
    fun `porcentaje de progreso es correcto`() {
        val totalAhorrado = 1200000
        val valorObjetivo = 3000000
        
        val porcentaje = (totalAhorrado.toFloat() / valorObjetivo.toFloat()) * 100
        
        assertEquals(40f, porcentaje, 0.1f)
    }
}
