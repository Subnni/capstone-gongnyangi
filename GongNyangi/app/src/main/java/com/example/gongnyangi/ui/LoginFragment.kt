package com.example.gongnyangi.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R
import com.example.gongnyangi.network.RetrofitClient  // 이 경로가 맞는지 확인!
import com.example.gongnyangi.network.apidata.LoginRequest
import com.example.gongnyangi.network.apidata.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var phoneEditText: EditText
    private lateinit var loginButton: LinearLayout
    private lateinit var gotoSignUpTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phoneEditText = view.findViewById(R.id.PhoneLogin)
        loginButton = view.findViewById(R.id.gotoMainLayout)
        gotoSignUpTextView = view.findViewById(R.id.gotoSignUpTextView)

        // 회원가입 페이지 이동
        gotoSignUpTextView.setOnClickListener {
            findNavController().navigate(R.id.gotoSignUp)
        }

        // 로그인 버튼 클릭 시
        loginButton.setOnClickListener {
            val phone = phoneEditText.text.toString()

            if (phone.isEmpty()) {
                Toast.makeText(requireContext(), "전화번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //임시로 주석처리
            checkLogin(phone)

            //임시 코드####로그인 검사 시 주석 처리 필수##########################최종 삭제 필요
            //val intent = Intent(requireContext(), MainActivity::class.java)
            //startActivity(intent)
            //activity?.finish()
        }
    }

    //API 호출 테스트
    //lifecycleScope.launch {
    //    try {
    //        val response = RetrofitInstance.api.predict(InputData("Hello 서버!"))
    //        if (response.isSuccessful) {
    //            val result = response.body()?.result
    //            testText.text = result
    //        } else {
    //            testText.text = "서버 에러: ${response.code()}"
    //        }
    //    } catch (e: Exception) {
    //        testText.text = "연결 실패: ${e.message}"
    //    }
    //}

    //로그인 검사
    private fun checkLogin(phone: String) {
        val loginData = LoginRequest(phone)

        // 💡 RetrofitClient.service 호출
        RetrofitClient.api.login(loginData).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()

                    if (result != null && result.success) {
                        val userId = result.userId
                        saveData(userId)

                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.putExtra("user_id", userId)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        val message =  "해당하는 전화번호가 없습니다."
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //사용자 정보 저장(userId)
    private fun saveData(userId : Int){
        var sharedPref = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putInt("USER_ID", userId).apply()
    }
}