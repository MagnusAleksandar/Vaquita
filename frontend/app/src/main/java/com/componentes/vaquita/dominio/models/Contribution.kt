package com.componentes.vaquita.dominio.models

data class Contribution(
    val _id: String?,
    val contributor: Person?,
    val amount: Int?,
    val description: String? = null,
    val createdAt: String? = null
)
