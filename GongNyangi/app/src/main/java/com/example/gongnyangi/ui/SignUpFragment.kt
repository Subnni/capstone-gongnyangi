package com.example.gongnyangi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R


class SignUpFragment : Fragment() {

    private lateinit var gotoLogin : ImageView
    private lateinit var gotoWelcomeLayout : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        //UI 연결
        gotoLogin = view.findViewById(R.id.gotoLogin)
        gotoWelcomeLayout = view.findViewById(R.id.gotoWelcomeLayout)

        //리스너 연결
        gotoLogin.setOnClickListener {
            findNavController().navigate(R.id.gotoLogin)
        }
        gotoWelcomeLayout.setOnClickListener {
            findNavController().navigate(R.id.gotoWelcome)
        }

    }

}