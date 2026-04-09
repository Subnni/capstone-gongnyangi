package com.example.gongnyangi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gongnyangi.R

class CreateBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_book)
        //
        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //val navController = navHostFragment.navController
        //
        //// Intent에서 데이터를 꺼냅니다.
        //val startFragment = intent.getStringExtra("start_fragment")
        //
        //if (startFragment == "workbookcreate") {
        //    // startDestination(textbook)을 무시하고 workbookcreate로 이동
        //    navController.navigate(R.id.workbookcreate)
        //}
    }
}