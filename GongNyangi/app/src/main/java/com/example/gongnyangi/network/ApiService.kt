package com.example.gongnyangi.network

import com.example.gongnyangi.network.apidata.InputData
import com.example.gongnyangi.network.apidata.MessageResponse
import com.example.gongnyangi.network.apidata.PredictResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/")
    suspend fun test(): Response<MessageResponse>

    @POST("/predict")
    suspend fun predict(
        @Body data : InputData
    ) : Response<PredictResponse>

}