package com.example.gongnyangi.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R
import com.example.gongnyangi.network.RetrofitClient
import com.example.gongnyangi.network.UserRequest
import com.example.gongnyangi.network.ServerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {

    private lateinit var gotoLogin : ImageView
    private lateinit var gotoWelcomeLayout : LinearLayout
    private lateinit var editUserName: EditText
    private lateinit var editPassword: EditText
    private lateinit var editCatName: EditText
    private lateinit var spinnerGrade: Spinner
    private lateinit var editPhone: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 상단 상태바 높이만큼 패딩 부여 (StatusBarUtils 클래스가 프로젝트 내에 있어야 함)
        StatusBarUtils.addStatusBarPadding(view)

        // UI 연결
        gotoLogin = view.findViewById(R.id.gotoLogin)
        gotoWelcomeLayout = view.findViewById(R.id.gotoWelcomeLayout)
        editUserName = view.findViewById(R.id.editUserName)
        editPassword = view.findViewById(R.id.editPassword)
        editCatName = view.findViewById(R.id.editCatName)
        spinnerGrade = view.findViewById(R.id.spinnerGrade)
        editPhone = view.findViewById(R.id.editPhone)

//        // 학년 스피너 어댑터 설정
//        val grades = arrayOf("1학년", "2학년", "3학년", "4학년", "5학년","6학년")
//        val g_adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, grades)
//        g_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerGrade.adapter = g_adapter

        // 뒤로가기(로그인 화면으로 이동) 버튼
        gotoLogin.setOnClickListener {
            findNavController().navigate(R.id.gotoLogin)
        }

        // 회원가입 버튼 클릭 시
        gotoWelcomeLayout.setOnClickListener {
            val userName = editUserName.text.toString().trim()
            val pw = editPassword.text.toString().trim()
            val grade = spinnerGrade.selectedItem.toString()
            val phone = editPhone.text.toString().trim()

            // 1. 필수 입력 확인
            if (userName.isEmpty() || pw.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. 서버 전송용 데이터 생성 (UserRequest는 ApiService.kt에 정의된 것 사용)
            val requestData = UserRequest(
                user_name = userName,
                grade_level = grade,
                phone = phone,
                school_level = "중학교", // 현재는 고정값, 필요시 수정 가능
                user_score = 0
            )

            // 3. 서버 통신 시작
            Log.d("SignUp", "서버 통신 시도 중...")
            RetrofitClient.service.signUp(requestData).enqueue(object : Callback<ServerResponse> {
                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body?.success == true) {
                            // ✅ 회원가입 성공
                            Toast.makeText(requireContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.gotoWelcome)
                        } else {
                            // ❌ 서버에서 거절 (이미 가입된 번호 등)
                            val errorMsg = body?.message ?: "회원가입에 실패했습니다."
                            Log.e("SignUp", "실패 원인: $errorMsg")
                            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // ❌ 서버 에러 (422 미싱 필드 등)
                        val errorBody = response.errorBody()?.string()
                        Log.e("SignUp", "서버 응답 에러: $errorBody")
                        Toast.makeText(requireContext(), "서버 데이터 처리 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    // 🌐 네트워크 에러
                    Log.e("SignUp", "통신 실패: ${t.message}")
                    Toast.makeText(requireContext(), "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}