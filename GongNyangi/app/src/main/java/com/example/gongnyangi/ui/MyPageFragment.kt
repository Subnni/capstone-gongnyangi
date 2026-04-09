package com.example.gongnyangi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R

class MyPageFragment : Fragment() {

    private lateinit var gotoHomeImageView : ImageView
    private lateinit var popupTextView : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as? MainActivity)?.setNavVisible(false)

        //상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        //UI 연결
        gotoHomeImageView = view.findViewById(R.id.gotoHomeImageView)
        popupTextView = view.findViewById(R.id.popupTextView)

        //리스너 연결
        gotoHomeImageView.setOnClickListener {
            findNavController().navigate(R.id.gotoHome)
        }
        popupTextView.setOnClickListener { v ->
            //객체 생성
            val popup = PopupMenu(requireContext(), v)
            //xml 파일 연결
            popup.menuInflater.inflate(R.menu.popup_mypage, popup.menu)
            //리스너 연결
            popup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.item_modify -> {
                        findNavController().navigate(R.id.gotoMyPageModify)
                        true
                    }
                    R.id.item_logout -> {
                        true//아무것도 안함
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }
}