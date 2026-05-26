package com.componentes.vaquita.dominio.models

import com.google.gson.annotations.SerializedName

data class Image(
    val _id: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("name") val name: String?
)
