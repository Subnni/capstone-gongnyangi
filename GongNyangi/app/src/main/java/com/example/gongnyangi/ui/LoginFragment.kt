package com.example.gongnyangi.ui

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
import com.example.gongnyangi.network.LoginRequest
import com.example.gongnyangi.network.LoginResponse
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

            checkLogin(phone)
        }
    }

    private fun checkLogin(phone: String) {
        val loginData = LoginRequest(phone)

        // 💡 RetrofitClient.service 호출
        RetrofitClient.service.login(loginData).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()

                    if (result != null && result.success) {
                        val userName = result.user_name ?: "사용자"
                        Toast.makeText(requireContext(), "${userName}님, 환영합니다!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        val message = result?.message ?: "로그인에 실패했습니다."
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "서버 응답 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}