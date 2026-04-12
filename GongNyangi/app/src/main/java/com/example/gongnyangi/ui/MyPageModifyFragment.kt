package com.example.gongnyangi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R


class MyPageModifyFragment : Fragment() {

    private lateinit var gotoMyPageImageView : ImageView
    private lateinit var gotoMyPageDeleteAccountTextView : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page_modify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        gotoMyPageImageView = view.findViewById(R.id.gotoMyPageImageView)
        gotoMyPageDeleteAccountTextView = view.findViewById(R.id.gotoMyPageDeleteAccountTextView)

        gotoMyPageImageView.setOnClickListener{
            findNavController().navigate(R.id.modifyToMyPage)
        }
        gotoMyPageDeleteAccountTextView.setOnClickListener{
            findNavController().navigate(R.id.gotoMyPageDeleteAccount)
        }
    }
}