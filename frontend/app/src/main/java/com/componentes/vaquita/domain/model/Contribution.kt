package com.example.vaquita.model

import com.componentes.vaquita.domain.model.Person

data class Contribution(val _id: String?, val contributor: Person, val amount: Int)
