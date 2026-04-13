package com.example.gongnyangi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofit을 이용한 클라이언트 작업용 클래스
object RetrofitClient {
    private val BASE_URL = "http://10.0.2.2:8000/"

    val service: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}