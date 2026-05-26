package com.componentes.vaquita.dominio.model

data class Goal(
    val _id: String?,
    var name: String?,
    var amount: Int?,
    var dueDate: String?,
    var contributions: MutableList<Contribution>?,
    var image: String?
)
