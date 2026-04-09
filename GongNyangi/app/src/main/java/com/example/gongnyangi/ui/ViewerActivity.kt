package com.example.gongnyangi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.gongnyangi.R

class ViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)

        val bookId = intent.getIntExtra("BOOK_ID", 0) //기본값 0
        val bookType = intent.getStringExtra("BOOK_TYPE") ?: "TEXTBOOK" //기본값 임의 설정

        //네비게이션 설정
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.viewer_nav_graph)

        //동적으로 화면 설정
        if (bookType == "TEXTBOOK") {
            navGraph.setStartDestination(R.id.ViewTextbookFragment)
        } else if (bookType == "WORKBOOK") {
            navGraph.setStartDestination(R.id.ViewWorkbookFragment)
        }
        val bundle = Bundle().apply {
            putInt("BOOK_ID", bookId)
        }
        navController.setGraph(navGraph, bundle)

    }
}