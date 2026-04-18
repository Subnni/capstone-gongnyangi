package com.example.gongnyangi.ui

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R
import com.example.gongnyangi.network.RetrofitClient
import com.example.gongnyangi.network.apidata.ServerResponse
import com.example.gongnyangi.network.apidata.SignUpRequest
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {

    //타이머
    private var timerJob : Job? = null
    //인증 여부 flag
    private var isVerified : Boolean = false

    //UI 연결용 변수
    private lateinit var gotoLogin : ImageView
    private lateinit var gotoWelcomeLayout : Button
    private lateinit var editUserName: EditText

    private lateinit var spinnerSchool : Spinner
    private lateinit var spinnerGrade: Spinner
    private lateinit var editPhone: EditText
    private lateinit var authButton : Button
    private lateinit var row4 : ImageView
    private lateinit var editVerifyCode : EditText
    private lateinit var verifyButton : Button
    private lateinit var verifyInfoTextView : TextView
    private lateinit var timeInfoTextView : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        // UI 연결
        gotoLogin = view.findViewById(R.id.gotoLogin)
        gotoWelcomeLayout = view.findViewById(R.id.gotoWelcomeLayout)
        editUserName = view.findViewById(R.id.editUserName)
        spinnerSchool = view.findViewById(R.id.spinnerSchool)
        spinnerGrade = view.findViewById(R.id.spinnerGrade)
        editPhone = view.findViewById(R.id.editPhone)
        authButton = view.findViewById(R.id.authButton)
        row4 = view.findViewById(R.id.row4)
        editVerifyCode = view.findViewById(R.id.editVerifyCode)
        verifyButton = view.findViewById(R.id.verifyButton)
        verifyInfoTextView = view.findViewById(R.id.verifyInfoTextView)
        timeInfoTextView = view.findViewById(R.id.timeInfoTextView)


        // 스피너 어댑터 설정
        val adapterSchool = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.list_school,
            R.layout.spinner_item
        )
        adapterSchool.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerSchool.adapter = adapterSchool

        spinnerSchool.adapter = adapterSchool
        val adapterGrade = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            listOf("학년을 선택해주세요")
        )
        adapterGrade.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerGrade.adapter = adapterGrade

        spinnerSchool.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val selectedSchool = parent?.getItemAtPosition(position).toString()
                if (selectedSchool == "학교를 선택해주세요") {
                    val adapterGrade = ArrayAdapter(
                        requireContext(),
                        R.layout.spinner_item,
                        listOf("학년을 선택해주세요")
                    )
                    adapterGrade.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    spinnerGrade.adapter = adapterGrade
                }
                if (selectedSchool == "초등학교") {
                    val newAdapterGrade = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.list_grade_elementary,
                        R.layout.spinner_item
                    )
                    newAdapterGrade.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    spinnerGrade.adapter = newAdapterGrade
                }
                if(selectedSchool == "중학교") {
                    val newAdapterGrade = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.list_grade_middle,
                        R.layout.spinner_item
                    )
                    newAdapterGrade.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    spinnerGrade.adapter = newAdapterGrade
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // 뒤로가기(로그인 화면으로 이동) 버튼
        gotoLogin.setOnClickListener {
            findNavController().navigate(R.id.gotoLogin)
        }

        //전화번호 입력 및 수정 시
        editPhone.doOnTextChanged { text, start, before, count ->
            isVerified = false
            authButton.text = "인증번호 받기"
            if (text.isNullOrBlank()) {
                authButton.isEnabled = false
            } else {
                authButton.isEnabled = true

            }

        }

        //인증번호 받기 클릭시
        authButton.setOnClickListener {

            if(editPhone.text.isNullOrBlank()){
                Toast.makeText(requireContext(), "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                authButton.text = "인증번호 재전송"
                editVerifyCode.text = null
                row4.visibility = View.VISIBLE
                editVerifyCode.visibility = View.VISIBLE
                verifyButton.visibility = View.VISIBLE
                verifyButton.isEnabled = true
                verifyButton.text = "인증번호 확인"
                verifyInfoTextView.visibility = View.VISIBLE
                timeInfoTextView.visibility = View.VISIBLE

                startTimer(180)
            }

        }
        //인증번호 확인 클릭 시 (인증번호 = 1234)
        verifyButton.setOnClickListener {
            Log.d("tes2t", editVerifyCode.text.toString())
            if(editVerifyCode.text.toString() == "1234"){
                isVerified = true
                //Log.d("test", editVerifyCode.text.toString())
                Toast.makeText(requireContext(), "인증 성공!", Toast.LENGTH_SHORT).show()

            }
            else{
                //Log.d("test", editVerifyCode.text.toString())
                Toast.makeText(requireContext(), "인증번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 회원가입 버튼 클릭 시
        gotoWelcomeLayout.setOnClickListener {
            val userName = editUserName.text.toString().trim()
            val grade = spinnerGrade.selectedItem.toString()
            val school = spinnerSchool.selectedItem.toString()
            val phone = editPhone.text.toString().trim().replace("-", "")

            //필수 입력값 입력 여부 검사
            if (userName.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (school == "학교를 선택해주세요") { // 예시 문구
                Toast.makeText(requireContext(), "학교를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (grade == "학년을 선택해주세요") { // 예시 문구
                Toast.makeText(requireContext(), "학년을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //인증 완료 여부 검사
            if(!isVerified){
                Toast.makeText(requireContext(), "인증을 받으세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //데이터 서버로 전송
            val requestData = SignUpRequest(
                userName = userName,
                gradeLevel = grade,
                phone = phone,
                schoolLevel = school,
                userScore = 0
            )

            RetrofitClient.api.signUp(requestData).enqueue(object : Callback<ServerResponse> {
                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body?.success == true) {
                            findNavController().navigate(R.id.gotoWelcome)
                        } else {
                            // 이미 가입된 번호일 시
                            val errorMsg =  "이미 가입된 번호입니다."
                            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // 서버 에러 시
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(requireContext(), "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun startTimer(totalSeconds : Int){
        timerJob?.cancel()

        timerJob = viewLifecycleOwner.lifecycleScope.launch {
            var timeleft = totalSeconds

            while(timeleft >= 0){
                var minute = (timeleft / 60)
                var sec = (timeleft % 60)
                timeInfoTextView.text = String.format("%d:%02d", minute, sec)

                if(timeleft ==0){
                    authButton.text = "인증번호 받기"
                    verifyButton.isEnabled= false
                    editVerifyCode.visibility = View.INVISIBLE
                    verifyInfoTextView.visibility = View.INVISIBLE
                    timeInfoTextView.text = "인증 시간 만료"
                    break
                }
                if(isVerified){
                    authButton.text = "인증번호 받기"
                    verifyButton.text = "인증 완료"
                    verifyButton.isEnabled= false
                    verifyInfoTextView.visibility = View.INVISIBLE
                    timeInfoTextView.visibility = View.INVISIBLE
                    break
                }

                delay(1000L)
                timeleft--

            }
        }

    }

}