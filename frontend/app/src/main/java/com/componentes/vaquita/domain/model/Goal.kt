package com.componentes.vaquita.domain.model

import com.google.gson.annotations.SerializedName


data class Goal(val _id: String?,
                @SerializedName("name") var name: String?,
                var amount: Int?,
                var dueDate: String?,
                var contributions: MutableList<Contribution>?,
                var image: String?)