package com.componentes.vaquita.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.202.52.127:5000/" // IP datos
    //private const val BASE_URL = "http://192.168.0.11:5000/"

    fun <T> create(api: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}