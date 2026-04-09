package com.example.gongnyangi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R

class BookConfirmPhotoFragment : Fragment() {

    val totalItemCount = 3 //이미지 개수

    private lateinit var textbookCompleteButton : TextView
    private lateinit var gotoGallery : ImageView
    private lateinit var imageCarouselViewer : ComposeView
    private lateinit var layoutPagination : LinearLayout
    private lateinit var currentPage : TextView
    private lateinit var totalPage : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_confirm_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        //ui 연결
        gotoGallery = view.findViewById(R.id.gotoGallery)
        textbookCompleteButton = view.findViewById(R.id.textbookCompleteButton)
        imageCarouselViewer = view.findViewById(R.id.imageCarouselViewer)
        layoutPagination = view.findViewById(R.id.layoutPagination)
        currentPage = view.findViewById(R.id.currentPage)
        totalPage = view.findViewById(R.id.totalPage)

        //사진 1개일 시 하단 페이지네이션 숨김
        if(totalItemCount ==1) layoutPagination.visibility = View.INVISIBLE
        else {
            layoutPagination.visibility = View.VISIBLE
            totalPage.text = totalItemCount.toString()
        }

        //이미지 캐러샐 ComposeView 호출
        imageCarouselViewer.setContent {
            androidx.compose.material3.MaterialTheme {
                ImageCarousel(itemCount = totalItemCount,onPageChanged = {
                    pageIndex ->
                    val cp = pageIndex + 1
                    currentPage.text = cp.toString()
                })
            }
        }
        totalPage.text = totalItemCount.toString()

        //페이지 이동 리스너
        gotoGallery.setOnClickListener {
            findNavController().navigate(R.id.gotoGallery)
        }
        textbookCompleteButton.setOnClickListener {
            findNavController().navigate(R.id.gotoBookLoading)
        }
    }

}