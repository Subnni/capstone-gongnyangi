package com.example.gongnyangi.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R

class BookLoadingFragment : Fragment() {

    //플래그 변수
    private var isPopupOpen = false // 현재 팝업이 떠 있는지
    private var isAnalysisFinished = false // API 분석이 끝났는지
    //ui
    private lateinit var cancelBtn : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        cancelBtn = view.findViewById(R.id.cancelBtn)
        cancelBtn.setOnClickListener {
            //다이얼로그 팝업 띄우기
            isPopupOpen = true
            val dialog = BookCancel_DialogFragment()
            dialog.onConfirmClickListener = {
                // 메인 액티비티 - 홈으로 이동
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
            dialog.onDismissListener = {
                isPopupOpen = false
                if (isAnalysisFinished) navigateToNextScreen()
            }
            dialog.show(parentFragmentManager, "SaveDialog")
        }


        // 2초 지연 후 API 응답 왔다고 가정
        Handler(Looper.getMainLooper()).postDelayed({
            onAnalysisComplete()
        }, 4500)
    }

    private fun onAnalysisComplete() {
        isAnalysisFinished = true
        if (!isPopupOpen) {         // 팝업이 떠 있지 않을때만 이동
            navigateToNextScreen()
            //결과(책) 전송하는 api 호출
        }
    }

    private fun navigateToNextScreen() {
        if (isAdded) {
            findNavController().navigate(R.id.gotoBookComplete)
        }
    }
}