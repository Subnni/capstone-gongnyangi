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


class GalleryFragment : Fragment() {

    private lateinit var gotoTextbookConfirmTextView : TextView
    private lateinit var gotoBookcaseImageButton : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        //UI 연결
        gotoTextbookConfirmTextView = view.findViewById(R.id.gotoTextbookConfirmTextView)
        gotoBookcaseImageButton = view.findViewById(R.id.gotoBookcaseImageButton)

        //리스너 연결
        gotoTextbookConfirmTextView.setOnClickListener {
            findNavController().navigate(R.id.gotoBookConfirmPhoto)
        }
        gotoBookcaseImageButton.setOnClickListener {
            //val intent = Intent(requireContext(), MainActivity::class.java)
            //intent.putExtra("targetFragment", "Bookcase")
            //startActivity(intent)
            activity?.finish()
        }
    }
}