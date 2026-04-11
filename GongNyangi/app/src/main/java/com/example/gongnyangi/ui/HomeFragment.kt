package com.example.gongnyangi.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.gongnyangi.R
import com.example.gongnyangi.network.RetrofitInstance
import com.example.gongnyangi.network.apidata.InputData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    //UI 담을 변수
    //private lateinit var testText : TextView
    private lateinit var gotoCreateBook : ImageView
    private lateinit var gotoMyPage : LinearLayout
    private lateinit var bookListLayout : LinearLayout
    private lateinit var ghostTextView : TextView
    private lateinit var ghostImageView : ImageView
    private lateinit var roadmapBox : LinearLayout

    //레이아웃 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(activity as? MainActivity)?.setNavVisible(true)
        //상단 상태바 높이만큼 패딩 부여
        StatusBarUtils.addStatusBarPadding(view)

        //UI 연결
        gotoCreateBook = view.findViewById(R.id.gotoCreateBook)
        gotoMyPage = view.findViewById(R.id.gotoMyPage)
        //testText = view.findViewById(R.id.testText)
        bookListLayout = view.findViewById(R.id.bookListLayout)
        ghostImageView = view.findViewById(R.id.ghostImageView)
        ghostTextView = view.findViewById(R.id.ghostTextView)
        roadmapBox = view.findViewById(R.id.roadmapBox)

        //DB값 담을 변수
        //임의 생성
        var totalRoadMap : Int = 0
        if(totalRoadMap>0){
            //필요없는 뷰 invisible
            ghostImageView.setVisibility(View.INVISIBLE)
            ghostTextView.setVisibility(View.INVISIBLE)

            //로드맵 리스트 생성
            for(i in 1..totalRoadMap){
                val roadmapItemView = layoutInflater.inflate(R.layout.fragment_home_roadmap_item, bookListLayout, false)

                val titleTextView = roadmapItemView.findViewById<TextView>(R.id.titleTextView)
                val dateTextView = roadmapItemView.findViewById<TextView>(R.id.dateTextView)
                val popupTextView = roadmapItemView.findViewById<TextView>(R.id.popupTextView)

                //임시 코드
                titleTextView.setText("book{$i}가나다라마바사라아자")
                dateTextView.setText("2026.04.1{$i}")

                //리스너 연결
                popupTextView.setOnClickListener { v ->
                    //객체 생성
                    val popup = PopupMenu(requireContext(), v)
                    //xml 파일 연결
                    popup.menuInflater.inflate(R.menu.popup_roadmap, popup.menu)
                    //리스너 연결
                    popup.setOnMenuItemClickListener { item ->
                        when(item.itemId){
                            R.id.item_setting -> {
                                val bottomSheet_select = BottomSheetDialogFragment_CategorySelect()

                                bottomSheet_select.onCompleteSelectListener = {
                                    bottomSheet_select.dismiss()
                                }
                                //카테고리 관리 버튼 누를 시 새 바텀시트를 위에 띄움
                                bottomSheet_select.onSettingSelectListener = {
                                    val settingSheet = BottomSheetDialogFragment_CategorySetting()

                                    //바텀시트 안의 버튼 동작 정의
                                    settingSheet.onCompleteSettingListener = {
                                        settingSheet.dismiss()
                                        //bottomSheet_select.refreshData() //데이터 리프레쉬
                                    }

                                    settingSheet.show(parentFragmentManager, "CategorySettingSheet")
                                }

                                bottomSheet_select.show(parentFragmentManager, "CategorySelectSheet")
                                true
                            }
                            R.id.item_modify -> {
                                val bottomSheet_bookSetting = BottomSheetDialogFragment_BookSetting()

                                bottomSheet_bookSetting.onCompleteSelectListener = {
                                    bottomSheet_bookSetting.dismiss()
                                }
                                bottomSheet_bookSetting.show(parentFragmentManager, "CategorySelectSheet")
                                true
                            }
                            R.id.item_delete -> {
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                //선택된 책을 isSelected로 변경
                roadmapItemView.setOnClickListener { clickedView ->
                    //부모 안의 모든 자식 뷰를 isSelected=false
                    for (j in 0 until bookListLayout.childCount) {
                        bookListLayout.getChildAt(j).isSelected = false
                    }
                    clickedView.isSelected = true
                }
                bookListLayout.addView(roadmapItemView)
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
        }
        else{
            roadmapBox.setVisibility(View.INVISIBLE)
        }



        //페이지 이동 버튼 리스너 연결
        val currentFragment = parentFragmentManager.findFragmentById(R.id.fragmentContainer)
        gotoMyPage.setOnClickListener {
            findNavController().navigate(R.id.MyPageFragment)

        }
        gotoCreateBook.setOnClickListener {
            val intent = Intent(requireContext(), CreateBookActivity::class.java)
            startActivity(intent)
        }

    }

}