package com.example.gongnyangi.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.gongnyangi.R
import com.example.gongnyangi.network.RetrofitInstance

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //intent 안 데이터 따라 프래그먼트 이동
        //val target = intent.getStringExtra("targetFragment")
        //when (target) {
        //    "Bookcase" -> navController.navigate(R.id.BookcaseFragment)
        //    "Closet" -> navController.navigate(R.id.ClosetFragment)
        //    "Mistake" -> navController.navigate(R.id.MistakeNoteFragment)
        //}
    }
}