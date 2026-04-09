package com.example.gongnyangi.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R
import com.example.gongnyangi.network.RetrofitInstance
import com.example.gongnyangi.network.apidata.InputData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var testText : TextView
    private lateinit var gotoCreateBook : ImageView
    private lateinit var gotoMyPage : ImageView

    //레이아웃 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(activity as? MainActivity)?.setNavVisible(true)
        //상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        //UI 연결
        gotoCreateBook = view.findViewById(R.id.gotoCreateBook)
        gotoMyPage = view.findViewById(R.id.gotoMyPage)
        testText = view.findViewById(R.id.testText)

        //API 호출 테스트
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.predict(InputData("Hello 서버!"))
                if (response.isSuccessful) {
                    val result = response.body()?.result
                    testText.text = result
                } else {
                    testText.text = "서버 에러: ${response.code()}"
                }
            } catch (e: Exception) {
                testText.text = "연결 실패: ${e.message}"
            }
        }

        //페이지 이동 버튼 리스너 연결
        val currentFragment = parentFragmentManager.findFragmentById(R.id.fragmentContainer)
        gotoMyPage.setOnClickListener {
            findNavController().navigate(R.id.MyPageFragment)

        }
        gotoCreateBook.setOnClickListener {
            val intent = Intent(requireContext(), CreateBookActivity::class.java)
            startActivity(intent)
        }

    }

}