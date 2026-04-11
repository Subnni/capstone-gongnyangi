package com.example.gongnyangi.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.gongnyangi.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 2초 지연 이후 액티비티 이동
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)

            // 이전 키 눌렀을 때 스플래시 스크린 화면으로 이동을 방지하기 위해
            // 이동 후 사용 안함 처리
            finish()

        }, 3000) //깃허브 연동 테스트용으로 서진 2000->3000으로 수정했습니다! 요부분 바뀌었는지 확인 부탁드립니다!!
    }
}