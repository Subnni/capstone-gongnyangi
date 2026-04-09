package com.example.gongnyangi.ui

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

object StatusBarUtils {

    //프래그먼트의 최상위 뷰에 상태바만큼 패딩 부여
    //싱글톤으로 생성하여 공용으로 사용

    public fun addStatusBarPadding(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top

            // 뷰의 원래 패딩 값들을 유지하면서 상단 패딩만 상태바 높이로 설정
            v.setPadding(v.paddingLeft, statusBarHeight, v.paddingRight, v.paddingBottom)

            insets
        }
    }
}