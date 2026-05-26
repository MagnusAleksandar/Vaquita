package com.componentes.vaquita.dominio.model

data class Contribution(
    val _id: String?,
    val contributor: Person?,
    val amount: Int?,
    val createdAt: String? = null
)
