package com.example.gongnyangi.network.apidata

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    fun signUp(@Body user: SignUpRequest): Call<ServerResponse>

    @POST("login")
    fun login(@Body phone: LoginRequest): Call<LoginResponse>
}

//
//interface ApiService {
//
////    @GET("/")
////    suspend fun test(): Response<MessageResponse>
//
//    @POST("/predict")
//    suspend fun predict(
//        @Body data : InputData
//    ) : Response<PredictResponse>
//
//    // 회원가입 경로를 서버 API 설계에 맞게 수정하세요 (예: /signup)
//    @POST("signup")
//    fun signUp(@Body user: UserRequest): Call<ServerResponse>
////    suspend fun registerUser(
////        @Body userInfo: UserRequest
////    ): Response<ServerResponse>
//
//    @POST("login")
//    fun login(@Body phone: LoginRequest): Call<LoginResponse>
//}
//
//data class ServerResponse(val success: Boolean, val message: String)
//
//// --- 데이터 모델 클래스들 ---
//
//data class UserRequest(
//    val user_name: String, // XML의 이름
//    val phone: String,      // XML의 전화번호
//    val school_level: String,// 학교급 (중학교 default로 설정)
//    val grade_level: String,     // XML의 학년
//    val user_score: Int? = 0 // 간식점수 (초기값 0)
//)
//
//data class ServerResponse(
//    val success: Boolean,
//    val message: String
//)
//
//data class LoginRequest(val phone: String)
//data class LoginResponse(
//    val success: Boolean,
//    val message: String,
//    val user_name: String? = null
//)