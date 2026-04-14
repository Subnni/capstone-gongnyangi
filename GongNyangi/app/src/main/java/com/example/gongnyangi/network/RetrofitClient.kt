package com.example.gongnyangi.network

import com.example.gongnyangi.network.apidata.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofit을 이용한 클라이언트 작업용 클래스

//Retrofit 객체 생성 파일
//싱글톤으로 만들어 재사용

object RetrofitClient {

    //서버 기본 주소
    //앤드포인트 호출 시 뒤에 url이 붙는다
    private const val BASE_URL = "http://10.0.2.2:8000/"

    //레트로핏 객체 만들어 instance 변수에 저장
    //by lazy = 처음에만 생성(메모리 효율)
    val retrofit : Retrofit by lazy{
        //레트로핏 객체 생성
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}